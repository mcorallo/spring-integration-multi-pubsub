package com.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Gateway;

public class AsyncService2 {

	@Autowired
	private ServiceGateway2<Integer, List<Integer>> gateway;

	@Gateway
	public String process ( Object input ) {

		List<Integer> send = gateway.send ( 0 );

		return send.toString ();
	}
}
