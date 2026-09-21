package se.liu.emilo739.tetris;

public class DefaultFallHandler extends AbstractFallHandler {
    @Override
    protected boolean checkCollision(SquareType boardSquare) {
	return boardSquare != SquareType.EMPTY;
    }

    @Override
    public String getDescription() {
	return "Blocktype: Default";
    }

}
