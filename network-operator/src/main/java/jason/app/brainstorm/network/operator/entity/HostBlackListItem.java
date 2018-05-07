package jason.app.brainstorm.network.operator.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class HostBlackListItem {
	// remote ip address
	@Id
	private String id;
	
	@Version
	private Long version;
	
	@Column
	private int riskLevel;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date punishTime;
	
	@Column
	private int accessCountDuringPunish;
	
	@Column
	private boolean forbidden;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public int getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(int riskLevel) {
		this.riskLevel = riskLevel;
	}

	public Date getPunishTime() {
		return punishTime;
	}

	public void setPunishTime(Date punishTime) {
		this.punishTime = punishTime;
	}

	public boolean isForbidden() {
		return forbidden;
	}

	public void setForbidden(boolean forbidden) {
		this.forbidden = forbidden;
	}

	public int getAccessCountDuringPunish() {
		return accessCountDuringPunish;
	}

	public void setAccessCountDuringPunish(int accessCountDuringPunish) {
		this.accessCountDuringPunish = accessCountDuringPunish;
	}
	
	
}
