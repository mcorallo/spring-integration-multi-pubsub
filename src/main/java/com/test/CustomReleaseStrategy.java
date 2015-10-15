package com.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class CustomReleaseStrategy implements ReleaseStrategy, CorrelationStrategy {

	@Aggregator
	public Object aggregate ( List<Message<?>> objects ) {

		List<Object> res = new ArrayList<> ();
		for ( Message<?> m : objects ) {
			res.add ( m.getPayload () );
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canRelease ( MessageGroup group ) {

		//size() == iterator().next().headers['sequenceSize'] * iterator().next().headers['sequenceDetails'][0][2]

		Message<?> oneMessage = group.getOne ();
		MessageHeaders headers = oneMessage.getHeaders ();
		int currentSize = (int) headers.get ( "sequenceSize" );
		int nestedSize = (int) ( (List<List<Object>>) headers.get ( "sequenceDetails" ) ).get ( 0 ).get ( 2 );
		return group.size () == currentSize * nestedSize;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getCorrelationKey ( Message<?> message ) {

		//headers['sequenceDetails'][0][0]
		return ( (List<List<Object>>) message.getHeaders ().get ( "sequenceDetails" ) ).get ( 0 ).get ( 0 );
	}

}
