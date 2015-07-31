package com.hxyr.gocarapp.entity.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.hxyr.gocarapp.entity.IosVersion;

public class IosVersionParser extends BaseParser<IosVersion>{

	@Override
	public IosVersion doParser(String s) {
		IosVersion version = new IosVersion();
		
		
		try {
			JSONObject json = new JSONObject(s);

			version.setVersion(json.optString("data"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return version;
	}

}
