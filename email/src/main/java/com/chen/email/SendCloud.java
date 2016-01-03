package com.chen.email;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

// http://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime
// http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient

class B {
	String address;
	String name;
	String money;

	B(String address, String name, String money) {
		this.address = address;
		this.name = name;
		this.money = money;
	}
}

public class SendCloud {

	public static String convert(List<B> dataList) {

		JSONObject ret = new JSONObject();

		JSONArray to = new JSONArray();

		JSONArray names = new JSONArray();
		JSONArray moneys = new JSONArray();

		for (B a : dataList) {
			to.put(a.address);
			names.put(a.name);
			moneys.put(a.money);
		}

		JSONObject sub = new JSONObject();
		sub.put("%name%", names);
		sub.put("%money%", moneys);

		ret.put("to", to);
		ret.put("sub", sub);

		return ret.toString();
	}

	public static void send_common() throws IOException {

		final String url = "http://sendcloud.sohu.com/webapi/mail.send.json";

		final String apiUser = "***";
		final String apiKey = "***";
		final String rcpt_to = "***";

		String subject = "...";
		String html = "...";

		HttpPost httpPost = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_user", apiUser));
		params.add(new BasicNameValuePair("api_key", apiKey));
		params.add(new BasicNameValuePair("to", rcpt_to));
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		params.add(new BasicNameValuePair("fromname", "SendCloud"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("html", html));
		params.add(new BasicNameValuePair("resp_email_id", "true"));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

	public static void send_common_with_attachment() throws ClientProtocolException, IOException {

		final String url = "http://sendcloud.sohu.com/webapi/mail.send.json";

		final String apiUser = "***";
		final String apiKey = "***";
		final String rcpt_to = "***";

		String subject = "...";
		String html = "...";

		HttpPost httpPost = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();

		// 涉及到附件上传, 需要使用 MultipartEntity
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
		entity.addPart("api_user", new StringBody(apiUser, Charset.forName("UTF-8")));
		entity.addPart("api_key", new StringBody(apiKey, Charset.forName("UTF-8")));
		entity.addPart("to", new StringBody(rcpt_to, Charset.forName("UTF-8")));
		entity.addPart("from", new StringBody("sendcloud@sendcloud.org", Charset.forName("UTF-8")));
		entity.addPart("fromname", new StringBody("SendCloud", Charset.forName("UTF-8")));
		entity.addPart("subject", new StringBody(subject, Charset.forName("UTF-8")));
		entity.addPart("html", new StringBody(html, Charset.forName("UTF-8")));
		entity.addPart("resp_email_id", new StringBody("true"));

		// 添加附件
		File file = new File("/home/liubida/Desktop/file");
		FileBody attachment = new FileBody(file, "application/octet-stream", "UTF-8");
		entity.addPart("files", attachment);

		// 添加附件, 文件流形式
		// File file = new File("/path/file");
		// String attachName = "attach.txt";
		// InputStreamBody is = new InputStreamBody(new FileInputStream(file),
		// attachName);
		// entity.addPart("files", is);

		httpPost.setEntity(entity);

		HttpResponse response = httpClient.execute(httpPost);
		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

	public static void send_template() throws ClientProtocolException, IOException {

		final String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";

		final String apiUser = "***";
		final String apiKey = "***";

		String subject = "...";

		List<B> dataList = new ArrayList<B>();
		dataList.add(new B("to1@domain.com", "user1", "1000"));
		dataList.add(new B("to2@domain.com", "user2", "2000"));

		final String vars = convert(dataList);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_user", apiUser));
		params.add(new BasicNameValuePair("api_key", apiKey));
		params.add(new BasicNameValuePair("substitution_vars", vars));
		params.add(new BasicNameValuePair("template_invoke_name", "test_template"));
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		params.add(new BasicNameValuePair("fromname", "SendCloud"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("resp_email_id", "true"));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

	public static void send_template_maillist() throws ClientProtocolException, IOException {

		final String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";

		final String apiUser = "***";
		final String apiKey = "***";
		final String to = "***";

		String subject = "...";

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("api_user", apiUser));
		params.add(new BasicNameValuePair("api_key", apiKey));
		params.add(new BasicNameValuePair("to", to));
		params.add(new BasicNameValuePair("template_invoke_name", "test_template"));
		params.add(new BasicNameValuePair("from", "sendcloud@sendcloud.org"));
		params.add(new BasicNameValuePair("fromname", "SendCloud"));
		params.add(new BasicNameValuePair("subject", subject));
		params.add(new BasicNameValuePair("use_maillist", "true"));
		params.add(new BasicNameValuePair("resp_email_id", "true"));

		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = httpClient.execute(httpPost);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpPost.releaseConnection();
	}

	public static void send_template_with_attachment() throws ClientProtocolException, IOException {

		final String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";

		final String apiUser = "***";
		final String apiKey = "***";

		String subject = "...";

		List<B> dataList = new ArrayList<B>();
		dataList.add(new B("to1@domain.com", "user1", "1000"));
		dataList.add(new B("to2@domain.com", "user2", "2000"));

		final String vars = convert(dataList);

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);

		// 涉及到附件上传, 需要使用 MultipartEntity
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"));
		entity.addPart("api_user", new StringBody(apiUser, Charset.forName("UTF-8")));
		entity.addPart("api_key", new StringBody(apiKey, Charset.forName("UTF-8")));
		entity.addPart("substitution_vars", new StringBody(vars, Charset.forName("UTF-8")));
		entity.addPart("template_invoke_name", new StringBody("test_template", Charset.forName("UTF-8")));
		entity.addPart("from", new StringBody("sendcloud@sendcloud.org", Charset.forName("UTF-8")));
		entity.addPart("fromname", new StringBody("SendCloud", Charset.forName("UTF-8")));
		entity.addPart("subject", new StringBody(subject, Charset.forName("UTF-8")));
		entity.addPart("resp_email_id", new StringBody("true"));

		// 添加附件
		File file = new File("/home/liubida/Desktop/1");
		FileBody attachment = new FileBody(file, "application/octet-stream", "UTF-8");
		entity.addPart("files", attachment);

		// 添加附件, 文件流形式
		// File file = new File("/path/file");
		// String attachName = "attach.txt";
		// InputStreamBody is = new InputStreamBody(new FileInputStream(file),
		// attachName);
		// entity.addPart("files", is);

		httpost.setEntity(entity);

		HttpResponse response = httpclient.execute(httpost);
		// 处理响应
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 正常返回, 解析返回数据
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			System.err.println("error");
		}
		httpost.releaseConnection();
	}

	public static void main(String[] args) throws Exception {
		send_common();
		// send_common_with_attachment();
		// send_template_with_attachment();
		// send_template();
		// send_template_maillist();
	}
}
