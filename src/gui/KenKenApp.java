package gui;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
		
		BorderPane layout = new BorderPane();
		layout.setTop(title);
		BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(title, new Insets(20, 0, 0, 0));
		layout.setLeft(grid);
		Scene r = new Scene(layout, 550, 400);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(r);
		primaryStage.show();
	}
	
	private GridPane setUpKenKenVisual() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_LEFT);
		grid.setPadding(new Insets(50));
		
		for(int x=0; x<5; x+=1) {
			for(int y=0; y<5; y+=1) {
				VisualSquare v = new VisualSquare(50, 50);
				v.row = y;
				v.col = x;
				v.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if(v.locked) { return; }
						
						GraphicsContext gc = v.getGraphicsContext2D();
						if (v.selected) {
							gc.setStroke(Color.DARKGRAY);
							gc.setLineWidth(2.0);
							gc.strokeRect(0, 0, 50, 50);
							VisualSquare.selectedSquares.remove(v);
						} else {
							gc.setStroke(Color.BLACK);
							gc.setLineWidth(2.0);
							gc.strokeRect(0, 0, 50, 50);
							VisualSquare.selectedSquares.add(v);
						}
						v.selected = !v.selected;
					}
					
				});
				GraphicsContext gc = v.getGraphicsContext2D();
				gc.setStroke(Color.DARKGRAY);
				gc.setLineWidth(2.0);
				gc.strokeRect(0, 0, 50, 50);
				grid.add(v, x, y);
			}
		}
		
		return grid;
	}

	
	private static class VisualSquare extends Canvas {
		int row;
		int col;
		boolean selected;
		boolean locked;
		
		public VisualSquare() {
			genericConstruction();
		}
		
		public VisualSquare(int width, int height) {
			super(width, height);
			genericConstruction();
		}
		
		private void genericConstruction() {
			row = 0;
			col = 0;
			selected = false;
		}
		
		static Set<VisualSquare> selectedSquares;
		
		static {
			selectedSquares = new HashSet<VisualSquare>();
		}
	}

}
