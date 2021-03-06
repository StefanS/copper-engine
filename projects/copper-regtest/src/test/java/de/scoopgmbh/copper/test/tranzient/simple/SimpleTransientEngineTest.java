/*
 * Copyright 2002-2013 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.copper.test.tranzient.simple;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.scoopgmbh.copper.DuplicateIdException;
import de.scoopgmbh.copper.EngineState;
import de.scoopgmbh.copper.Workflow;
import de.scoopgmbh.copper.WorkflowInstanceDescr;
import de.scoopgmbh.copper.test.TestResponseReceiver;
import de.scoopgmbh.copper.test.backchannel.BackChannelQueue;
import de.scoopgmbh.copper.test.backchannel.WorkflowResult;
import de.scoopgmbh.copper.tranzient.TransientScottyEngine;
import de.scoopgmbh.copper.util.BlockingResponseReceiver;

public class SimpleTransientEngineTest {
	
	private final int[] response = { -1 };

	@Test
	public void testWorkflow() throws Exception {
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"transient-engine-application-context.xml", "SimpleTransientEngineTest-application-context.xml"});
		TransientScottyEngine engine = context.getBean("transientEngine", TransientScottyEngine.class);
		context.getBeanFactory().registerSingleton("OutputChannel4711",new TestResponseReceiver<String, Integer>() {
			@Override
			public void setResponse(Workflow<String> wf, Integer r) {
				synchronized (response) {
					response[0] = r.intValue();
					response.notifyAll();
				}
			}
		});

		assertEquals(EngineState.STARTED,engine.getEngineState());
		
		try {
			BlockingResponseReceiver<Integer> brr = new BlockingResponseReceiver<Integer>();
			engine.run("de.scoopgmbh.copper.test.tranzient.simple.SimpleTransientWorkflow", brr);
			synchronized (response) {
				if (response[0] == -1) {
					response.wait(30000);
				}
			}
			assertEquals(10, response[0]);
		}
		finally {
			context.close();
		}
		assertEquals(EngineState.STOPPED,engine.getEngineState());
		
	}
	
	
	@Test(expected=DuplicateIdException.class)
	public void testDuplicateIdException() throws Exception {
		final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"transient-engine-application-context.xml", "SimpleTransientEngineTest-application-context.xml"});
		final TransientScottyEngine engine = (TransientScottyEngine) context.getBean("transientEngine");
		
		assertEquals(EngineState.STARTED,engine.getEngineState());
		
		try {
			engine.run(new WorkflowInstanceDescr<String>("de.scoopgmbh.copper.test.tranzient.simple.VerySimpleTransientWorkflow", "data", "singleton", null,
					null));
			engine.run(new WorkflowInstanceDescr<String>("de.scoopgmbh.copper.test.tranzient.simple.VerySimpleTransientWorkflow", "data", "singleton", null,
					null));
		}
		finally {
			context.close();
		}
		assertEquals(EngineState.STOPPED,engine.getEngineState());
		
	}
}
