import java.io.IOException;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

public class Driver extends Application {
	
	public String[] list;	
	public ComboBox stations;

	@Override
	public void start(Stage applicationStage) {
		
		MesoCalculations calc = new MesoCalculations();
		try {
			calc.read("Mesonet.txt");
		} catch (Exception e) {System.out.println(e);}
		
		GridPane gridPane = new GridPane();
		Insets smallGridPadding = new Insets(5, 5, 5, 5);
		Scene display = new Scene(gridPane);
		
		GridPane topLeftPane = new GridPane();
		Label enterLabel = new Label("Enter Hamming Distance: ");
		TextField chosenDistance = new TextField("2");
		chosenDistance.setEditable(false);
		topLeftPane.add(enterLabel, 0, 0);
		topLeftPane.add(chosenDistance, 1, 0);
		topLeftPane.setPadding(smallGridPadding);
		topLeftPane.setHgap(10);
		topLeftPane.setVgap(10);
		
		Slider slider = new Slider(1, 4, 2);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMinorTickCount(0);
		slider.setMajorTickUnit(1);
		Label value = new Label(Integer.toString((int)slider.getValue()));
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
				Number oldVal, Number newVal) {
				chosenDistance.setText(String.format("%.0f", newVal));
			}
		});
				
		Button showStationButton = new Button("Show Station");
		
		ListView displayStations = new ListView();
		displayStations.setPrefHeight(200);
		
		GridPane middleLeftPane = new GridPane();
		Label compareWith = new Label("Compare with: ");
		list = calc.getStationsArray();		
		stations = new ComboBox(FXCollections.observableArrayList(list));
		stations.getSelectionModel().selectFirst();
		middleLeftPane.add(compareWith, 0, 0);
		middleLeftPane.add(stations, 1, 0);
		middleLeftPane.setPadding(smallGridPadding);
		middleLeftPane.setHgap(10);
		middleLeftPane.setVgap(10);
		
		showStationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayStations.getItems().clear();
				Integer distance = Integer.valueOf(chosenDistance.getText());
				String chosen = stations.getValue().toString();
				TreeSet<String> stationSet = calc.matchingStations(distance, chosen);
				for (String s: stationSet)
					displayStations.getItems().add(s);
			}
		});
		
		Button calculateButton = new Button("Calculate HD");
		
		GridPane bottomLeftPane = new GridPane();
		for (int i = 0; i < 5; i++) {
			Label distanceLabel = new Label("Distance " + i);
			bottomLeftPane.add(distanceLabel, 0, i);
		}
		TextField distance0 = new TextField();
		distance0.setEditable(false);
		bottomLeftPane.add(distance0, 1, 0);
		TextField distance1 = new TextField();
		distance1.setEditable(false);
		bottomLeftPane.add(distance1, 1, 1);
		TextField distance2 = new TextField();
		distance2.setEditable(false);
		bottomLeftPane.add(distance2, 1, 2);
		TextField distance3 = new TextField();
		distance3.setEditable(false);
		bottomLeftPane.add(distance3, 1, 3);
		TextField distance4 = new TextField();
		distance4.setEditable(false);
		bottomLeftPane.add(distance4, 1, 4);
		Button addStationButton = new Button("Add Station");
		// TODO: Add Mesonet.txt functionality...
		bottomLeftPane.add(addStationButton, 0, 5);
		TextField customStation = new TextField();
		customStation.setEditable(true);
		
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String chosen = stations.getValue().toString();
				int part0 = calc.matchingDistance(0, chosen);
				distance0.setText("" + part0);
				int part1 = calc.matchingDistance(1, chosen);
				distance1.setText("" + part1);
				int part2 = calc.matchingDistance(2, chosen);
				distance2.setText("" + part2);
				int part3 = calc.matchingDistance(3, chosen);
				distance3.setText("" + part3);
				int part4 = calc.matchingDistance(4, chosen);
				distance4.setText("" + part4);
			}
		});
		
		addStationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayStations.getItems().clear();
				String custom = customStation.getText();
				if (!(custom.length() > 4 || custom.length() < 4)) {
					calc.addStation(custom);
					list = calc.getStationsArray();
					stations = new ComboBox(FXCollections.observableArrayList(list));
					stations.getSelectionModel().selectFirst();
					middleLeftPane.add(stations, 1, 0);
				}
				else {
					System.out.println("Error!");
				}
			}
		});
		
		// TODO: Have error-checking capabilities (check zyBooks)
		bottomLeftPane.add(customStation, 1, 5);
		bottomLeftPane.setPadding(smallGridPadding);
		bottomLeftPane.setHgap(20);
		bottomLeftPane.setVgap(20);
		
		Label freeZone = new Label("FREE ZONE: You are free to fill this area with a creative idea.");
		// TODO: Figure out something to do with the free zone!!!
		
		gridPane.add(topLeftPane, 0, 0);
		gridPane.add(slider, 0, 1);
		gridPane.add(showStationButton, 0, 2);
		gridPane.add(displayStations, 0, 3);
		gridPane.add(middleLeftPane, 0, 4);
		gridPane.add(calculateButton, 0, 5);
		gridPane.add(bottomLeftPane, 0, 6);
		gridPane.add(freeZone, 1, 0);
		
		gridPane.setPadding(smallGridPadding);
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		applicationStage.setScene(display);
		applicationStage.setTitle("Hamming Distance");
		applicationStage.show();

		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
