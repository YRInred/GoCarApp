package com.hxyr.gocarapp.entity;

import java.text.MessageFormat;
import java.util.HashMap;

import com.android.volley.Request;
import com.hxyr.gocarapp.entity.parser.BaseParser;

public class RequestEntitiy {
	
	private int tagInt;
	private String tag = "";//请求头
    private String url;//请求url
    private int requestMethod = Request.Method.GET;//默认为get方法
    private HashMap<String,String> params;//参数
    private BaseParser parser;

    
    
	public int getTagInt() {
		return tagInt;
	}

	public RequestEntitiy setTagInt(int tagInt) {
		this.tagInt = tagInt;
		return this;
	}

	public String getTag() {
		return tag;
	}

	public RequestEntitiy setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public String getUrl() {
        return url;
    }

    public RequestEntitiy setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getRequestMethod() {
        return requestMethod;
    }

    public RequestEntitiy setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public RequestEntitiy setParams(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public <T> BaseParser<T> getParser() {
		return parser;
	}

	public <T> RequestEntitiy setParser(BaseParser<T> parser) {
		this.parser = parser;
		return this;
	}

	@Override
    public String toString() {
        String s = MessageFormat.format("index = {0}, url = {1}, requestMethod = {2}",tag.toString(),url, requestMethod);
        return s;
    }
	
}
