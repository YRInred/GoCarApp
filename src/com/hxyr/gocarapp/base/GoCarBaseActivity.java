package com.hxyr.gocarapp.base;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hxyr.gocarapp.apiiml.RequestListener;
import com.hxyr.gocarapp.application.GoCarApplication;
import com.hxyr.gocarapp.entity.RequestEntitiy;
import com.hxyr.gocarapp.util.GoCarLog;

public abstract class GoCarBaseActivity extends Activity{
	
	protected List<RequestEntitiy> requestEntitiys;
	private RequestListener requestListener;
	
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        initData();
	        setContentView(main_layout());
	        initView();
	        initVoid();
	    }
	  
	  /**
	   * 初始化数据
	   */
	  protected void initData() {
		  addRequest(requestEntitiys);
	  }
	  
	  	
	  /**
	   * layoutID
	   * @return
	   */
	  protected abstract int main_layout();
	  
	  /**
	   * 初始化视图
	   */
	  protected abstract void initView();
	  
	    /**
	     * 初始化方法
	     */
	    protected void initVoid() {
	    }
	    
	    /**
	     * showToast
	     * @param s
	     */
	    protected void showToast(String s) {
	        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	    }
	    
	    
	    /**
	     * 基础跳转Activity
	     * @param activity
	     */
	    protected <T> void toActivity(Class<T> activity){
	        Intent intent = new Intent(this,activity);
	        startActivity(intent);
	    }
	    
	    /**
	     * 设置网络请求监听
	     * @param requestListener
	     */
	    protected void setRequestListener(RequestListener requestListener){
	    	this.requestListener = requestListener;
	    }
	    
	    /**
	     * 添加网络请求
	     * @param requestEntitiys
	     */
	    private void addRequest(List<RequestEntitiy> requestEntitiys) {
	        if (requestEntitiys == null || requestEntitiys.isEmpty())
	            return;

	        StringRequest stringRequest;
	        for (final RequestEntitiy entitiy : requestEntitiys) {
	        	GoCarLog.printError("entity="+entitiy.toString());
	            stringRequest = new StringRequest(
	                    entitiy.getRequestMethod(),
	                    entitiy.getUrl(),
	                    new Response.Listener<String>() {
	                        @Override
	                        public void onResponse(String s) {
	                        	
	                        	requestListener.onResponse(entitiy.getTagInt(),
	                        			entitiy.getTag(),
	                        			entitiy.getParser().doParser(s));
	                        }
	                    },
	                    new Response.ErrorListener() {
	                        @Override
	                        public void onErrorResponse(VolleyError volleyError) {
	                        	requestListener.onErrorResponse(entitiy.getTag(), volleyError);
	                        }
	                    })
	            {
	                @Override
	                protected Map<String, String> getParams() throws AuthFailureError {
	                    if (entitiy.getParams() == null || entitiy.getParams().isEmpty())
	                        return super.getParams();
	                    return entitiy.getParams();
	                }
	            };
	            GoCarApplication.getInstance().addToRequestQueue(stringRequest, entitiy.getTag());
	        }
	    }
	    
	

}
