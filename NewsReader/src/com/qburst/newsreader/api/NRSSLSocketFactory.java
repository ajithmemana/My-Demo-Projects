package com.qburst.newsreader.api;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class NRSSLSocketFactory extends SSLSocketFactory{

    SSLContext sslContext = SSLContext.getInstance("TLS");

	public NRSSLSocketFactory(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(truststore);

		TrustManager tm = new TrustManager() {
			
			 @SuppressWarnings("unused")
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            @SuppressWarnings("unused")
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            @SuppressWarnings("unused")
				public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
		};
		
        sslContext.init(null, new TrustManager[] { tm }, null);

	}
	@Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
 }
    
	
	
}


