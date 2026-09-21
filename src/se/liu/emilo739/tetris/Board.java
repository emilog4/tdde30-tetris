package se.liu.emilo739.tetris;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board {
    private SquareType[][] squares;
    private int width;
    private int height;
    private final static Random RND = new Random();
    private final static int MARGIN = 2;
    private final static int DOUBLE_MARGIN = 2 * MARGIN;
    private Poly falling = null;
    private Point fallingPoint = null;
    private List<BoardListener> boardListeners = new ArrayList<>();
    private TetrominoMaker tetrominoMaker = new TetrominoMaker();
    private boolean gameOver = false;
    private int score = 0;
    private boolean paused = false;
    private FallHandler fallHandler = new FallTrough();
    private static final Map<Integer,Integer> POINTS = Map.of(1, 100, 2,300, 3,500,4,800);
    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.squares = new SquareType[height + DOUBLE_MARGIN][width + DOUBLE_MARGIN];

	for (int y = 0; y < this.height + DOUBLE_MARGIN; y++) {
	    for (int x = 0; x < this.width + DOUBLE_MARGIN; x++) {
		if (x < MARGIN || y < MARGIN || x >= this.width + MARGIN || y >= this.height + MARGIN) {
		    this.squares[y][x] = SquareType.OUTSIDE;
		} else {
		    this.squares[y][x] = SquareType.EMPTY;
		}
	    }
	}
	notifyListeners();
    }

    public String getBlockStateDescription() {
	return fallHandler.getDescription();
    }
    public Point getFallingPoint() {
	return fallingPoint;
    }

    public void setFalling(final Poly falling) {
	this.falling = falling;
    }

    public void setFallingPoint(final Point fallingPoint) {
	this.fallingPoint = fallingPoint;
	notifyListeners();
    }
    public Poly getFalling() {
	return falling;
    }

    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public int getScore() {
	return score;
    }

    public SquareType[][] getSquares() {
	return squares;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
	this.gameOver = gameOver;
    }


    public boolean isPaused() {
	return paused;
    }

    public void setPaused(final boolean paused) {
	this.paused = paused;
    }

    public void tick(){
	if (gameOver || paused) {return;}
	if (getFalling() != null){ // falling poly
	    Point originalFallingPoint = new Point(fallingPoint);

	    fallingPoint.setLocation(fallingPoint.x, fallingPoint.y + 1);

	    if (fallHandler.hasCollision(this, originalFallingPoint)) {
		fallingPoint = originalFallingPoint;
		placeFallingPoly();
		removeRows();
		setFalling(null);
	    }

	} else { // no poly is falling
	    changeCollisionHandler();
	    int rnd = RND.nextInt(SquareType.values().length-2);
	    falling = tetrominoMaker.getPoly(rnd);
	    setFallingPoint(new Point(width/2,0));
	    if (fallHandler.hasCollision(this, getFallingPoint())) {
		gameOver = true;
	    }
	}
	notifyListeners();
    }

    public void addBoardListener(BoardListener bl){
	boardListeners.add(bl);
    }

    private void notifyListeners(){
       for (BoardListener boardListener : boardListeners) {
	   boardListener.boardChanged();
       }
    }


    public SquareType getSquareType(int y, int x){

	return squares[y + MARGIN][x + MARGIN];
    }

    public void setSquareType(int y, int x, SquareType squareType){

	squares[y + MARGIN][x + MARGIN] = squareType;
    }

    public void generateRandom(){
	for (int x = 0; x < getWidth() ; x++) {
	    for (int y= 0;  y < getHeight() ; y++) {
		int rnd = RND.nextInt(SquareType.values().length);
		squares[y][x] = SquareType.values()[rnd];
	    }
	}
	notifyListeners();
    }


    public SquareType getVisibleSquareAt(int x, int y){
	final SquareType boardSquare = getSquareType(y, x);
	if (falling != null){
	    int x2 = fallingPoint.x + falling.getWidth();
	    int y2 = fallingPoint.y + falling.getHeight();
	    if (x >= fallingPoint.x && x < x2 && y >= fallingPoint.y && y < y2) {
		SquareType fallingPolySquare = falling.getPoly()[y - fallingPoint.y][x - fallingPoint.x];
		if (fallingPolySquare != SquareType.EMPTY) {
		    return fallingPolySquare;
		}
	    }
	    return boardSquare;
	}
	return boardSquare;
    }


    public void move(Direction direction) {
	if(falling != null && !paused){
	Point originalFallingPoint = new Point(fallingPoint);
	if(direction == Direction.RIGHT){

	    fallingPoint.setLocation(fallingPoint.x + 1, fallingPoint.y);
	    if (fallHandler.hasCollision(this, originalFallingPoint)) {
		fallingPoint = originalFallingPoint;
	    }
	}
	if(direction == Direction.LEFT){
	    fallingPoint.setLocation(fallingPoint.x - 1, fallingPoint.y);
	    if (fallHandler.hasCollision(this , originalFallingPoint)) {
		fallingPoint = originalFallingPoint;
	    }
	}}
	notifyListeners();
    }

    private void placeFallingPoly() {
	int x = (int) fallingPoint.getX();
	int y = (int) fallingPoint.getY();
	SquareType[][] shape = falling.getPoly();

	for (int row = 0; row < falling.getHeight(); row++) {
	    for (int col = 0; col < falling.getWidth(); col++) {
		SquareType blockType = shape[row][col];
		if (blockType != SquareType.EMPTY) {
		    squares[y + row + MARGIN][x + col + MARGIN] = blockType; // Add block to board.
		}
	    }
	}
    }

    public void rotate(Direction dir) {
	if (falling == null || paused) {
	    return;
	}

	Poly originalPoly = falling;
	Poly rotatedPoly = null;
	switch(dir) {
	    case Direction.RIGHT:
		rotatedPoly = originalPoly.rotateRight();
		break;
	    case Direction.LEFT:
		rotatedPoly = originalPoly.rotateLeft();
		break;
	}
	falling = rotatedPoly;

	if (fallHandler.hasCollision(this, getFallingPoint())) {
	    falling = originalPoly;
	}

	notifyListeners();
    }


    private void removeRows() {
	int rows = 0;
	for (int y = 0; y < getHeight(); y++) {
	    if (isFullRow(y)) {
		System.out.println("Fuuulll rooooow");
		removeRow(y);
		rows++;
	    }
	}
	int points = POINTS.getOrDefault(rows,0);
	score += points;
    }

    private boolean isFullRow(final int row) {
	boolean fullRow = true;
	for (int x = 0; x < width; x++) {
	    if (getSquareType(row,x) == SquareType.EMPTY) {
		fullRow = false;
	    }
	}
	return fullRow;
    }

    private void removeRow(final int row) {
	for (int y = row - 1; y >= 0; y--) {
	    for (int x = 0; x < width; x++) {
		squares[y + 1 + MARGIN][x + MARGIN] = getSquareType(y,x);
	    }
	} // Remove the top row
	for (int x = 0; x < width; x++) {
	    squares[MARGIN][x + MARGIN] = SquareType.EMPTY;
	}
    }

    private void changeCollisionHandler() {
	// 10% chance that the collision handler will change
	int rnd = RND.nextInt(10);
	//int rnd = 2;
	if (rnd  <= 2) {
	    fallHandler = new FallTrough();
	}
	else if (rnd == 3) {
	    fallHandler = new HeavyFallHandler();
	}
	else {
	    fallHandler = new DefaultFallHandler();
	}
    }
}
