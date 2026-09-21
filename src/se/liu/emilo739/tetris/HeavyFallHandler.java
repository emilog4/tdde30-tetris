package se.liu.emilo739.tetris;


import java.awt.*;

public class HeavyFallHandler extends AbstractFallHandler
{
    @Override protected boolean checkCollision(final SquareType boardSquare) {
	return boardSquare != SquareType.EMPTY;
    }

    @Override public boolean hasCollision(final Board board, Point previousPosition) {
	if (previousPosition == null) {
	    return super.hasCollision(board, previousPosition);
	}

	int previousX = previousPosition.x;
	int previousY = previousPosition.y;
	int currentX = board.getFallingPoint().x;
	int currentY = board.getFallingPoint().y;
	// Uses the default handeling if a player attempts to move sideways
	if (previousX != currentX) {
	    return super.hasCollision(board, previousPosition);
	}
	// Uses the heavy fallhandeling when the tetromino is moving down
	SquareType[][] fallingPoly = board.getFalling().getPoly();
	for (int row = 0; row < board.getFalling().getHeight(); row++) {
	    for (int col = 0; col < board.getFalling().getWidth(); col++) {
		SquareType blockSquare = fallingPoly[row][col];
		if (blockSquare == SquareType.EMPTY) {
		    continue;
		}
		int boardRow = currentY + row;
		int boardCol = currentX + col;
		SquareType boardSquare = board.getSquareType(boardRow, boardCol);

		if (boardSquare == SquareType.OUTSIDE) {
		    return true;
		}
		if (boardSquare != SquareType.EMPTY) {
		    if (!canPushDown(board, boardRow, boardCol)) {
			return true;
		    }
		}
	    }

	    // Moves the pieces down

	    for (int col = 0; col < fallingPoly.length; col++) {
		if (fallingPoly[row][col] != SquareType.EMPTY) {
		    int boardCol = board.getFallingPoint().x + col;
		    int boardRow = board.getFallingPoint().y + row;

		    boolean hasNotFoundEmptySpace = true;
		    int fallingBlockRow = boardRow;
			// Finds the first empty square below it.
		    while (hasNotFoundEmptySpace) {
			if (board.getSquareType(fallingBlockRow, boardCol) == SquareType.EMPTY) {
			    hasNotFoundEmptySpace = false;
			} else {
			    fallingBlockRow++;
			}
		    }

			// Moves the row that has an empty space below down. From the bottom to the top.
		    for (int i = fallingBlockRow; i > boardRow; i--) {
			board.setSquareType(i, boardCol, board.getSquareType(i - 1, boardCol));
		    }
		    board.setSquareType(boardRow, boardCol, SquareType.EMPTY);

		}
	    }
	}
	return false;
    }

    private boolean canPushDown(final Board board, int boardRow, final int boardCol) {
	while (boardRow < board.getHeight() - 1) {
	    boardRow++;
	    if (board.getSquareType(boardRow, boardCol) == SquareType.EMPTY) {
		return true;
	    }
	}
	return false;
    }



    @Override public String getDescription() {
	return "Blocktype: Heavy";
    }
}
