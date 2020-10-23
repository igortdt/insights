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
@Table(name = "need")
public class NeedEntity implements Serializable {

	private static final long serialVersionUID = -2343314846890219627L;
	
	private Long id;
	private String trait_id;
	private String name;
	private String category;
	private String percentile;
	private String significant;
	private ProfileEntity profile;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "trait_id")
	public String getTrait_id() {
		return trait_id;
	}

	public void setTrait_id(String trait_id) {
		this.trait_id = trait_id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "category")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "percentile")
	public String getPercentile() {
		return percentile;
	}

	public void setPercentile(String percentile) {
		this.percentile = percentile;
	}

	@Column(name = "significant")
	public String getSignificant() {
		return significant;
	}

	public void setSignificant(String significant) {
		this.significant = significant;
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
