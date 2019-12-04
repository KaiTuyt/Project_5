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
		//Pane leftPane = new Pane();
		TextField field = new TextField();
		field.setEditable(false);
		//leftPane.getChildren().add(field);
		GridPane gridPane = new GridPane();
		Insets gridPadding = new Insets(10, 10, 10, 10);
		Scene display = new Scene(gridPane);
		
		Label enterLabel = new Label("Enter Hamming Distance: ");
		TextField chosenDistance = new TextField();
		chosenDistance.setEditable(false);
		Slider slider = new Slider(1, 4, 2);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMinorTickCount(0);
		slider.setMajorTickUnit(1);
		chosenDistance.setText(Integer.toString((int)slider.getValue()));
		Button showStation = new Button("Show Station");
		
		gridPane.add(enterLabel, 0, 0);
		gridPane.add(chosenDistance, 1, 0);
		gridPane.add(slider, 0, 1);
		gridPane.add(showStation, 0, 2);
		
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
