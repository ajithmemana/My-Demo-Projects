package com.qburst.newsreader.api;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.qburst.newsreader.api.beans.response.NRBaseResponseBean;
import com.qburst.newsreader.api.beans.response.NRErrorMessageResponseBean;
import com.qburst.newsreader.api.beans.response.NRResponseMessagesBean;
import com.qburst.newsreader.interfaces.NRApiListener;
import com.qburst.newsreader.interfaces.NewsListener;
import com.qburst.newsreader.interfaces.NRNetworkUnAvailableRetryListener;
import com.qburst.newsreader.utilities.NRUtility;

/**
 * @author user
 * 
 *         Designed base web service class to implement lower level network
 *         actions. Such as converting beans to stream and stream to beans.
 * 
 */
public class NRBaseWebService extends AsyncTask<String, Void, Object> {

	private String _postParams = null;
	private DefaultHttpClient _httpClient = null;
	private int _httpStatusCode = 0;
	private HttpResponse _httpResponse = null;
	private NRApiListener _apiListener = null;
	@SuppressWarnings("rawtypes")
	private Class _responseClass = null;
	String responseType = "JSON";
	private String _userName;
	private String _password;
	NewsListener _listener;
	AlertDialog.Builder alertDialog;
	ProgressDialog _progressDialogue;
	NRNetworkUnAvailableRetryListener _retry;
	private BasicCookieStore cookieJar;

	public NRBaseWebService(NRApiListener apiListener) {
		this._apiListener = apiListener;

	}

	public NRBaseWebService(NRApiListener apiListener, String postParams) {
		this._apiListener = apiListener;
		this._postParams = postParams;

	}

	@SuppressWarnings("rawtypes")
	public NRBaseWebService(NRApiListener apiListener, String postParams,
			Class responseClass) {
		this._apiListener = apiListener;
		this._postParams = postParams;
		this._responseClass = responseClass;

	}

	public NRBaseWebService(NRApiListener apiListener, String postParams,
			@SuppressWarnings("rawtypes") Class responseClass, String baseUrl) {
		this._apiListener = apiListener;
		this._postParams = postParams;
		this._responseClass = responseClass;
	
	}

