package com.chen.email;

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

public class SendCloudService {
	final static String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";

    final static String apiUser = "haichen_test_HQzi5N";
    final static String apiKey = "LOJ1Pu7018LQ3aat";
    final static String rcpt_to = "hcxin@icepin.com";
	public static void main(String[] args) throws ClientProtocolException, IOException {


	    HttpPost httpost = new HttpPost(url);
	    HttpClient httpclient = new DefaultHttpClient();

	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user", apiUser));
	    params.add(new BasicNameValuePair("api_key", apiKey));
	    params.add(new BasicNameValuePair("to", rcpt_to));
	    params.add(new BasicNameValuePair("from", "haichen086@163.com"));
	    params.add(new BasicNameValuePair("fromname", "haichenxxx"));
	    params.add(new BasicNameValuePair("subject", "SendCloud java common"));
	    //params.add(new BasicNameValuePair("html", "test html"));
	    params.add(new BasicNameValuePair("resp_email_id", "true"));
	    params.add(new BasicNameValuePair("template_invoke_name", "test_template"));
	    httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

	    HttpResponse response = httpclient.execute(httpost);
	    System.out.println(response.getStatusLine().getStatusCode());
	    System.out.println(response.getEntity());
	    // response
	    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
	        System.out.println(EntityUtils.toString(response.getEntity()));
	    } else {
	        System.out.println(response.getStatusLine().getStatusCode());
	    }
	    httpost.releaseConnection();
	
	}
}
