package graphics;

public class Tile {
	private int type;
	
	public Tile(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	public String getColor(){
		switch(type){
		case 0:
			return "black";
		case 1:
			return "lightgrey";
		case 2:
			return "lightblue";
		case 3:
			return "blue";
		case 4:
			return "pink";
		case 5:
			return "red";
		case 7:
			return "hotpink";
		default:
			return "green";
		}
	}
}
