package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"classpath*:spring/integration-context*.xml"
})
public class SpringTest {

	@Autowired
	private ApplicationContext context;

	@Test
	public void testName () throws Exception {

		AsyncService2 as2 = context.getBean ( AsyncService2.class );
		String process = as2.process ( null );
		System.out.println ( process );
	}

}
