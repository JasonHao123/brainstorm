package jason.app.brainstorm.network.operator.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.apache.camel.http.common.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.network.operator.entity.HostBlackListItem;
import jason.app.brainstorm.network.operator.entity.SystemServiceGroupVersion;
import jason.app.brainstorm.network.operator.entity.SystemServiceVersion;
import jason.app.brainstorm.network.operator.repository.BlackListRepository;
import jason.app.brainstorm.network.operator.repository.SystemServiceGroupVersionRepository;
import jason.app.brainstorm.network.operator.repository.SystemServiceVersionRepository;
import jason.app.brainstorm.network.operator.rule.IRule;
import jason.app.brainstorm.network.operator.service.NetworkOperatorService;

@Service
@ConfigurationProperties("blacklist")
public class NetworkOperatorServiceImpl implements NetworkOperatorService {
	private Calendar calendar = Calendar.getInstance();
	private int punishCount;
	private int punishTime;
	private int forbidden;
	@Autowired
	private BlackListRepository blackListRepo;
	
	@Autowired
	private SystemServiceGroupVersionRepository systemServiceGroupRepo;
	
	@Autowired
	private SystemServiceVersionRepository serviceRepo;


	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private RedisOperationsSessionRepository sessionRepository;
	
	@Override
	public boolean matches(Exchange exchange) {

		if (exchange.getIn() instanceof HttpMessage) {
			HttpServletRequest request = ((HttpMessage) exchange.getIn()).getRequest();
			String host = request.getRemoteHost();
			exchange.setProperty("remoteHost", host);
			HostBlackListItem item = blackListRepo.findOne(host);
			if (item == null) {
				return false;
			} else {
				if (item.isForbidden()) {
					return true;
				} else {
					if (item.getPunishTime() == null) {
						return false;
					} else {
						// punish is expire, clear punish
							if (item.getPunishTime().compareTo(new Date()) < 0) {
								calendar.setTime(new Date());
								calendar.add(Calendar.MINUTE, (-1)*item.getRiskLevel()*punishTime);
								if(calendar.after(item.getPunishTime())) {
								item.setPunishTime(null);
								if(item.getRiskLevel()>1) {
									item.setRiskLevel(item.getRiskLevel()-1);
									blackListRepo.save(item);
								}else {
									blackListRepo.delete(item);
								}
								
							}
							return false;
						} else {
							// increase access count, and check whether it's exceed max limit, then increase
							// punishment
							item.setAccessCountDuringPunish(item.getAccessCountDuringPunish() + 1);
							if (item.getAccessCountDuringPunish() >= punishCount) {
								item.setRiskLevel(item.getRiskLevel()+1);
								if(item.getRiskLevel()>=forbidden) {
									item.setForbidden(true);
								}
								item.setAccessCountDuringPunish(0);
							}
							calendar.setTime(new Date());
							calendar.add(Calendar.MINUTE, item.getRiskLevel()*punishTime);
							item.setPunishTime(calendar.getTime());
							blackListRepo.save(item);
							return true;
						}
					}

				}
			}
		} else {
			return true;
		}
	}

	@Override
	public void recordFailure(Exchange exchange) {
		String host = (String) exchange.getProperty("remoteHost");
		System.out.println("---------------record failure-------------" + host);


		HostBlackListItem item = blackListRepo.findOne(host);
		if (item == null) {
			item = new HostBlackListItem();
			item.setId(host);
		}
		item.setAccessCountDuringPunish(item.getAccessCountDuringPunish() + 1);
		if (item.getAccessCountDuringPunish() >= punishCount) {
			calendar.setTime(new Date());
			item.setRiskLevel(item.getRiskLevel()+1);
			if(item.getRiskLevel()>=forbidden) {
				item.setForbidden(true);
			}
			calendar.add(Calendar.MINUTE, item.getRiskLevel()*punishTime);
			item.setPunishTime(calendar.getTime());
			item.setAccessCountDuringPunish(0);
		}
		blackListRepo.save(item);

	}

	public int getPunishCount() {
		return punishCount;
	}

