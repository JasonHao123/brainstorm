package jason.app.brainstorm.network.operator.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class SystemServiceVersionRule {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	private Long version;
	
	@ManyToOne
	private SystemService service;
	
	@ManyToOne
	private VersionRule rule;

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

	public SystemService getService() {
		return service;
	}

	public void setService(SystemService service) {
		this.service = service;
	}

	public VersionRule getRule() {
		return rule;
	}

	public void setRule(VersionRule rule) {
		this.rule = rule;
	}
	
	
}
