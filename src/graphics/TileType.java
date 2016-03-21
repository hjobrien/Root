package graphics;


public enum TileType {
	DOESNT_EXIST(0),				//0
	BLANK(1),						//1
	DOUBLE_LETTER(2),				//2
	TRIPLE_LETTER(3),				//3
	DOUBLE_WORD(4),					//4
	TRIPLE_WORD(5),					//5	
	BOARD_LETTER(6),				//6
	CENTER(7);						//7
	
	private final int value;

    private TileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
