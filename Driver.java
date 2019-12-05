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
	
	private static final int SLIDER_DEFAULT = 2;
	private static final int SLIDER_HIGH = 4;
	private static final int SLIDER_LOW = 1;
	private static final int LISTVIEW_HEIGHT = 200;
	private static final int PANE_VGAP = 10;
	private static final int PANE_HGAP = 20;
	private static final Insets SMALL_GRID_PADDING = new Insets(5, 5, 5, 5);
	
	protected String[] list;
	
	protected Slider slider;
	protected ComboBox stations;
	protected Button showStationButton;
	protected Button calculateButton;
	protected Button addStationButton;
	protected Button randomButton;
	protected TextField chosenIndex;
	protected TextField asciiAverage;
	protected TextField letterAverage;
	protected TextField numberOfSimilar;
	protected TextField chosenDistance;
	protected TextField distance0;
	protected TextField distance1;
	protected TextField distance2;
	protected TextField distance3;
	protected TextField distance4;
	protected TextField customStation;
	protected ListView displayStations;
	protected Label similarStations;
	protected Label enterLabel;
	protected Label value;
	protected Label compareWith;
	protected Label freeZone;
	protected Label index;
	protected Label asciiLabel;
	protected Label letterLabel;
	protected Label similarLabel;
	protected GridPane majorGridPane;
	protected GridPane topLeftPane;
	protected GridPane middleLeftPane;
	protected GridPane bottomLeftPane;
	protected GridPane topRightPane;
	protected GridPane topMiddleRightPane;
	protected Scene display;
	

	@Override
	public void start(Stage applicationStage) {
		
		MesoCalculations calc = new MesoCalculations();
		try {
			calc.read("Mesonet.txt");
		} catch (Exception e) {System.out.println(e);}
		
		majorGridPane = new GridPane();
		display = new Scene(majorGridPane);
		
		topLeftPane = new GridPane();
		enterLabel = new Label("Enter Hamming Distance: ");
		chosenDistance = new TextField("2");
		chosenDistance.setEditable(false);
		topLeftPane.add(enterLabel, 0, 0);
		topLeftPane.add(chosenDistance, 1, 0);
		topLeftPane.setPadding(SMALL_GRID_PADDING);
		topLeftPane.setHgap(PANE_HGAP);
		topLeftPane.setVgap(PANE_VGAP);
		
		slider = new Slider(SLIDER_LOW, SLIDER_HIGH, SLIDER_DEFAULT);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMinorTickCount(0);
		slider.setMajorTickUnit(1);
		value = new Label(Integer.toString((int)slider.getValue()));
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
				Number oldVal, Number newVal) {
				chosenDistance.setText(String.format("%.0f", newVal));
			}
		});
				
		showStationButton = new Button("Show Station");
		
		displayStations = new ListView();
		displayStations.setPrefHeight(LISTVIEW_HEIGHT);
		
		middleLeftPane = new GridPane();
		compareWith = new Label("Compare with: ");
		list = calc.getStationsArray();		
		stations = new ComboBox(FXCollections.observableArrayList(list));
		stations.getSelectionModel().selectFirst();
		middleLeftPane.add(compareWith, 0, 0);
		middleLeftPane.add(stations, 1, 0);
		middleLeftPane.setPadding(SMALL_GRID_PADDING);
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
		
		calculateButton = new Button("Calculate HD");
		
		bottomLeftPane = new GridPane();
		for (int i = 0; i < 5; i++) {
			Label distanceLabel = new Label("Distance " + i);
			bottomLeftPane.add(distanceLabel, 0, i);
		}
		distance0 = new TextField();
		distance0.setEditable(false);
		bottomLeftPane.add(distance0, 1, 0);
		distance1 = new TextField();
		distance1.setEditable(false);
		bottomLeftPane.add(distance1, 1, 1);
		distance2 = new TextField();
		distance2.setEditable(false);
		bottomLeftPane.add(distance2, 1, 2);
		distance3 = new TextField();
		distance3.setEditable(false);
		bottomLeftPane.add(distance3, 1, 3);
		distance4 = new TextField();
		distance4.setEditable(false);
		bottomLeftPane.add(distance4, 1, 4);
		addStationButton = new Button("Add Station");
		bottomLeftPane.add(addStationButton, 0, 5);
		customStation = new TextField();
		customStation.setEditable(true);
		
		calculateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String chosen = stations.getValue().toString();
				distance0.setText("" + calc.matchingDistance(0, chosen));
				distance1.setText("" + calc.matchingDistance(1, chosen));
				distance2.setText("" + calc.matchingDistance(2, chosen));
				distance3.setText("" + calc.matchingDistance(3, chosen));
				distance4.setText("" + calc.matchingDistance(4, chosen));
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
				if (custom.length() == 4) {
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
				customStation.setText("");
			}
		});
		
		bottomLeftPane.add(customStation, 1, 5);
		bottomLeftPane.setPadding(SMALL_GRID_PADDING);
		bottomLeftPane.setHgap(PANE_HGAP);
		bottomLeftPane.setVgap(PANE_VGAP);
		
		
		
		freeZone = new Label("FREE ZONE: Additional Functions");
		
		topRightPane = new GridPane();
		index = new Label("Station Index: ");
		chosenIndex = new TextField();
		chosenIndex.setEditable(false);
		topRightPane.add(index, 0, 0);
		topRightPane.add(chosenIndex, 1, 0);
		
		randomButton = new Button("Create Random Station");
		
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
		
		topMiddleRightPane = new GridPane();
		asciiLabel = new Label("Ascii Average:");
		asciiAverage = new TextField("");
		letterLabel = new Label("Letter Average:");
		letterAverage = new TextField("");
		similarLabel = new Label("Stations with \nsimilar start letter:");
		numberOfSimilar = new TextField("");
		topMiddleRightPane.setPadding(SMALL_GRID_PADDING);
		topMiddleRightPane.setHgap(PANE_HGAP);
		topMiddleRightPane.setVgap(PANE_VGAP);
		topMiddleRightPane.add(asciiLabel, 0, 0);
		topMiddleRightPane.add(asciiAverage, 1, 0);
		topMiddleRightPane.add(letterLabel, 0, 1);
		topMiddleRightPane.add(letterAverage, 1, 1);
		topMiddleRightPane.add(similarLabel, 0, 2);
		topMiddleRightPane.add(numberOfSimilar, 1, 2);
		
		similarStations = new Label("");

		// TODO: Figure out something else to do with the free zone!!!
		
		majorGridPane.add(topLeftPane, 0, 0);
		majorGridPane.add(slider, 0, 1);
		majorGridPane.add(showStationButton, 0, 2);
		majorGridPane.add(displayStations, 0, 3);
		majorGridPane.add(middleLeftPane, 0, 4);
		majorGridPane.add(calculateButton, 0, 5);
		majorGridPane.add(bottomLeftPane, 0, 6);
		majorGridPane.add(freeZone, 1, 0);
		majorGridPane.add(topRightPane, 1, 1);
		majorGridPane.add(randomButton, 1, 2);
		majorGridPane.add(topMiddleRightPane, 1, 3);
		majorGridPane.add(similarStations, 1, 4);
		
		majorGridPane.setPadding(SMALL_GRID_PADDING);
		majorGridPane.setHgap(PANE_HGAP);
		majorGridPane.setVgap(PANE_VGAP);

		applicationStage.setScene(display);
		applicationStage.setTitle("Hamming Distance");
		applicationStage.show();

		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
