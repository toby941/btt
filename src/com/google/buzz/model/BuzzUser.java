package com.google.buzz.model;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BuzzUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7179694628647872526L;

	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Long id;

	@Persistent
	private String buzzId;

	@Persistent
	private String twitterId;

	@Persistent
	private String consumerKey;
	@Persistent
	private String consumerSecret;
	@Persistent
	private String accessToken;
	@Persistent
	private String accessTokenSecret;

	public BuzzUser() {
		super();
	}

	public BuzzUser(String buzzId, String twitterId, String consumerKey,
			String consumerSecret, String accessToken, String accessTokenSecret) {
		this.buzzId = buzzId;
		this.twitterId = twitterId;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuzzId() {
		return buzzId;
	}

	public void setBuzzId(String buzzId) {
		this.buzzId = buzzId;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

}
