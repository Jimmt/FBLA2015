package com.jumpbuttonstudio.FBLA2015.box2d;

/**
 * Small class for contactlistener data
 *
 */
public class UserData {
	public String tag;
	public Object value;

	public UserData() {

	}

	public void setValue(Object value) {
		this.value = value;
		
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
