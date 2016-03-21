package graphics;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Board extends Application{
	
	public static final int SQUARE_SIZE = 50;
	
	public static void main(String[] args){
		launch(args);
	}

	
	public void dispatchSolver() {
		/**
		 * put runner coder here
		 */
		
	}
	
	
	
	
	
	
	
	@Override
	public void start(Stage arg0) throws Exception {
			String[][] tileColors = new String[][]{
				{"red", "white", "white", "lightblue", "white", "white", "white", "red", "white", "white", "white", "lightblue", "white", "white", "red"},							
				{"white", "pink", "white", "white", "white", "blue", "white", "white", "white", "blue", "white", "white", "white", "pink", "white"},							
				{"white", "white", "pink", "white", "white", "white", "lightblue", "white", "lightblue", "white", "white", "white", "pink", "white", "white"},							
				{"lightblue", "white", "white", "pink", "white", "white", "white", "lightblue", "white", "white", "white", "pink", "white", "white", "lightblue"},							
				{"white", "white", "white", "white", "pink", "white", "white", "white", "white", "white", "pink", "white", "white", "white", "white"},							
				{"white", "blue", "white", "white", "white", "blue", "white", "white", "white", "blue", "white", "white", "white", "blue", "white"},							
				{"white", "white", "lightblue", "white", "white", "white", "lightblue", "white", "lightblue", "white", "white", "white", "lightblue", "white", "white"},							
				{"red", "white", "white", "lightblue", "white", "white", "white", "hotpink", "white", "white", "white", "lightblue", "white", "white", "red"},							
				{"white", "white", "lightblue", "white", "white", "white", "lightblue", "white", "lightblue", "white", "white", "white", "lightblue", "white", "white"},							
				{"white", "blue", "white", "white", "white", "blue", "white", "white", "white", "blue", "white", "white", "white", "blue", "white"},							
				{"white", "white", "white", "white", "pink", "white", "white", "white", "white", "white", "pink", "white", "white", "white", "white"},							
				{"lightblue", "white", "white", "pink", "white", "white", "white", "lightblue", "white", "white", "white", "pink", "white", "white", "lightblue"},							
				{"white", "white", "pink", "white", "white", "white", "lightblue", "white", "lightblue", "white", "white", "white", "pink", "white", "white"},							
				{"white", "pink", "white", "white", "white", "blue", "white", "white", "white", "blue", "white", "white", "white", "pink", "white"},							
				{"red", "white", "white", "lightblue", "white", "white", "white", "red", "white", "white", "white", "lightblue", "white", "white", "red"}						
			};
		Stage main = new Stage();
		main.addEventFilter(KeyEvent.KEY_PRESSED, e ->{
			if(e.getCode() == KeyCode.ESCAPE){
				System.exit(0);
			}
		});
		GridPane boardGrid = new GridPane();
		Scene boardScene = new Scene(boardGrid);
		for(int i = 0; i < 15; i++){
			boardGrid.getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE));
			boardGrid.getRowConstraints().add(new RowConstraints(SQUARE_SIZE));
		}
		Button a = new Button("");
		for(int i = 0; i < 15; i++){
			for(int j = 0; j < 15; j++){
				a = new Button("");
				a.setMinHeight(SQUARE_SIZE);
				a.setMinWidth(SQUARE_SIZE);
				//21 is the highest number that shows all the letters
				a.setStyle("-fx-font-size: 21; -fx-base: " + tileColors[i][j]);

				final Button localButton = a;
				a.setOnAction(e ->{
					Stage letterStage = new Stage();
					letterStage.setMinWidth(150);
					letterStage.setMinHeight(100);
					VBox letterDialogue = new VBox();
					letterDialogue.setAlignment(Pos.CENTER);
					TextField text = new TextField("Type a Letter");
					text.setMaxWidth(100);
					Button close = new Button("Close");
					close.setOnAction(f ->{
						localButton.setText(text.getText().toUpperCase());
						letterStage.close();
					});
					text.setOnAction(f ->{
						localButton.setText(text.getText().toUpperCase());
						letterStage.close();
					});
					letterDialogue.getChildren().addAll(text,close);
					StackPane letterPane = new StackPane(letterDialogue);
					StackPane.setAlignment(letterDialogue, Pos.CENTER);
					letterStage.setScene(new Scene(letterPane));
					letterStage.show();
					dispatchSolver();
				});
				boardGrid.add(a,i,j);
			}
		}
		boardGrid.setGridLinesVisible(true);
		main.setScene(boardScene);
		main.show();
	}

	

}
