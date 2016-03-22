package graphics;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import runner.Processor;

public class Board extends Application{
	
	public static final int SQUARE_SIZE = 50;
	public static final int SIZE = 15;
	public static final boolean USE_ENUM = true;
	
	private String handLetters = "ABCDEFG";

	
	public static final Tile[][] BLANK_BOARD = new Tile[][]{
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
	
	public static void main(String[] args){
		launch(args);
	}

	
	public void dispatchSolver(String handLetters, char boardLetter, int boardLetterX, int boardLetterY) {
		try{
		long t1 = System.currentTimeMillis();
		/**
		 * put runner coder here
		 */
		if(USE_ENUM)
			Processor.run(getTilesAsEnum(), handLetters.toCharArray(), boardLetter, boardLetterX, boardLetterY);
		System.out.println("Solver Finished in " + (t1 - System.currentTimeMillis()) + " Milliseconds");
		}catch (Exception e){
			System.err.println("Error: Solver threw exception");
			e.printStackTrace(System.err);
		}
	}
	
	private int[][] getTilesAsInt(){
		int[][] tileType = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				tileType[i][j] = BLANK_BOARD[i][j].getType();
			}
		}
		return tileType;
	}
	
	private TileType[][] getTilesAsEnum(){
		TileType[][] tileType = new TileType[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				tileType[i][j] = TileType.values()[BLANK_BOARD[i][j].getType()];
			}
		}
		return tileType;
	}
	
	
	
	
	
	
	
	@Override
	public void start(Stage arg0) throws Exception {
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
				a.setStyle("-fx-font-size: 21; -fx-base: " + BLANK_BOARD[i][j].getColor());
				final int x = i;
				final int y = j;
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
						dispatchSolver(handLetters, text.getText().toCharArray()[0], x,y);
					});
					text.setOnAction(f ->{
						localButton.setText(text.getText().toUpperCase());
						letterStage.close();
						dispatchSolver(handLetters, text.getText().toCharArray()[0], x,y);
					});
					letterDialogue.getChildren().addAll(text,close);
					StackPane letterPane = new StackPane(letterDialogue);
					StackPane.setAlignment(letterDialogue, Pos.CENTER);
					letterStage.setScene(new Scene(letterPane));
					letterStage.show();
				});
				boardGrid.add(a,i,j);
			}
		}
		GridPane mainGrid = new GridPane();
		mainGrid.add(boardGrid, 0, 0);
		VBox utilBox = new VBox();
		utilBox.setAlignment(Pos.TOP_CENTER);
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
		clearLetters.setMinWidth(300);
		Label letterLabel = new Label("ABCDEFG");
		letterLabel.setStyle("-fx-font-size: 35");
		letterLabel.setMinHeight(30);
		Button genLetters = new Button("Generate new letters");
		genLetters.setMinWidth(300);
		genLetters.setOnAction(e ->{
			Stage letterPopup = new Stage();
			letterPopup.setMinHeight(100);
			letterPopup.setMinWidth(250);
			VBox letterPrompt = new VBox();
			letterPrompt.setAlignment(Pos.CENTER);
			TextField letterInput = new TextField();
			letterInput.setFocusTraversable(false);
			letterInput.setPromptText("A, b, c, D, E, F, G");
			letterInput.setOnAction(f ->{
				handLetters = letterInput.getText().toUpperCase();
				handLetters = handLetters.replace(" ", "");
				handLetters = handLetters.replace(",", "");
				letterLabel.setText(handLetters);
				letterPopup.close();
			});
			Button close = new Button("Close");
			close.setFocusTraversable(false);
			close.setOnAction(f ->{
				handLetters = letterInput.getText().toUpperCase();
				handLetters = handLetters.replace(" ", "");
				handLetters = handLetters.replace(",", "");
				letterLabel.setText(handLetters);
				letterPopup.close();
			});
			letterPrompt.getChildren().addAll(letterInput, close);
			Scene letterScene = new Scene(letterPrompt);
			letterPopup.setScene(letterScene);
			letterPopup.show();
		});
		utilBox.getChildren().addAll(validWords, clearLetters, letterLabel, genLetters);
		mainGrid.add(utilBox, 1, 0);
		Scene boardScene = new Scene(mainGrid);
		boardGrid.setGridLinesVisible(true);
		main.setScene(boardScene);
		main.show();
	}

	

}
