package jason.app.brainstorm.network.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class SystemServiceGroupVersion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	private Long version;
	
	@Column
	private String systemName;
	
	@Column
	private String systemVersion;
	
	@Column
	private String serviceGroup;
	
	@Column
	private String dockerGroup;
	
	@Column
	private boolean global;
	
	@Column
	private String serviceVersion;
	
	@Column
	private String country;
	
	@Column
	private String contextPath;
	
	@Column
	private boolean nonceCheck;

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDockerGroup() {
		return dockerGroup;
	}

	public void setDockerGroup(String dockerGroup) {
		this.dockerGroup = dockerGroup;
	}

	public boolean isNonceCheck() {
		return nonceCheck;
	}

	public void setNonceCheck(boolean nonceCheck) {
		this.nonceCheck = nonceCheck;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}


}
