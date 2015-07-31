package com.hxyr.gocarapp.entity.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.hxyr.gocarapp.entity.User;

public class UserParser extends BaseParser<User>{

	@Override
	public User doParser(String s) {
		User user = new User();
		
		try {
			JSONObject json = new JSONObject(s);
			code = json.getInt("code");
			if (code == 0) {
				JSONObject data = json.optJSONObject("data");
				user.setId(data.optInt("id"));
				user.setName(data.optString("name"));
			}else
				return null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	
	
}
