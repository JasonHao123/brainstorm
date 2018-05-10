package jason.app.brainstorm.network.operator.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class SystemServiceVersion {

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
	private String country;
	
	@Column
	private String serviceGroup;
	
	@Column
	private String service;
	
	@OneToMany(mappedBy="serviceVersion")
	private List<VersionRule> rules;
	

}
