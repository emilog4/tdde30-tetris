package se.liu.emilo739.tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisViewer
{
    public TetrisViewer(Board board) {
	show(board);
    }

    private void show(Board board) {
	JFrame frame = new JFrame("Tetris Viewer");
	JTextArea textArea = new JTextArea(board.getHeight(), board.getWidth());
	BoardToTextConverter boardToTextConverter = new BoardToTextConverter();
	String boardString = boardToTextConverter.convertToText(board);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	textArea.setText(boardString);
	frame.setLayout(new BorderLayout());
	frame.add(textArea, BorderLayout.CENTER);
	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));


	frame.pack();
	frame.setVisible(true);
    }

}
