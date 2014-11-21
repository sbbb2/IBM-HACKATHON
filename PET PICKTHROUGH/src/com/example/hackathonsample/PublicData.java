package com.example.hackathonsample;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

@IBMDataObjectSpecialization("PublicData")
public class PublicData extends IBMDataObject {
	
	public static final String CLASS_NAME = "PublicData";
	private static final String ZIP_CODE = "zip_code";
	private static final String REQUEST_TYPE = "request_type";
	private static final String AREA = "neighborhood";
	private static final String CREATIONDATE = "creation_date";
	private static final String STREET = "street_address";
	
	
	/**
	 * Gets the name of the Item.
	 * @return String itemName
	 */
	public String getStreet() {
		return (String) getObject(STREET);
	}
	
	

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setStreet(String street) {
		setObject(STREET, (street != null) ? street : "");
	}
	
	
	public String getCreationDate() {
		return (String) getObject(CREATIONDATE);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setCreationDate(String date) {
		setObject(CREATIONDATE, (date != null) ? date : "");
	}
	
	public String getZipCode() {
		return (String) getObject(ZIP_CODE);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setZipCode(String zip) {
		setObject(ZIP_CODE, (zip != null) ? zip : "");
	}
	
	
	/**
	 * Gets the name of the Item.
	 * @return String itemName
	 */
	public String getRequestType() {
		return (String) getObject(REQUEST_TYPE);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setRequestType(String type) {
		setObject(REQUEST_TYPE, (type != null) ? type : "");
	}
	
	
	/**
	 * Gets the name of the Item.
	 * @return String itemName
	 */
	public String getArea() {
		return (String) getObject(AREA);
	}

	/**
	 * Sets the name of a list item, as well as calls setCreationTime().
	 * @param String itemName
	 */
	public void setArea(String locality) {
		setObject(AREA, (locality != null) ? locality : "");
	}
	
	
	/**
	 * When calling toString() for an item, we'd really only want the name.
	 * @return String theItemName
	 */
	public String toString() {
		String theItemName = "";
		theItemName = getRequestType();
		return theItemName;
	}
}
