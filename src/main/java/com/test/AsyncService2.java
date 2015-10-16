package com.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class AsyncService2 {

	@Autowired
	private ServiceGateway2<Integer, List<Integer>> gateway;

	public List<Integer> process ( Object input ) {

		List<Integer> result = gateway.send ( 0 );

		return result;
	}
}
