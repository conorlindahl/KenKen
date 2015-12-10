package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KenKenApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KenKen Solver");
		
		VBox menu = new VBox();
		Scene t = new Scene(menu, Params.sceneWidth, Params.sceneHeight);
		
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(new Text("Welcome to the kenken Solver!"));
		
		HBox sizeSelect = new HBox();
		sizeSelect.getChildren().add(new Text("Choose your KenKen size: "));
		ChoiceBox<Integer> sizes = new ChoiceBox<Integer>();
		sizes.getItems().addAll(new Integer(5), new Integer(6), new Integer(7), new Integer(8), new Integer(9));
		sizeSelect.setAlignment(Pos.CENTER);
		sizeSelect.getChildren().add(sizes);
		menu.getChildren().add(sizeSelect);
		
		GameScene game = new GameScene(primaryStage, t);
		Button start = new Button("Solve");
		start.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int size = sizes.getValue().intValue();
				Params.kenkenDimension = size;
				game.resize();
				primaryStage.setScene(game);
			}
		});
		menu.getChildren().add(start);
		
		
		primaryStage.setResizable(false);
		primaryStage.setScene(t);
		primaryStage.show();
		
	}
}
