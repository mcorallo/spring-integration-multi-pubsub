package com.test;

import org.springframework.integration.annotation.ServiceActivator;

public class Transformer {

	@ServiceActivator
	public Integer temp ( Integer input ) throws InterruptedException {

		int i = input + 1;
		return i;

	}

}
