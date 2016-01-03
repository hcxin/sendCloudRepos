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
import org.json.JSONArray;
import org.json.JSONObject;
class EmailTestParam {
    String address;
    String name;
    String money;

    EmailTestParam(String address, String name, String money) {
        this.address = address;
        this.name = name;
        this.money = money;
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
    	send_template();
    }
    
    
public static String convert(List<EmailTestParam> dataList) {

    JSONObject ret = new JSONObject();

    JSONArray to = new JSONArray();

    JSONArray names = new JSONArray();
    JSONArray moneys = new JSONArray();

    for (EmailTestParam a : dataList) {
        to.put(a.address);
        names.put(a.name);
        moneys.put(a.money);
    }

    JSONObject sub = new JSONObject();
    sub.put("%name%", names);
    sub.put("%money%", moneys);

    ret.put("to", to);
    ret.put("sub", sub);
System.out.println(ret.toString());
    return ret.toString();
}

public static void send_template() throws ClientProtocolException, IOException {

    final String url = "http://sendcloud.sohu.com/webapi/mail.send_template.json";

    final String apiUser = "haichen_test_HQzi5N";
    final String apiKey = "LOJ1Pu7018LQ3aat";
    List<EmailTestParam> dataList = new ArrayList<EmailTestParam>();
    dataList.add(new EmailTestParam("hcxin@icepin.com", "<p> <a href='http://sendcloud.sohu.com'>SendCloud!</a></p>", "1000"));
    dataList.add(new EmailTestParam("xyi@icepin.com", "<p> gggg</p>", "1000"));
    dataList.add(new EmailTestParam("yygu@icepin.com", "<p> oooooooooooxxxxxxxxxx</p>", "1000"));

    final String vars = convert(dataList);

    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httpost = new HttpPost(url);

    List<NameValuePair> params = new ArrayList<NameValuePair>();  
    params.add(new BasicNameValuePair("api_user", apiUser));
    params.add(new BasicNameValuePair("api_key", apiKey));
    //params.add(new BasicNameValuePair("use_maillist", "true"));
    params.add(new BasicNameValuePair("substitution_vars", vars));
    //params.add(new BasicNameValuePair("to", rcpt_to));
    params.add(new BasicNameValuePair("template_invoke_name", "company_template"));
    params.add(new BasicNameValuePair("from", "haichen089@163.com"));
    params.add(new BasicNameValuePair("fromname", "haichenoooo"));
    params.add(new BasicNameValuePair("subject", "笔试通知"));
    //params.add(new BasicNameValuePair("html", "<html><head></head><body>" + "<p><a href='http://sendcloud.sohu.com'>SendCloud!</a></p>" + "</body></html>"));
    //params.add(new BasicNameValuePair("html", "��ӭʹ��SendCloud"));
    params.add(new BasicNameValuePair("resp_email_id", "true"));

    httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

    HttpResponse response = httpclient.execute(httpost);

    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        System.out.println(EntityUtils.toString(response.getEntity()));
    } else {
        System.err.println("error");
    }

    httpost.releaseConnection();
}
}