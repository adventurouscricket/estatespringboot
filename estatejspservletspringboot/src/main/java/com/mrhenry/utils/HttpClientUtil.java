package com.mrhenry.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class HttpClientUtil {

    final static Logger logger = Logger.getLogger(HttpClientUtil.class);

    public static String httpGet(String url) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpRequest = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode >= 200 && statusCode <= 300){
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity);
            }
        } catch (IOException e) {
            logger.info("ERROR execute API: "+ e.getMessage());
        }
        return null;
    }
}
