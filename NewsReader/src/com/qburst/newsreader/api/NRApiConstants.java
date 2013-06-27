package com.qburst.newsreader.api;

/**
 * @author user
 * 
 * All constants related to API are defined.
 */
public class NRApiConstants {


	
	public static final int kRequestTimeOutInMills = 20000;
	public static final String kApiError = "API Error";
	public static final String kApiLargeContentError = "Large Response Error";
	public static final String kInvalidResponseError ="Invalid Response";
	public static final String kNetworkError = "Failed";
	public static final String kHttpStatusKey = "HttpStatus";
	public static final String kErrorMsgKey = "errorMessages";
	public static final String kNetworkErrorExceptionKey = "101";
	public static final String kLargeContentErrorExceptionKey = "105";
	public static final String kApiFailedMsgKey = "Failed";
	public static final String kApiSuccessMsgKey = "Success";
	public static final String kSuccessMsgKey = "OK";
	public static final String kAuthenticationErrorExceptionKey = "104";
	public static final String kInternalServerErrorExceptionKey = "103";
	public static final String kTimeOutErrorExceptionKey = "102";
	public static final String baseUrl = "http://content.guardianapis.com/search?q=news&format=json&show-fields=all&show-factboxes=all";
	public static final String kNetworkErrorMessage = "No Internet connection. Please try again later.";
	public static final String kNetworkTimeoutErrorMessage = "You've lost connection. Please try again.";
	public static final String kInternalServerErrorMessage = "Error occurred in server.";
	public static final String kAuthenticationErrorMessage = "User is invalid.";
	public static final String kLargeContentProcessingErrorMessage = "We are unable to process the huge response from the server.";
	public static final int kApiFailedCode = -1;
	

}
