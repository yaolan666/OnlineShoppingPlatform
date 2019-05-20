package com.youzhi.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public void setUserID(java.lang.Integer userID) {
		set("userID", userID);
	}
	
	public java.lang.Integer getUserID() {
		return getInt("userID");
	}

	public void setUsername(java.lang.String username) {
		set("username", username);
	}
	
	public java.lang.String getUsername() {
		return getStr("username");
	}

	public void setRealname(java.lang.String realname) {
		set("realname", realname);
	}
	
	public java.lang.String getRealname() {
		return getStr("realname");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}
	
	public java.lang.String getSex() {
		return getStr("sex");
	}

	public void setUserPassword(java.lang.String userPassword) {
		set("userPassword", userPassword);
	}
	
	public java.lang.String getUserPassword() {
		return getStr("userPassword");
	}

	public void setEmail(java.lang.String email) {
		set("email", email);
	}
	
	public java.lang.String getEmail() {
		return getStr("email");
	}

	public void setTelephone(java.lang.String telephone) {
		set("telephone", telephone);
	}
	
	public java.lang.String getTelephone() {
		return getStr("telephone");
	}

	public void setID(java.lang.String ID) {
		set("ID", ID);
	}
	
	public java.lang.String getID() {
		return getStr("ID");
	}

	public void setRegTime(java.util.Date regTime) {
		set("regTime", regTime);
	}
	
	public java.util.Date getRegTime() {
		return get("regTime");
	}

	public void setAddress(java.lang.String address) {
		set("address", address);
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

}
