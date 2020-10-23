package br.com.personality.insights.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;


@Audited
@Entity
@Table(name = "profile")
public class ProfileEntity  implements Serializable {

	
	private static final long serialVersionUID = 741737733469894748L;
	
	private Long id;
	private String processed_language;
	private String word_count;
	private String word_count_message;
	private String perfilTwitter;
	private Date dataCriacao;
	
	private List<TwitterEntity> twittes;
	private List<PersonalityEntity> personalitys;
	private List<NeedEntity> needs;
	private List<ValueEntity> values;
	private List<BehaviorEntity> behaviors;
	private List<WarningEntity> warnings;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "processed_language")
	public String getProcessed_language() {
		return processed_language;
	}

	public void setProcessed_language(String processed_language) {
		this.processed_language = processed_language;
	}

	@Column(name = "word_count")
	public String getWord_count() {
		return word_count;
	}

	public void setWord_count(String word_count) {
		this.word_count = word_count;
	}

	@Column(name = "word_count_message")
	public String getWord_count_message() {
		return word_count_message;
	}

	public void setWord_count_message(String word_count_message) {
		this.word_count_message = word_count_message;
	}

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "profile")
	public List<PersonalityEntity> getPersonalitys() {
		return personalitys;
	}

	public void setPersonalitys(List<PersonalityEntity> personalitys) {
		this.personalitys = personalitys;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "profile")
	public List<NeedEntity> getNeeds() {
		return needs;
	}

	public void setNeeds(List<NeedEntity> needs) {
		this.needs = needs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "profile")
	public List<ValueEntity> getValues() {
		return values;
	}

	public void setValues(List<ValueEntity> values) {
		this.values = values;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "profile")
	public List<WarningEntity> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<WarningEntity> warnings) {
		this.warnings = warnings;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "profile")
	public List<BehaviorEntity> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(List<BehaviorEntity> behaviors) {
		this.behaviors = behaviors;
	}

	
	@Column(name = "perfil_twitter")
	public String getPerfilTwitter() {
		return perfilTwitter;
	}

	public void setPerfilTwitter(String perfilTwitter) {
		this.perfilTwitter = perfilTwitter;
	}

	@Column(name = "data_criacao")
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "profile")
	public List<TwitterEntity> getTwittes() {
		return twittes;
	}

	public void setTwittes(List<TwitterEntity> twittes) {
		this.twittes = twittes;
	}
}
