package org.mailfilter.service.model;

import java.io.Serializable;

import org.exoplatform.services.jcr.util.IdGenerator;

public class Spammer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4521719601188655634L;
	public final static String PREF = "espamer";
	public final static String NT_NAME = "exo:spamer";
	public final static String P_MAIL = "exo:email";
	public final static String P_DESCRIPTION= "exo:description";
	public final static String P_STATUS = "exo:status";
	public final static String P_SENDER = "exo:sender";
	public final static String ST_DEFAULT = "0";
	public final static String ST_BLOCK = "1";
	public final static String ST_PENDING = "2";
	public final static String ST_ARCHIVE = "3";
	public final static String[] STT = {ST_DEFAULT, ST_BLOCK, ST_PENDING, ST_ARCHIVE};
	private String id;
	private String email;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	private String description;
	private String status;
	private String sender;
	
	public Spammer() {
		setId(PREF + IdGenerator.generate());
	}
}
