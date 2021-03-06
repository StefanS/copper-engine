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
package de.scoopgmbh.copper.monitoring.core.model;

import java.io.Serializable;

public class StorageInfo implements Serializable{
	private static final long serialVersionUID = 8298167440433882270L;
	
	String description;
	BatcherInfo batcher;

	public StorageInfo(String description, BatcherInfo batcher) {
		super();
		this.description = description;
		this.batcher = batcher;
	}

	public BatcherInfo getBatcher() {
		return batcher;
	}

	public void setBatcher(BatcherInfo batcher) {
		this.batcher = batcher;
	}

	public StorageInfo() {
		super();
		batcher= new BatcherInfo();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
