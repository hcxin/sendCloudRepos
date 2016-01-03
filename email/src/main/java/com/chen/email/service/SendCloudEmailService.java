package com.chen.email.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public abstract class SendCloudEmailService<T> implements ISendCloudEmailService<T> {
	protected final static String TEMPLATE_URL = "http://sendcloud.sohu.com/webapi/mail.send_template.json";
	protected final static String API_USER = "your default api_user";
	protected final static String API_KEY = "your api_key";

	/**
	 * 此方法支持参数
	 */
	public List<NameValuePair> getCommonNameValuePair(String vars,
			String templateName, String fromUserEmail, String fromUserName,
			String subject) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("api_user", API_USER));
		params.add(new BasicNameValuePair("api_key", API_KEY));
		params.add(new BasicNameValuePair("substitution_vars", vars));//在 use_maillist=false 时使用
		params.add(new BasicNameValuePair("template_invoke_name", templateName));
		params.add(new BasicNameValuePair("from", fromUserEmail));
		params.add(new BasicNameValuePair("fromname", fromUserName));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("resp_email_id", "true"));
		return params;
	}
	
	/**
	 * 此方法不支持参数
	 */
	public List<NameValuePair> getNameValuePairWithMailList(String templateName, String fromUserEmail, 
			String fromUserName, String subject,String toMailList) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("api_user", API_USER));
		params.add(new BasicNameValuePair("api_key", API_KEY));
		params.add(new BasicNameValuePair("template_invoke_name", templateName));
		params.add(new BasicNameValuePair("to", toMailList));//不能与substitution_vars同时使用 
		params.add(new BasicNameValuePair("from", fromUserEmail));
		params.add(new BasicNameValuePair("fromname", fromUserName));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("use_maillist", "true"));
		params.add(new BasicNameValuePair("resp_email_id", "true"));
		return params;
	}
	
	/**
	 * sendcloud 发邮件公共方法
	 */
	public void sendEmialByTemplate(List<NameValuePair> params) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(TEMPLATE_URL);
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 200 is success
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		
		httpPost.releaseConnection();
	}
}
