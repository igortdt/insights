package br.com.personality.insights.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name = "twitter")
public class TwitterEntity implements Serializable {

	private static final long serialVersionUID = 2150330866807349650L;
	
	
	private Long id;
	private Date createdAt;
	private String text;
	private String source;
	private boolean truncated;
	private Long inReplyToStatusId;
	private Long inReplyToUserId;
	private String inReplyToScreenName;
	private String geoLocation;
	private String place;
	private boolean favorited;
	private boolean retweeted;
	private int favoriteCount;
	private String userTwitter;
	private boolean retweet;
	private String retweetedStatus;
	private int retweetCount;
	private boolean retweetedByMe;
	private Long currentUserRetweetId;
	private Long possiblySensitive;
	private String lang;
	private ProfileEntity profile;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "text", length= 2000)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "source", length= 2000)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name="truncated")
	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	@Column(name = "in_reply_to_status_id")
	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	@Column(name = "in_reply_to_user_id")
	public Long getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(Long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	@Column(name = "in_reply_to_screen_name", length= 2000)
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	@Column(name = "geo_location", length= 2000)
	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	@Column(name = "place", length= 2000)
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "favorited")
	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	@Column(name = "retweeted")
	public boolean isRetweeted() {
		return retweeted;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}

	@Column(name = "favorite_count")
	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	

	@Column(name = "retweet")
	public boolean isRetweet() {
		return retweet;
	}

	public void setRetweet(boolean retweet) {
		this.retweet = retweet;
	}

	@Column(name = "retweeted_status", length= 2000)
	public String getRetweetedStatus() {
		return retweetedStatus;
	}

	public void setRetweetedStatus(String retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}

	@Column(name = "retweet_count")
	public int getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}

	@Column(name = "retweeted_by_me")
	public boolean isRetweetedByMe() {
		return retweetedByMe;
	}

	public void setRetweetedByMe(boolean retweetedByMe) {
		this.retweetedByMe = retweetedByMe;
	}

	@Column(name = "current_user_retweet_id")
	public Long getCurrentUserRetweetId() {
		return currentUserRetweetId;
	}

	public void setCurrentUserRetweetId(Long currentUserRetweetId) {
		this.currentUserRetweetId = currentUserRetweetId;
	}

	@Column(name = "possibly_sensitive")
	public Long getPossiblySensitive() {
		return possiblySensitive;
	}

	public void setPossiblySensitive(Long possiblySensitive) {
		this.possiblySensitive = possiblySensitive;
	}

	@Column(name = "lang", length= 2000)
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@ManyToOne
	@JoinColumn(name = "profile_id")
	public ProfileEntity getProfile() {
		return profile;
	}

	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}

	@Column(name = "user_twitter")
	public String getUserTwitter() {
		return userTwitter;
	}

	public void setUserTwitter(String userTwitter) {
		this.userTwitter = userTwitter;
	}
}
