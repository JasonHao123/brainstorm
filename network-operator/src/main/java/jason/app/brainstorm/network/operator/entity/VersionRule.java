package jason.app.brainstorm.network.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class VersionRule {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Version
	private Long version;
	
	@Column
	private String name;
	
	@Column
	private String clazz;
	
	@Column
	private String parameters;
	
	@ManyToOne
	private SystemServiceVersion serviceVersion;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public SystemServiceVersion getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(SystemServiceVersion serviceVersion) {
		this.serviceVersion = serviceVersion;
	}
	
	
}
