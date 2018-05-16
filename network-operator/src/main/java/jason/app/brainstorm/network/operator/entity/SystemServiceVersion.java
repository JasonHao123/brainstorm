package jason.app.brainstorm.network.operator.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class SystemServiceVersion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	private Long version;
	
	@ManyToOne
	private SystemServiceGroupVersion serviceGroup;
	
	@Column
	private String service;
	
	@Column
	private boolean nonceCheck;
	
	@Column
	private String serviceVersion;
	
	@OneToMany(mappedBy="serviceVersion")
	private List<VersionRule> rules;

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

	public SystemServiceGroupVersion getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(SystemServiceGroupVersion serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public boolean isNonceCheck() {
		return nonceCheck;
	}

	public void setNonceCheck(boolean nonceCheck) {
		this.nonceCheck = nonceCheck;
	}

	public List<VersionRule> getRules() {
		return rules;
	}

	public void setRules(List<VersionRule> rules) {
		this.rules = rules;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	

	
}
