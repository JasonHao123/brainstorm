package com.mkyong.helloworld.service;

import org.apache.ofbiz.base.start.Config;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class Initializer extends AbstractHttpSessionApplicationInitializer { 

	public Initializer() {
		super(Config.class); 
	}
}
