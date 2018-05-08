package jason.app.brainstorm.network.operator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class ServiceGroup {
	
	@Id
	private String id;
	
	@Version
	private Long version;
	
	@Column
	private String name;
}