	public void setPunishCount(int punishCount) {
		this.punishCount = punishCount;
	}

	public int getPunishTime() {
		return punishTime;
	}

	public void setPunishTime(int punishTime) {
		this.punishTime = punishTime;
	}

	public int getForbidden() {
		return forbidden;
	}

	public void setForbidden(int forbidden) {
		this.forbidden = forbidden;
	}

	@Override
	public void handleServiceUrl(Exchange exchange) {
		String system = (String) exchange.getProperty("system");
		String country = (String) exchange.getProperty("country");
		String version = (String) exchange.getProperty("version");
		String serviceGroup = (String) exchange.getIn().getHeader("serviceGroup");
		String service = (String) exchange.getIn().getHeader("serviceId");
		SystemServiceGroupVersion group = systemServiceGroupRepo.findFirstByCountryIsNullOrCountryIsAndSystemNameAndSystemVersionAndServiceGroupOrderByCountryDesc(country,system,version,serviceGroup);
		if(group==null) {
			throw new RuntimeException("invalid access");
		}
		SystemServiceVersion serviceObj = serviceRepo.findFirstByServiceGroup_systemNameAndServiceGroup_countryAndServiceGroup_systemVersionAndServiceGroup_serviceGroupAndService(system, country, version, serviceGroup, service);
		// nounce check
		if(serviceObj!=null && serviceObj.isNonceCheck()) {
			checkNounce((String) exchange.getProperty("SESSION"),(String) exchange.getProperty("nonce"));
		}else if(serviceObj==null && group.isNonceCheck()) {
			checkNounce((String) exchange.getProperty("SESSION"),(String) exchange.getProperty("nonce"));
		}
		String serviceVersion = group.getServiceVersion();
		if(serviceObj!=null && serviceObj.getServiceVersion()!=null) {
			serviceVersion = serviceObj.getServiceVersion();
		}
		String url = null;
		if(!group.isGlobal()) {
			url = country;
		}
		if(serviceVersion!=null) {
			if(url==null) {
				url = serviceVersion;
			}else {
				url = url+"-"+serviceVersion;
			}
		}
		if(group.getDockerGroup()!=null) {
			if(url==null) {
				url = group.getDockerGroup();
			}else {
				url = url+"-"+group.getDockerGroup();
			}
		}else {
			if(url==null) {
				url = serviceGroup;
			}else {
				url = url+"-"+ serviceGroup;
			}
		}
		exchange.getIn().setHeader("serviceUrl", url);
	}

	private void checkNounce(String sessionId,String nonce) {
		// TODO Auto-generated method stub
		Session session = sessionRepository.getSession(sessionId);
		if(session!=null) {
			String token = (String) redisTemplate.opsForHash().get("spring:session:sessions:"+sessionId, "sessionAttr:nonce");
			if(token!=null && !token.equals(nonce)) {
				throw new RuntimeException("Illegal access!");
			}
		}
	}

	@Override
	public void setNounce(Exchange exchange) {
		Session session = sessionRepository.getSession((String) exchange.getProperty("SESSION"));
		if(session!=null) {
			String token =  UUID.randomUUID().toString();
			redisTemplate.opsForHash().put("spring:session:sessions:"+exchange.getProperty("SESSION"), "sessionAttr:nonce", token);
			exchange.getOut().setBody(exchange.getIn().getBody()+"   "+token);
		}
	}

	@Override
	public void detectIpChange(Exchange exchange) throws IllegalAccessException {
		String remoteIp = (String) exchange.getProperty("remoteHost");
		Session session = sessionRepository.getSession((String) exchange.getProperty("SESSION"));
		if(session!=null) {
			String sessionIp = session.getAttribute("remoteIp");
			if(sessionIp==null) {
				redisTemplate.opsForHash().put("spring:session:sessions:"+exchange.getProperty("SESSION"), "sessionAttr:remoteIp", remoteIp);
			}else if(!remoteIp.equals(sessionIp)) {
				throw new IllegalAccessException();
			}
		}
		
	}
	
	
}
