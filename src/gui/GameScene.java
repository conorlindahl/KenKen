package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameScene extends Scene {
	
	public GameScene(Stage s, Scene linkBack) {
		super(new BorderPane(), Params.sceneWidth, Params.sceneHeight);
		
		BorderPane layout = (BorderPane) this.getRoot();
		Text title = new Text("KenKen Solver");
		title.setFont(Font.font("Times New Roman", 32));
		layout.setTop(title);
		BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
		
		GridPane grid = setUpKenKenVisual();
		layout.setLeft(grid);
		
		VBox control = new VBox();
		
		ControlPanel controlPanel = new ControlPanel();
		Button link = new Button("Back");
		link.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				s.setScene(linkBack);
			}
			
		});
		control.setAlignment(Pos.TOP_RIGHT);
		control.setSpacing(5);
		
		control.getChildren().addAll(controlPanel, link);
		layout.setRight(control);
		BorderPane.setAlignment(control, Pos.BOTTOM_LEFT);
		BorderPane.setMargin(control, new Insets(50, 100, 0, 0));
	}
	
	public void resize() {
		BorderPane layout = (BorderPane) this.getRoot();
		
		GridPane newGrid = new GridPane();
		newGrid.setAlignment(Pos.BASELINE_LEFT);
		newGrid.setPadding(new Insets(50));
		
		int squareSize = (int) Params.kenkenGridSize/Params.kenkenDimension;
		for(int col=0; col<Params.kenkenDimension; col+=1) {
			for(int row=0; row<Params.kenkenDimension; row+=1) {
				VisualSquare v =  
						new VisualSquare(row, col, squareSize, squareSize);
				newGrid.add(v, col, row);
			}
		}
		
		layout.setLeft(newGrid);
	}
	
	
	private GridPane setUpKenKenVisual() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setPadding(new Insets(50));
		
		int squareSize = (int) Params.kenkenGridSize/Params.kenkenDimension;
		for(int col=0; col<Params.kenkenDimension; col+=1) {
			for(int row=0; row<Params.kenkenDimension; row+=1) {
				VisualSquare v =  
						new VisualSquare(row, col, squareSize, squareSize);
				grid.add(v, col, row);
			}
		}
		
		return grid;
	}
}
