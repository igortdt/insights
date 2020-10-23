package br.com.personality.insights.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;


@Audited
@Entity
@Table(name = "warning")
public class WarningEntity implements Serializable {


	private static final long serialVersionUID = -1665217249696329612L;

	private Long id;
	
	private String warning_id;
	private String message;
	private ProfileEntity profile;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "warning_id")
	public String getWarning_id() {
		return warning_id;
	}

	public void setWarning_id(String warning_id) {
		this.warning_id = warning_id;
	}

	
	@Column(name = "message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@ManyToOne
	@JoinColumn(name = "profile_id")
	public ProfileEntity getProfile() {
		return profile;
	}

	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}
}