	public HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new NRSSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	@Override
	protected void onPreExecute() {

		_httpClient = (DefaultHttpClient) getNewHttpClient();


		_httpClient.setCookieStore(cookieJar);

		if (_userName != null && _password != null) {

			HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

				@Override
				public void process(HttpRequest request, HttpContext context)
						throws HttpException, IOException {
					AuthState authState = (AuthState) context
							.getAttribute(ClientContext.TARGET_AUTH_STATE);

					if (authState.getAuthScheme() == null) {
						UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
								_userName, _password);
						if (creds != null) {
							authState.setAuthScheme(new BasicScheme());
							authState
									.setCredentials((org.apache.http.auth.Credentials) creds);
						}
					}
				}
			};
			_httpClient.addRequestInterceptor(preemptiveAuth, 0);

		}

		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Void... values) {

		super.onProgressUpdate(values);
	}

	public byte[] readBytes(InputStream inputStream) throws IOException {
		// this dynamically extends to take the bytes you read
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

		// this is storage overwritten on each iteration with bytes
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		// we need to know how may bytes were read to write them to the
		// byteBuffer
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			byteBuffer.write(buffer, 0, len);
		}

		// and then we can return your byte array.
		return byteBuffer.toByteArray();
	}

	@Override
	protected Object doInBackground(String... urls) {
		String serverResponse = null;

	//	if (BEUtility.isNetworkAvailable(BillExpressApp.getSharedApplication()
			//	.getApplicationContext())) {
			try {
				// POST
				_httpResponse = this.doJsonPostRequest(urls[0]);

				if (_httpResponse != null) {

					if (this.responseType.equalsIgnoreCase("STREAM")) {

						_httpStatusCode = _httpResponse.getStatusLine()
								.getStatusCode();
						return (Object) _httpResponse.getEntity().getContent();

					} else {

						serverResponse = NRUtility
								.convertStreamToString(_httpResponse
										.getEntity().getContent());
					}

					_httpStatusCode = _httpResponse.getStatusLine()
							.getStatusCode();

				} else {

					serverResponse = NRApiConstants.kApiError;
				}
			} catch (ClientProtocolException e) {
				serverResponse = NRApiConstants.kApiError;
				e.printStackTrace();
			} catch (IOException e) {
				serverResponse = NRApiConstants.kApiError;
				e.printStackTrace();
			} catch (OutOfMemoryError e) {
				serverResponse = NRApiConstants.kApiLargeContentError;
				e.printStackTrace();
			}
		//} else {

	//		serverResponse = BEApiConstants.kNetworkError;

		//}
		Log.i("serverResponse", "" + serverResponse);
		return serverResponse;
	}

	public void setAuthenticationParams(String username, String password) {
		this._userName = username;
		this._password = password;
		// TODO:Remove following comments after checking with release API.
		// Authenticator.setDefault(new Authenticator() {
		// @Override
		// protected PasswordAuthentication getPasswordAuthentication() {
		// .toCharArray());
		// }
		// });
	}

	private HttpResponse doJsonPostRequest(String url)
			throws ClientProtocolException, IOException {

		HttpResponse httpResponse = null;

		HttpConnectionParams.setConnectionTimeout(_httpClient.getParams(),
				NRApiConstants.kRequestTimeOutInMills);
		HttpConnectionParams.setSoTimeout(_httpClient.getParams(),
				NRApiConstants.kRequestTimeOutInMills);
HttpGet get = new HttpGet(url);
	//	HttpPost httpPost = new HttpPost(url);
		// Add post body.

		get.setHeader("Accept", "application/json");
		get.setHeader("Content-type", "application/json");

	//	httpPost.setEntity(se);

		// Execute the request
		Log.i("Request", "" + this._postParams);
		httpResponse = _httpClient.execute(get);

		return httpResponse;
	}

	
	@Override
	protected void onPostExecute(Object resultobj) {

		String result = "";

		if (!this.responseType.equalsIgnoreCase("STREAM")) {
			result = (String) resultobj;
		}

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(NRApiConstants.kHttpStatusKey, _httpStatusCode);

		

		if (result.equalsIgnoreCase(NRApiConstants.kNetworkError)) {

			response.put(NRApiConstants.kErrorMsgKey,
					NRApiConstants.kNetworkErrorExceptionKey);
			_apiListener.onFailedToGetResponse(response);

		} else if (result
				.equalsIgnoreCase(NRApiConstants.kApiLargeContentError)) {
			response.put(NRApiConstants.kErrorMsgKey,
					NRApiConstants.kLargeContentErrorExceptionKey);

			_apiListener.onFailedToGetResponse(response);
		} else if (result != null
				&& (_httpStatusCode == HttpStatus.SC_OK || _httpStatusCode == HttpStatus.SC_CREATED)) {
			if (this.responseType == "JSON") {
				try {
					NRBaseResponseBean responseBean = getBeanFromResult(result);

					Log.i("Response >>>>", responseBean.toJsonString());

					if (responseBean.getResultString() != null
							&& responseBean.getResultString().equalsIgnoreCase(
									NRApiConstants.kApiSuccessMsgKey) == false) {
						response.put(NRApiConstants.kApiFailedMsgKey,
								responseBean);
					} else {

						response.put(NRApiConstants.kSuccessMsgKey,
								getBeanFromResult(result));
						
						

					}

					_apiListener.onResponseReceived(response);
				} catch (Exception e) {
							response.put(NRApiConstants.kErrorMsgKey,
							getBeanFromResult(result));
					_apiListener.onFailedToGetResponse(response);

				}
			} else {
				try {
					NRBaseResponseBean responseBeanforStream = (NRBaseResponseBean) _responseClass
							.newInstance();
					responseBeanforStream.setIs((InputStream) resultobj);
					response.put(NRApiConstants.kSuccessMsgKey,
							responseBeanforStream );

					_apiListener.onResponseReceived(response);
				} catch (Exception e) {
								response.put(NRApiConstants.kErrorMsgKey, result);
					_apiListener.onFailedToGetResponse(response);

				}
			}

		} else if (_httpStatusCode == 401) {

			response.put(NRApiConstants.kErrorMsgKey,
					NRApiConstants.kAuthenticationErrorExceptionKey);

			_apiListener.onFailedToGetResponse(response);

		} else if (_httpStatusCode == 500) {

			response.put(NRApiConstants.kErrorMsgKey,
					NRApiConstants.kInternalServerErrorExceptionKey);

			_apiListener.onFailedToGetResponse(response);

		} else if (result.equalsIgnoreCase(NRApiConstants.kApiError)) {
			response.put(NRApiConstants.kErrorMsgKey,
					NRApiConstants.kTimeOutErrorExceptionKey);

			_apiListener.onFailedToGetResponse(response);

		} else {

			response.put(NRApiConstants.kApiFailedMsgKey,
					getBeanFromResult(result));

			_apiListener.onResponseReceived(response);
		}

	}

	@SuppressWarnings("unchecked")
	private NRBaseResponseBean getBeanFromResult(String result) {
		Gson gson = new Gson();
		try {
			return (NRBaseResponseBean) gson.fromJson(result, _responseClass);
		} catch (Exception ex) {
			NRBaseResponseBean bean = null;
			try {
				bean = (NRBaseResponseBean) _responseClass.newInstance();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}

			bean.setResponseCode(NRApiConstants.kApiFailedCode);
			NRResponseMessagesBean resBean = new NRResponseMessagesBean();
			NRErrorMessageResponseBean errBean = new NRErrorMessageResponseBean();
			errBean.setCode(NRApiConstants.kApiFailedMsgKey);
			errBean.setContent("Error");
			ArrayList<NRErrorMessageResponseBean> errArray = new ArrayList<NRErrorMessageResponseBean>();
			errArray.add(errBean);
			resBean.setErrorMessages(errArray);
			bean.setResponseMessages(resBean);
			return bean;
		}
	}

}
