package com.hxyr.gocarapp.apiiml;

import com.android.volley.VolleyError;

public interface RequestListener {
		
	void onResponse(int tagInt,String tag,Object entity);
    void onErrorResponse(Object tag,VolleyError volleyError);
	
}
