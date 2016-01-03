package com.chen.email.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

public interface ISendCloudEmailService<T> {
	public void sendEmialByTemplate(List<NameValuePair> params)
			throws ClientProtocolException, IOException;

	public List<NameValuePair> getCommonNameValuePair(String vars,
			String templateName, String fromUserEmail, String fromUserName,
			String subject);

	public List<NameValuePair> getNameValuePairWithMailList(String templateName, String fromUserEmail, 
			String fromUserName, String subject,String toMailList);

	public String convertDataList(List<T> dataList);
}
