package se.liu.emilo739.tetris;


import java.awt.*;

public abstract class AbstractFallHandler implements FallHandler
{
    protected abstract boolean checkCollision(SquareType boardSquare);

    @Override
    public boolean hasCollision(final Board board, Point originalPosition) {
	SquareType[][] fallingPoly = board.getFalling().getPoly(); // Get block shape
	int x = board.getFallingPoint().x;
	int y = board.getFallingPoint().y;

	for (int row = 0; row < board.getFalling().getHeight(); row++) {
	    for (int col = 0; col < board.getFalling().getWidth(); col++) {
		SquareType blockSquare = fallingPoly[row][col];
		// Ignore empty squares
		if (blockSquare == SquareType.EMPTY) {
		    continue;
		}
		int boardRow = y + row;
		int boardCol = x + col;

		SquareType boardSquare = board.getSquareType(boardRow, boardCol);
		if (checkCollision(boardSquare)) {
		    return true; // Collision detected
		}
	    }
	}
	return false; // No collision
    }
}