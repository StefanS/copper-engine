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
package de.scoopgmbh.copper.test.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.scoopgmbh.copper.EngineState;
import de.scoopgmbh.copper.db.utility.RetryingTransaction;
import de.scoopgmbh.copper.persistent.PersistentScottyEngine;
import de.scoopgmbh.copper.test.backchannel.BackChannelQueue;
import de.scoopgmbh.copper.test.backchannel.WorkflowResult;


public class BaseSpringTxnPersistentWorkflowTest extends BasePersistentWorkflowTest {

	protected ConfigurableApplicationContext createContext(String dsContext) {
		return new ClassPathXmlApplicationContext(new String[] {dsContext, "SpringTxnPersistentWorkflowTest/persistent-engine-unittest-context.xml", "unittest-context.xml"});
	}


	public void testSpringTxnUnitTestWorkflow(String dsContext) throws Exception {
		assumeFalse(skipTests());
		final ConfigurableApplicationContext context = createContext(dsContext);
		cleanDB(context.getBean(DataSource.class));
		final PersistentScottyEngine engine = context.getBean(PersistentScottyEngine.class);
		final BackChannelQueue backChannelQueue = context.getBean(BackChannelQueue.class);
		try {
			engine.startup();
			engine.run("de.scoopgmbh.copper.test.persistent.springtxn.SpringTxnUnitTestWorkflow", "TestData");
			WorkflowResult x = backChannelQueue.dequeue(60, TimeUnit.SECONDS);
			assertNotNull(x);
			assertNotNull(x.getResult());
			assertNull(x.getException());

			//check
			new RetryingTransaction<Void>(context.getBean(DataSource.class)) {
				@Override
				protected Void execute() throws Exception {
					Statement stmt = getConnection().createStatement();
					ResultSet rs = stmt.executeQuery("select count(*) from cop_audit_trail_event");
					assertTrue(rs.next());
					int c = rs.getInt(1);
					assertEquals(7, c);
					rs.close();
					stmt.close();
					return null;
				}
			}.run();
		}
		finally {
			closeContext(context);
		}
		assertEquals(EngineState.STOPPED,engine.getEngineState());
		assertEquals(0,engine.getNumberOfWorkflowInstances());
	}	
}
