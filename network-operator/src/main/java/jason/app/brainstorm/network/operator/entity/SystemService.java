package jason.app.brainstorm.network.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class SystemService {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	private Long version;
	
	@ManyToOne
	private SystemVersion systemVersion;
	
	@ManyToOne
	private ServiceGroup serviceGroup;
	
	@Column
	private String service;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public SystemVersion getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(SystemVersion systemVersion) {
		this.systemVersion = systemVersion;
	}

	public ServiceGroup getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(ServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
	
}
