/*
 * Copyright 2002-2012 SCOOP Software GmbH
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

import junit.framework.TestCase;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.scoopgmbh.copper.EngineState;
import de.scoopgmbh.copper.Workflow;
import de.scoopgmbh.copper.WorkflowFactory;
import de.scoopgmbh.copper.test.TestResponseReceiver;
import de.scoopgmbh.copper.tranzient.TransientScottyEngine;

public class AbstractIssueTest extends TestCase {
	
	private final int[] response = { -1 };

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
			WorkflowFactory<CompletionIndicator> wfFactory = engine.createWorkflowFactory("de.scoopgmbh.copper.test.tranzient.simple.IssueClassCastExceptionWorkflow");
			Workflow<CompletionIndicator> wf = wfFactory.newInstance();
			wf.setData(new CompletionIndicator());
			engine.run(wf);
			
			Thread.sleep(2500);
			
			assertTrue(wf.getData().done);
			assertFalse(wf.getData().error);
		}
		finally {
			context.close();
		}
		assertEquals(EngineState.STOPPED,engine.getEngineState());
		
	}
	
}
