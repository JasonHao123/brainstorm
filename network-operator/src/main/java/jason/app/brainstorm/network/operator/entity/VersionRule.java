package jason.app.brainstorm.network.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class VersionRule {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	@Version
	private Long version;
	
	@Column
	private String name;
	
	@Column
	private String clazz;
	
	@Column
	private String parameters;
}
