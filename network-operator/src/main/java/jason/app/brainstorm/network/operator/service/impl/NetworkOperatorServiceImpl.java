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
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.network.operator.entity.HostBlackListItem;
import jason.app.brainstorm.network.operator.entity.SystemServiceGroupVersion;
import jason.app.brainstorm.network.operator.entity.VersionRule;
import jason.app.brainstorm.network.operator.repository.BlackListRepository;
import jason.app.brainstorm.network.operator.repository.SystemServiceGroupVersionRepository;
import jason.app.brainstorm.network.operator.repository.VersionRuleRepository;
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
	private VersionRuleRepository versionRuleRepo;


	@Autowired
	private RedisTemplate<String, CsrfToken> redisTemplate;
	
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
		// TODO Auto-generated method stub
		//"${header.country}-${header.serviceGroup}-${header.version}/camel/${header.serviceId}
		String system = (String) exchange.getProperty("system");
		String country = (String) exchange.getProperty("country");
		String version = (String) exchange.getProperty("version");
		String serviceGroup = (String) exchange.getIn().getHeader("serviceGroup");
		String service = (String) exchange.getIn().getHeader("serviceId");
		SystemServiceGroupVersion group = systemServiceGroupRepo.findFirstByCountryIsNullOrCountryIsAndSystemNameAndSystemVersionAndServiceGroupOrderByCountryDesc(country,system,version,serviceGroup);
		if(group==null) {
			throw new RuntimeException("invalid access");
		}
		VersionRule rule = versionRuleRepo.findFirstByServiceVersion_systemNameAndServiceVersion_countryAndServiceVersion_systemVersionAndServiceVersion_serviceGroupAndServiceVersion_service(system, country, version, serviceGroup, service);
		String serviceVersion = group.getServiceVersion();
		if(rule!=null) {
			try {
				Class clazz = Class.forName(rule.getClazz());
				IRule ruleObj = (IRule) clazz.newInstance();
				serviceVersion = ruleObj.run(rule.getParameters());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		String url = "";
		if(!group.isGlobal()) {
			url = url + country+"-";
		}
		url = url+serviceGroup+"-"+serviceVersion;
		exchange.getIn().setHeader("serviceUrl", url);
	}

	@Override
	public void setNounce(Exchange exchange) {
		Session session = sessionRepository.getSession((String) exchange.getProperty("SESSION"));
		if(session!=null) {
			DefaultCsrfToken token =  new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", UUID.randomUUID().toString());
			redisTemplate.opsForHash().put("spring:session:sessions:"+exchange.getProperty("SESSION"), "sessionAttr:org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN", token);
			exchange.getOut().setBody(exchange.getIn().getBody()+"   "+token.getToken());
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
