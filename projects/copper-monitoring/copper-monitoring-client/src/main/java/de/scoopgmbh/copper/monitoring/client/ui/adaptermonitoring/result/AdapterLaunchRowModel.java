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
package de.scoopgmbh.copper.monitoring.client.ui.adaptermonitoring.result;

import de.scoopgmbh.copper.monitoring.core.model.AdapterWfLaunchInfo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class AdapterLaunchRowModel {
	public final SimpleStringProperty workflowname;
	public final SimpleObjectProperty<Date> timestamp;
	public final SimpleStringProperty adapterName;
	
	public AdapterLaunchRowModel(AdapterWfLaunchInfo  adapterWfLaunchInfo){
		workflowname= new SimpleStringProperty(adapterWfLaunchInfo.getWorkflowname());
		timestamp= new SimpleObjectProperty<Date>(adapterWfLaunchInfo.getTimeStamp());
		adapterName= new SimpleStringProperty(adapterWfLaunchInfo.getAdapterName());
	}
}
