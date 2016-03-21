package graphics;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
	public static final int SIZE = 15;
	
	public static void main(String[] args){
		launch(args);
	}

	
	public void dispatchSolver(int[][] boardState) {
		try{
		long t1 = System.currentTimeMillis();
		/**
		 * put runner coder here
		 */
		System.out.println("Solver Finished in " + (t1 - System.currentTimeMillis()) + " Milliseconds");
		}catch (Exception e){
			System.out.println("Error: Solver threw exception");
		}
	}
	
	
	
	
	
	
	
	@Override
	public void start(Stage arg0) throws Exception {
			Tile[][] tileColors = new Tile[][]{
				{new Tile(5), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(5), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(5)},							
				{new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1)},							
				{new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1)},							
				{new Tile(2), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(2)},							
				{new Tile(1), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(1)},							
				{new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1)},							
				{new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1)},							
				{new Tile(5), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(7), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(5)},							
				{new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1)},							
				{new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1)},							
				{new Tile(1), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(1)},							
				{new Tile(2), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(2)},							
				{new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1), new Tile(1)},							
				{new Tile(1), new Tile(4), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(3), new Tile(1), new Tile(1), new Tile(1), new Tile(4), new Tile(1)},							
				{new Tile(5), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(1), new Tile(5), new Tile(1), new Tile(1), new Tile(1), new Tile(2), new Tile(1), new Tile(1), new Tile(5)}						
			};
		Stage main = new Stage();
		main.addEventFilter(KeyEvent.KEY_PRESSED, e ->{
			if(e.getCode() == KeyCode.ESCAPE){
				System.exit(0);
			}
		});
		GridPane boardGrid = new GridPane();
		for(int i = 0; i < 15; i++){
			boardGrid.getColumnConstraints().add(new ColumnConstraints(SQUARE_SIZE));
			boardGrid.getRowConstraints().add(new RowConstraints(SQUARE_SIZE));
		}
		Button a = new Button("");
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				a = new Button("");
				a.setMinHeight(SQUARE_SIZE);
				a.setMinWidth(SQUARE_SIZE);
				//21 is the highest number that shows all the letters
				a.setStyle("-fx-font-size: 21; -fx-base: " + tileColors[i][j].getColor());

				final Button localButton = a;
				a.setOnAction(e ->{
					Stage letterStage = new Stage();
					letterStage.setMinWidth(150);
					letterStage.setMinHeight(100);
					VBox letterDialogue = new VBox();
					letterDialogue.setAlignment(Pos.CENTER);
					TextField text = new TextField();
					text.setFocusTraversable(false);
					text.setPromptText("Type a Letter");
					text.setMaxWidth(100);
					Button close = new Button("Close");
					close.setFocusTraversable(false);
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
//					dispatchSolver(getBoardState());
				});
				boardGrid.add(a,i,j);
			}
		}
		GridPane mainGrid = new GridPane();
		mainGrid.add(boardGrid, 0, 0);
		VBox utilBox = new VBox();
		TextArea validWords = new TextArea("Possible Words:");
		validWords.setEditable(false);
		validWords.setMaxWidth(300);
		validWords.setMinHeight(600);
		Button clearLetters = new Button("Clear Board");
		clearLetters.setOnAction(e ->{
			for(Node n : boardGrid.getChildren()){
				if(n instanceof Button){
					Button b = (Button) n;
					b.setText("");
				}
			}
		});
		utilBox.getChildren().addAll(validWords, clearLetters);
		mainGrid.add(utilBox, 1, 0);
		Scene boardScene = new Scene(mainGrid);
		boardGrid.setGridLinesVisible(true);
		main.setScene(boardScene);
		main.show();
	}


	private int[][] getBoardState() {
		//TODO
		return null;
	}

	

}
