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
package de.scoopgmbh.copper.monitoring.client.form.filter;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public abstract class BaseFilterController<T> implements FilterController<T> {

	protected ListProperty<ActionsWithFilterForm> actionsWithFilterForm = 
			new SimpleListProperty<ActionsWithFilterForm>(FXCollections.<ActionsWithFilterForm>observableArrayList());

	@Override
	public ListProperty<ActionsWithFilterForm> getActionsWithFilterForm() {
		return actionsWithFilterForm;
	}

}
