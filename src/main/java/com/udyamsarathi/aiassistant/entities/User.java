package com.udyamsarathi.aiassistant.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.udyamsarathi.aiassistant.models.Queries;

@Document
public class User {
	
	@Id
	private String id;
	private String name;
	private String phonenumber;
	private String businesstype;
	private String location;
	private String Businesspref;
	private Queries queries;
	private Date createdat;
	private Date updatedat;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBusinesspref() {
		return Businesspref;
	}
	public void setBusinesspref(String businesspref) {
		Businesspref = businesspref;
	}
	public Queries getQueries() {
		return queries;
	}
	public void setQueries(Queries queries) {
		this.queries = queries;
	}
	public Date getCreatedat() {
		return createdat;
	}
	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}
	public Date getUpdatedat() {
		return updatedat;
	}
	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}
	public User(String id, String name, String phonenumber, String businesstype, String location, String businesspref,
			Queries queries, Date createdat, Date updatedat) {
		super();
		this.id = id;
		this.name = name;
		this.phonenumber = phonenumber;
		this.businesstype = businesstype;
		this.location = location;
		Businesspref = businesspref;
		this.queries = queries;
		this.createdat = createdat;
		this.updatedat = updatedat;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phonenumber=" + phonenumber + ", businesstype=" + businesstype
				+ ", location=" + location + ", Businesspref=" + Businesspref + ", queries=" + queries + ", createdat="
				+ createdat + ", updatedat=" + updatedat + "]";
	}
	
	

}
