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
package de.scoopgmbh.copper.monitoring.client.ui.dashboard.result.pool;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import de.scoopgmbh.copper.monitoring.client.adapter.GuiCopperDataProvider;
import de.scoopgmbh.copper.monitoring.client.context.FormContext;
import de.scoopgmbh.copper.monitoring.client.form.FxmlController;
import de.scoopgmbh.copper.monitoring.client.util.dialogs.Dialogs;
import de.scoopgmbh.copper.monitoring.core.model.ProcessingEngineInfo;
import de.scoopgmbh.copper.monitoring.core.model.ProcessorPoolInfo;

public class ProccessorPoolController implements Initializable, FxmlController {
	private ProcessorPoolInfo pool;
	private final ProcessingEngineInfo engine;
	private final FormContext context;
	private final GuiCopperDataProvider dataProvider;

    public ProccessorPoolController(ProcessingEngineInfo engine, ProcessorPoolInfo pool, FormContext context, GuiCopperDataProvider dataProvider) {
    	this.pool=pool;
    	this.engine = engine;
    	this.context = context;
    	this.dataProvider = dataProvider;
	}


    @FXML //  fx:id="id"
    private TextField id; // Value injected by FXMLLoader

    @FXML //  fx:id="nummerbutton"
    private Button nummerbutton; // Value injected by FXMLLoader

    @FXML //  fx:id="prioButton"
    private Button prioButton; // Value injected by FXMLLoader

    @FXML //  fx:id="threadNummerInfo"
    private TextField threadNummerInfo; // Value injected by FXMLLoader

    @FXML //  fx:id="threadPrioritaetInfo"
    private TextField threadPrioritaetInfo; // Value injected by FXMLLoader

    @FXML //  fx:id="typ"
    private TextField typ; // Value injected by FXMLLoader


    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'ProcessorPool.fxml'.";
        assert nummerbutton != null : "fx:id=\"nummerbutton\" was not injected: check your FXML file 'ProcessorPool.fxml'.";
        assert prioButton != null : "fx:id=\"prioButton\" was not injected: check your FXML file 'ProcessorPool.fxml'.";
        assert threadNummerInfo != null : "fx:id=\"threadNummerInfo\" was not injected: check your FXML file 'ProcessorPool.fxml'.";
        assert threadPrioritaetInfo != null : "fx:id=\"threadPrioritaetInfo\" was not injected: check your FXML file 'ProcessorPool.fxml'.";
        assert typ != null : "fx:id=\"typ\" was not injected: check your FXML file 'ProcessorPool.fxml'.";

		updatePool();
		
		prioButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Integer newValue = Dialogs.showIntInputDialog(null, 
						"Thread priority", "ProcessorPool", "Copper Dashboard", 
						threadPrioritaetInfo.getText());
				if (newValue != null){
					dataProvider.setThreadPriority(engine.getId(), pool.getId(), newValue);
					context.createDashboardForm().refresh();
				}
			}
		});
		prioButton.getStyleClass().add("copperActionButton");
		
		nummerbutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Integer newValue = Dialogs.showIntInputDialog(null, 
						"Thread count", "ProcessorPool", "Copper Dashboard", 
						threadNummerInfo.getText());
				if (newValue != null){
					dataProvider.setNumberOfThreads(engine.getId(), pool.getId(), newValue);
					context.createDashboardForm().refresh();
				}
			}
		});
		nummerbutton.getStyleClass().add("copperActionButton");
    }

    public void setPool(ProcessorPoolInfo pool) {
		this.pool = pool;
		updatePool();
	}

	private void updatePool() {
		id.setText(pool.getId());
		typ.setText(pool.getProcessorPoolTyp().toString());
		
		threadNummerInfo.setText(String.valueOf(pool.getNumberOfThreads()));
		threadPrioritaetInfo.setText(String.valueOf(pool.getThreadPriority()));
	}
	
	@Override
	public URL getFxmlResource() {
		return getClass().getResource("ProcessorPool.fxml");
	}

}
