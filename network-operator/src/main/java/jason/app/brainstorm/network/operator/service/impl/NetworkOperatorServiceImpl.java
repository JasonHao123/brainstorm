package jason.app.brainstorm.network.operator.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jason.app.brainstorm.network.api.request.NetworkRequest;
import jason.app.brainstorm.network.api.response.NetworkResponse;
import jason.app.brainstorm.network.api.vo.AppHeader;
import jason.app.brainstorm.network.api.vo.Header;
import jason.app.brainstorm.network.api.vo.SecurityHeader;
import jason.app.brainstorm.network.operator.entity.HostBlackListItem;
import jason.app.brainstorm.network.operator.entity.SystemServiceGroupVersion;
import jason.app.brainstorm.network.operator.entity.SystemServiceVersion;
import jason.app.brainstorm.network.operator.repository.BlackListRepository;
import jason.app.brainstorm.network.operator.repository.SystemServiceGroupVersionRepository;
import jason.app.brainstorm.network.operator.repository.SystemServiceVersionRepository;
import jason.app.brainstorm.network.operator.service.NetworkOperatorService;

@Service
@ConfigurationProperties("blacklist")
public class NetworkOperatorServiceImpl implements NetworkOperatorService {
	private ObjectMapper mapper = new ObjectMapper();
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
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
		NetworkRequest request = (NetworkRequest) exchange.getIn().getBody();
		String system = request.getHeader().getApp().getSystem();
		String country = request.getHeader().getApp().getCountry();
		String version = request.getHeader().getApp().getVersion();
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
		exchange.getIn().setHeader("serviceGroupUrl", url);
		if(group.getContextPath()!=null) {
			service = group.getContextPath()+service;
		}
		exchange.getIn().setHeader("serviceUrl", service);
		
	}

	private void checkNounce(String sessionId,String nonce) {
		// TODO Auto-generated method stub
		Session session = sessionRepository.getSession(sessionId);
		if(session!=null) {
			CsrfToken token = (CsrfToken) redisTemplate.opsForHash().get("spring:session:sessions:"+sessionId, "sessionAttr:org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN");
			if(token!=null && !token.getToken().equals(nonce)) {
				throw new RuntimeException("Illegal access!");
			}
		}
	}

	@Override
	public void updateHeader(Exchange exchange) throws IOException {
		NetworkResponse response = null;
		try {
			response = mapper.readValue((byte[])exchange.getIn().getBody(),NetworkResponse.class);
		}catch(JsonParseException e) {
			response = new NetworkResponse();
			response.setBody(exchange.getIn().getBody());
		}
		
		if(response.getHeader()==null) {
			response.setHeader(new Header());
		}
		if(response.getHeader().getSecurity()==null) {
			response.getHeader().setSecurity(new SecurityHeader());
		}
		if(response.getHeader().getApp()==null) {
			response.getHeader().setApp(new AppHeader());
		}
		response.getHeader().getSecurity().setSession((String) exchange.getProperty("SESSION"));
		response.getHeader().getApp().setTime(format.format(new Date()));
		Session session = sessionRepository.getSession((String) exchange.getProperty("SESSION"));
		if(session!=null) {
			DefaultCsrfToken token =  new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", UUID.randomUUID().toString());
			redisTemplate.opsForHash().put("spring:session:sessions:"+exchange.getProperty("SESSION"), "sessionAttr:org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN", token);
			response.getHeader().getSecurity().setNounce(token.getToken());
		}
		String output = mapper.writeValueAsString(response);
		exchange.getOut().setBody(output);
		exchange.getOut().setHeader("Content-Type", "application/json");
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
