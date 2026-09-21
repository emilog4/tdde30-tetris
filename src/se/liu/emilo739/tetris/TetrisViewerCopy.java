package se.liu.emilo739.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class TetrisViewerCopy
{
    private JTextArea textArea;
    private Board board;

    public TetrisViewerCopy(Board board) {
	this.board = board;
	createInterface();
	startTimer();
    }

    private void createInterface() {
	JFrame frame = new JFrame("Tetris Timer Test");
	textArea = new JTextArea(board.getHeight(), board.getWidth());

	textArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
	textArea.setEditable(false);

	frame.setLayout(new BorderLayout());
	frame.add(textArea, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);

	updateTextArea();
    }

    private void startTimer() {
	final Action doOneStep = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		board.generateRandom();
		updateTextArea();       // Uppdatera textarea
	    }
	};

	final Timer clockTimer = new Timer(1000, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }
    private void updateTextArea() {
	BoardToTextConverter boardToTextConverter = new BoardToTextConverter();
	String boardString = boardToTextConverter.convertToText(board);

	textArea.setText(boardString);
    }
}
