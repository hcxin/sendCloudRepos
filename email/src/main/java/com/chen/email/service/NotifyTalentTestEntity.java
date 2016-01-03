package com.chen.email.service;

import org.apache.commons.lang.StringUtils;

public class NotifyTalentTestEntity {
	private String address;
	private String name;
	private String companyName;
	private String positionName;
	private String expiration;
	private String timeLimit;
	private String testUrl;
	private String imageUrl;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getTestUrl() {
		return testUrl;
	}

	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public NotifyTalentTestEntity(String address, String name,
			String companyName, String positionName, String expiration,
			String timeLimit, String testUrl, String imageUrl) {
		super();
		this.address = address;
		this.name = name;
		this.companyName = companyName;
		this.positionName = positionName;
		this.expiration = expiration;
		this.timeLimit = timeLimit;
		this.testUrl = testUrl;
		this.imageUrl = imageUrl;
	}

	public NotifyTalentTestEntity(String address, String companyName,
			String positionName, String expiration, String timeLimit,
			String testUrl, String imageUrl) {
		super();
		this.address = address;
		this.name = StringUtils.EMPTY;
		this.companyName = companyName;
		this.positionName = positionName;
		this.expiration = expiration;
		this.timeLimit = timeLimit;
		this.testUrl = testUrl;
		this.imageUrl = imageUrl;
	}
}
