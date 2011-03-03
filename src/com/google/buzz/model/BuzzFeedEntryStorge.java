package com.google.buzz.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class BuzzFeedEntryStorge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6346049400720638010L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBitlyUrl() {
		return bitlyUrl;
	}

	public void setBitlyUrl(String bitlyUrl) {
		this.bitlyUrl = bitlyUrl;
	}

	public boolean isSynced() {
		return isSynced;
	}

	public void setSynced(boolean isSynced) {
		this.isSynced = isSynced;
	}

	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Long id;

	@Persistent
	private String who;

	@Persistent
	private Date postDate;

	@Persistent
	private String title;

	@Persistent
	private String bitlyUrl;

	@Persistent
	private boolean isSynced;

	public BuzzFeedEntryStorge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BuzzFeedEntryStorge(String userId, Date date, String title,
			String url, boolean isSynced) {
		this.who = userId;
		this.postDate = date;
		this.title = title;
		this.bitlyUrl = url;
		this.isSynced = isSynced;
	}
}
