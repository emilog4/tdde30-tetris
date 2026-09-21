package se.liu.emilo739.tetris;

public class BoardToTextConverter
{
    public String convertToText(Board board) {
	int width = board.getWidth();
	int height = board.getHeight();
	StringBuilder result = new StringBuilder();
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width ; x++) {
		SquareType piece = board.getVisibleSquareAt(x, y);
		switch (piece){
		    case EMPTY -> result.append(" ");
		    case I -> result.append("+");
		    case J -> result.append("%");
		    case L -> result.append("&");
		    case O -> result.append("#");
		    case S -> result.append("@");
		    case T -> result.append("*");
		    case Z -> result.append("-");
		    }
		}
	    result.append("\n");
	    }
	return result.toString();
    }
}