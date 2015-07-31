package com.hxyr.gocarapp.entity.parser;

public abstract class BaseParser<T> {
		
	protected int code = -1;
	
	public abstract T doParser(String s);
		
}
