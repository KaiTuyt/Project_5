import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class Driver extends Application {

	@Override
	public void start(Stage applicationStage) {
		GridPane gridPane = new GridPane();
		Insets gridPadding = new Insets(10, 10, 10, 10);
		Scene display = new Scene(gridPane);
		Label freeZone = new Label("FREE ZONE: You are free to fill this area with a creative idea.");
		
		GridPane topLeftPane = new GridPane();
		Label enterLabel = new Label("Enter Hamming Distance: ");
		TextField chosenDistance = new TextField();
		chosenDistance.setEditable(false);
		topLeftPane.add(enterLabel, 0, 0);
		topLeftPane.add(chosenDistance, 1, 0);
		
		Slider slider = new Slider(1, 4, 2);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMinorTickCount(0);
		slider.setMajorTickUnit(1);
		String chosen = Integer.toString((int)slider.getValue());
		chosenDistance.setText(chosen);
		Button showStation = new Button("Show Station");
		
		gridPane.add(topLeftPane, 0, 0);
		gridPane.add(slider, 0, 1);
		gridPane.add(showStation, 0, 2);
		gridPane.add(freeZone, 1, 0);
		
		gridPane.setPadding(gridPadding);
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
