package com.advana;

public abstract class Config {

	// Set this to true, to show debug statements in console
	public static final boolean DEBUG = false;

	// https://app.powerbi.com/home
	// Two possible Authentication methods:
	// - For authentication with master user credential choose MasterUser as
	// AuthenticationType.
	// - For authentication with app secret choose ServicePrincipal as
	// AuthenticationType.
	// More details here: https://aka.ms/EmbedServicePrincipal
	public static final String authenticationType = "MasterUser";

	
	// Enter Application Id / Client Id
	// public static final String clientId = "0e3d2917-7aa7-4142-bb86-fc3210caa6ab";
	public static final String clientId = "86fad115-7fdb-439b-820a-9cade31f3dad";

	
	// Enter ServicePrincipal credentials
	public static final String tenantId = "d11a6097-dc0e-4ee6-897d-a7a5bdf5de59";
	// public static final String appSecret = "zFRrm-x4ZqxaPP4h96OE_-vM.8R3viKvQk";
	public static final String appSecret = "98ac504d-8c7e-4d71-9923-7a599ce38929";

	// DO NOT CHANGE
	public static final String authorityUrl = "https://login.microsoftonline.com/";
	//public static final String scopeUrl = "https://analysis.windows.net/powerbi/api/.default";
	public static final String scopeUrl ="api://86fad115-7fdb-439b-820a-9cade31f3dad";

	private Config() {
		// Private Constructor will prevent the instantiation of this class directly
		throw new IllegalStateException("Config class");
	}

}