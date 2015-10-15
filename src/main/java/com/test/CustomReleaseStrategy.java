package com.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public class CustomReleaseStrategy implements ReleaseStrategy {

	@Aggregator
	public Object aggregate ( List<Message<?>> objects ) {

		List<Object> res = new ArrayList<> ();
		for ( Message<?> m : objects ) {
			res.add ( m.getPayload () );
			System.out.println ( m.getHeaders ().get ( "history" ) );
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canRelease ( MessageGroup group ) {

		MessageHeaders headers = group.getOne ().getHeaders ();
		List<List<Object>> sequenceDetails = (List<List<Object>>) headers.get ( "sequenceDetails" );
		System.out.println ( sequenceDetails );

		int expectedSize = 1;
		for ( List<Object> sequenceItem : sequenceDetails ) {
			if ( sequenceItem.get ( 1 ) != sequenceItem.get ( 2 ) ) {
				System.out.println ( "--> AGG: no release check, group max not reached" );
				return false;
			}
			expectedSize *= (int) sequenceItem.get ( 2 );//multiplies the group sizes
		}

		int expectedSize2 = expectedSize * (int) headers.get ( "sequenceSize" );

		int currentSize = group.size () * expectedSize;
		System.out.println ( "--> AGG: " + expectedSize2 + " : " + currentSize );
		boolean canRelease = expectedSize2 == currentSize;
		if ( canRelease ) {
			System.out.println ( "ok" );
		}
		return canRelease;
	}

}
