package com.chen.email.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotifyTalentTestEmailServiceImpl extends SendCloudEmailService<NotifyTalentTestEntity> {

	public String convertDataList(List<NotifyTalentTestEntity> dataList) {

		JSONObject content = new JSONObject();
		
		JSONArray addressArray = new JSONArray();
		JSONArray nameArray = new JSONArray();
		JSONArray companyNameArray = new JSONArray();
		JSONArray positionNameArray = new JSONArray();
		JSONArray expirationArray = new JSONArray();
		JSONArray timeLimitArray = new JSONArray();
		JSONArray testUrlArray = new JSONArray();
		JSONArray imageUrlArray = new JSONArray();
		
		for (NotifyTalentTestEntity entity : dataList) {
			addressArray.put(entity.getAddress());
			nameArray.put(entity.getName());
			companyNameArray.put(entity.getCompanyName());
			positionNameArray.put(entity.getPositionName());
			expirationArray.put(entity.getExpiration());
			timeLimitArray.put(entity.getTimeLimit());
			testUrlArray.put(entity.getTestUrl());
			imageUrlArray.put(entity.getName());
		}

		JSONObject sub = new JSONObject();
		//sub.put("%name%", nameArray);
		sub.put("%companyName%", companyNameArray);
		sub.put("%positionName%", positionNameArray);
		sub.put("%expiration%", expirationArray);
		sub.put("%timeLimit%", timeLimitArray);
		sub.put("%testUrl%", testUrlArray);
		//sub.put("%imageUrl%", imageUrlArray);
		
		content.put("to", addressArray);
		content.put("sub", sub);

		System.out.println(content.toString());
		return content.toString();
	}


	
}
