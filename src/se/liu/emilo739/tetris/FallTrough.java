package se.liu.emilo739.tetris;

public class FallTrough extends AbstractFallHandler {
    @Override protected boolean checkCollision(SquareType boardSquare) {
	return boardSquare == SquareType.OUTSIDE;
    }

    @Override
    public String getDescription() {
	return "Blocktype: Fallthrough";
    }
}

