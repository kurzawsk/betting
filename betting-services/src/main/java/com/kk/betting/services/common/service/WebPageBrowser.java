package com.kk.betting.services.common.service;

import com.kk.betting.services.common.util.CommonConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;

public class WebPageBrowser {
    public static final int DEFAULT_MAX_CONNECTIONS = 8;
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private HttpClient httpClient;

    public WebPageBrowser() {
        HttpClientParams params = new HttpClientParams();
        params.setConnectionManagerTimeout(DEFAULT_TIMEOUT);
        params.setSoTimeout(DEFAULT_TIMEOUT);
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        manager.getParams().setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONS);
        manager.getParams().setMaxTotalConnections(DEFAULT_MAX_CONNECTIONS);
        httpClient = new HttpClient(params, manager);
    }

    public String doGet(String url) {
        return doGet(url, Collections.EMPTY_MAP);
    }

    public String doGet2(String url, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = getReader(url, headers)) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append(CommonConstants.NEW_LINE_SYMBOL);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not get " + url + " content", e);
        }

        return sb.toString();
    }

    public String doGet(String url, Map<String, String> headers) {
        try {
            GetMethod getMethod = new GetMethod(url);
            headers.forEach((k, v) -> getMethod.addRequestHeader(k, v));
            return getHttpMethodResult(getMethod);
        } catch (Exception e) {
            throw new RuntimeException("Could not get " + url, e);
        }
    }

    public String doPostForm(String url, Map<String, Object> params) {
        try {
            PostMethod postMethod = new PostMethod(url);


            params.forEach((k, v) -> postMethod.addParameter(k, v.toString()));
            return getHttpMethodResult(postMethod);
        } catch (Exception e) {
            throw new RuntimeException("Could not post " + url, e);
        }
    }

    private String getHttpMethodResult(HttpMethod method) throws IOException {
        httpClient.executeMethod(method);
        if (method.getStatusCode() != HttpStatus.SC_OK) {
            throw new RuntimeException("Response code for " + method.getName() + " is: " + method.getStatusCode());
        }
        StringBuilder sb = new StringBuilder();
        try (InputStream is = method.getResponseBodyAsStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, DEFAULT_ENCODING))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(CommonConstants.NEW_LINE_SYMBOL);
            }
        }
        return sb.toString();
    }


    private BufferedReader getReader(String url, Map<String, String> headers) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setConnectTimeout(DEFAULT_TIMEOUT);
        connection.setReadTimeout(DEFAULT_TIMEOUT);
        headers.forEach(connection::setRequestProperty);
        return new BufferedReader(new InputStreamReader(connection.getInputStream(), DEFAULT_ENCODING));
    }

}
