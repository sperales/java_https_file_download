package com.csi.mobiaxiscore.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
import org.apache.http.util.ByteArrayBuffer;
 
import android.util.Log;
 
public class DownloadFile {
 
        private final String PATH = ""; 
       
 
        public void DownloadFromUrl(String fileURL, String fileName) { 
                try {
                        File file = new File(PATH + fileName);
 
                        long startTime = System.currentTimeMillis();
                        Log.d("ImageManager", "download begining");
                        Log.d("ImageManager", "downloaded file name:" + fileName);
                        /* Open a connection to that URL. */
                        //URL url = new URL(fileURL);
                        //URLConnection ucon = url.openConnection();
                        try{
	                        SSLContext ctx = SSLContext.getInstance("TLS");
                        
	                        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	                        	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                        		return null;
	                        	}
	                        	public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                        	}
	                        	public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                        	}
	                        }};
	
	                        ctx.init(null, trustAllCerts, new java.security.SecureRandom());
	                        HttpsURLConnection.setDefaultSSLSocketFactory(ctx.getSocketFactory());
	                        HostnameVerifier allHostsValid = new HostnameVerifier() {
	                        public boolean verify(String hostname, SSLSession session) {
		                        return true;
	                        }};
	                        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                        }
                        catch(Exception ex){
                        	Log.d("ERROR",ex.getMessage());
                        }
                        URL url = new URL(fileURL);
                        HttpsURLConnection ucon = (HttpsURLConnection) url.openConnection();
 
                        /*
                         * Define InputStreams to read from the URLConnection.
                         */
                        InputStream is = ucon.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
 
                        /*
                         * Read bytes to the Buffer until there is nothing more to read(-1).
                         */
                        ByteArrayBuffer baf = new ByteArrayBuffer(50);
                        int current = 0;
                        while ((current = bis.read()) != -1) {
                                baf.append((byte) current);
                        }
 
                        /* Convert the Bytes read to a String. */
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(baf.toByteArray());
                        fos.close();
                        Log.e("ImageManager", "download ready in"
                                        + ((System.currentTimeMillis() - startTime) / 1000)
                                        + " sec");
 
                } catch (IOException e) {
                        Log.d("ImageManager", "Error: " + e);
                }
 
        }
}
