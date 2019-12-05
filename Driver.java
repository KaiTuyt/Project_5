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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

public class Driver extends Application {
	
	private static final int PANE_VGAP = 10;
	private static final int PANE_HGAP = 20;
	public String[] list;	
	public ComboBox stations;
	public TextField chosenIndex;
	public TextField asciiAverage;
	public TextField letterAverage;
	public TextField numberOfSimilar;
	public Label similarStations;

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
		topLeftPane.setHgap(PANE_HGAP);
		topLeftPane.setVgap(PANE_VGAP);
		
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
		middleLeftPane.setHgap(PANE_HGAP);
		middleLeftPane.setVgap(PANE_VGAP);
		
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
				chosenIndex.setText("" + (calc.getStationIndex(chosen) + 1));
				asciiAverage.setText("" + calc.calAverage(chosen));
				letterAverage.setText("" + calc.letterAverage(chosen));
				numberOfSimilar.setText("" + calc.numberOfStationWithLetterAvg(chosen.charAt(0)));
				similarStations.setText("" + calc.stationsWithLetterAvg(chosen.charAt(0)));
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
					Alert alert = new Alert(AlertType.ERROR, 
							"Incorrectly formatted station ID inserted. Please insert a valid station ID.");
					alert.showAndWait();
				}
			}
		});
		
		bottomLeftPane.add(customStation, 1, 5);
		bottomLeftPane.setPadding(smallGridPadding);
		bottomLeftPane.setHgap(PANE_HGAP);
		bottomLeftPane.setVgap(PANE_VGAP);
		
		
		
		Label freeZone = new Label("FREE ZONE: Additional Functions");
		
		GridPane topRightPane = new GridPane();
		Label index = new Label("Station Index: ");
		chosenIndex = new TextField();
		chosenIndex.setEditable(false);
		topRightPane.add(index, 0, 0);
		topRightPane.add(chosenIndex, 1, 0);
		
		Button randomButton = new Button("Create Random Station");
		
		randomButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayStations.getItems().clear();
				calc.addStation(calc.createRandomStation());
				list = calc.getStationsArray();
				stations = new ComboBox(FXCollections.observableArrayList(list));
				stations.getSelectionModel().selectFirst();
				middleLeftPane.add(stations, 1, 0);
			}
		});
		
		GridPane topMiddleRightPane = new GridPane();
		Label asciiLabel = new Label("Ascii Average:");
		asciiAverage = new TextField("");
		Label letterLabel = new Label("Letter Average:");
		letterAverage = new TextField("");
		Label similarLabel = new Label("Stations with \nsimilar start letter:");
		numberOfSimilar = new TextField("");
		topMiddleRightPane.setPadding(smallGridPadding);
		topMiddleRightPane.setHgap(PANE_HGAP);
		topMiddleRightPane.setVgap(PANE_VGAP);
		topMiddleRightPane.add(asciiLabel, 0, 0);
		topMiddleRightPane.add(asciiAverage, 1, 0);
		topMiddleRightPane.add(letterLabel, 0, 1);
		topMiddleRightPane.add(letterAverage, 1, 1);
		topMiddleRightPane.add(similarLabel, 0, 2);
		topMiddleRightPane.add(numberOfSimilar, 1, 2);
		
		similarStations = new Label("");

		// TODO: Figure out something to do with the free zone!!!
		
		gridPane.add(topLeftPane, 0, 0);
		gridPane.add(slider, 0, 1);
		gridPane.add(showStationButton, 0, 2);
		gridPane.add(displayStations, 0, 3);
		gridPane.add(middleLeftPane, 0, 4);
		gridPane.add(calculateButton, 0, 5);
		gridPane.add(bottomLeftPane, 0, 6);
		gridPane.add(freeZone, 1, 0);
		gridPane.add(topRightPane, 1, 1);
		gridPane.add(randomButton, 1, 2);
		gridPane.add(topMiddleRightPane, 1, 3);
		gridPane.add(similarStations, 1, 4);
		
		gridPane.setPadding(smallGridPadding);
		gridPane.setHgap(PANE_HGAP);
		gridPane.setVgap(PANE_VGAP);

		applicationStage.setScene(display);
		applicationStage.setTitle("Hamming Distance");
		applicationStage.show();

		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
