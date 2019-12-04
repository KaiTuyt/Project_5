import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;

public class Driver extends Application{
	
	@Override
	public void start(Stage applicationStage) {
		Pane leftPane = new Pane();
		Scene display = new Scene(leftPane);
		TextField field = new TextField();
		field.setEditable(false);
		leftPane.getChildren().add(field);
		
		applicationStage.setScene(display);
		applicationStage.setTitle("Hamming Distance");
		applicationStage.show();
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}

}
