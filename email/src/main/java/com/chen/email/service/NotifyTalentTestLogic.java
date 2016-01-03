package com.chen.email.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public class NotifyTalentTestLogic {

	public static void main(String[] args) throws Exception {
		ISendCloudEmailService<NotifyTalentTestEntity> emailService = new NotifyTalentTestEmailServiceImpl();
		List<NotifyTalentTestEntity> dataList = new ArrayList<NotifyTalentTestEntity>();
		String address = "953899919@qq.com";
		String companyName = "oo公司";
		String positionName = "xx职位";
		String expiration = "2011-10-30";
		String timeLimit = "30";
		String testUrl = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%BF%AA%E6%A1%91%E5%88%A9%E4%BA%9A&step_word=&pn=4&spn=0&di=24580166050&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2269618465%2C2307635077&os=3956656351%2C3282410130&simid=3446648415%2C234656194&adpicid=0&ln=50&fr=ala&fmq=1449213024431_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&objurl=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2Fday_120514%2F1205141821119965d4d846e79a.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fkkf_z%26e3B2uwg_z%26e3Bv54AzdH3Fwg165t1-9d89lbc-8-8_z%26e3Bip4s&gsm=0";
		String imageUrl = "30";
		
		
		NotifyTalentTestEntity entity1 = new NotifyTalentTestEntity(address,companyName,positionName,expiration,timeLimit,
				testUrl,imageUrl);
		String address2 = "953899919@qq.com";
		String testUrl2 = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E8%BF%AA%E6%A1%91%E5%88%A9%E4%BA%9A&step_word=&pn=6&spn=0&di=189784219900&pi=&rn=1&tn=baiduimagedetail&is=&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2480429601%2C1757226737&os=240048942%2C3706353894&simid=4243057811%2C674768932&adpicid=0&ln=50&fr=ala&fmq=1449213024431_R&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D73d4904a15ce36d3a20483380af23a24%2Fb90e7bec54e736d1074fe0989b504fc2d46269c5.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fptjkw_z%26e3Bkwt17_z%26e3Bv54AzdH3FrAzdH3F808lannc9l&gsm=0";
		NotifyTalentTestEntity entity2 = new NotifyTalentTestEntity(address2,companyName,positionName,expiration,timeLimit,
				testUrl2,imageUrl);
		dataList.add(entity1);
		dataList.add(entity2);
		
		String vars = emailService.convertDataList(dataList);
		String templateName = "notify_talent_test_template";
		String fromUserEmail = "yygu@icepin.com";
		String fromUserName = "yyg";
		String subject = "笔试通知";
		List<NameValuePair> NameValuePairList = emailService.getCommonNameValuePair(vars, templateName, fromUserEmail, fromUserName, subject);
		emailService.sendEmialByTemplate(NameValuePairList);
		
	}
}
