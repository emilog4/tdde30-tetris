package se.liu.emilo739.tetris;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class BoardTester
{


    public static void main(String[] args) {
	startNewGame();
    }

    public static void startNewGame(){
	Board board = new Board(20,10);
	TetrisViewerTimer viewer = new TetrisViewerTimer(board) ;

    }
}
