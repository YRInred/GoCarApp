package com.hxyr.gocarapp.activity;


import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.hxyr.gocarapp.R;
import com.hxyr.gocarapp.apiiml.RequestListener;
import com.hxyr.gocarapp.base.GoCarBaseActivity;
import com.hxyr.gocarapp.constant.GoCarConstant;
import com.hxyr.gocarapp.entity.IosVersion;
import com.hxyr.gocarapp.entity.RequestEntitiy;
import com.hxyr.gocarapp.entity.User;

public class MainActivity extends GoCarBaseActivity implements RequestListener {
	
	private IosVersion version;
	private final int GETVERSION = 0x166;
	
	@Override
	protected void initData() {
		requestEntitiys = new ArrayList<RequestEntitiy>(); 
		
		requestEntitiys.add(new RequestEntitiy()
		.setUrl(GoCarConstant.SERVER_PREFIX+"Service/GetIOSVersion")
		.setTagInt(GETVERSION)
		);
		
		super.initData();
	}
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int main_layout() {
		// TODO Auto-generated method stub
		return R.layout.activity_main;
	}

	@Override
	public void onResponse(int tagInt,String tag,Object entity) {
		switch (tagInt) {
		case GETVERSION:
			version = (IosVersion) entity;
			showToast(version.getVersion());
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorResponse(Object tag, VolleyError volleyError) {
		// TODO Auto-generated method stub
		
	}


	
	

}
