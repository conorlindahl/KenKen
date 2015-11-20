package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KenKenApp extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KenKen Solver");
		
		Text title = new Text("KenKen Solver");
		title.setFont(Font.font("Times New Roman", 32));
		
		GridPane grid = setUpKenKenVisual();
		
		ControlPanel controlPanel = new ControlPanel();
		
		BorderPane layout = new BorderPane();
		layout.setTop(title);
		BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
		layout.setLeft(grid);
		layout.setRight(controlPanel);
		BorderPane.setAlignment(controlPanel, Pos.BOTTOM_LEFT);
		BorderPane.setMargin(controlPanel, new Insets(50, 100, 0, 0));

		double sceneWidth = Params.sceneWidth;
		double sceneHeight = Params.sceneHeight;
		Scene r = new Scene(layout, sceneWidth, sceneHeight);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(r);
		primaryStage.show();
	}
	
	private GridPane setUpKenKenVisual() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setPadding(new Insets(50));
		
		for(int col=0; col<Params.kenkenDimension; col+=1) {
			for(int row=0; row<Params.kenkenDimension; row+=1) {
				VisualSquare v =  
						new VisualSquare(row, col, Params.squareSize, Params.squareSize);
				GraphicsContext gc = v.getGraphicsContext2D();
				gc.setStroke(Color.DARKGREY);
				gc.setLineWidth(Params.unselectedStrokeSize);
				gc.strokeRect(0, 0, Params.squareSize, Params.squareSize);
				grid.add(v, col, row);
			}
		}
		
		return grid;
	}

}
