package se.liu.emilo739.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class TetrisViewerTimer
{
    private Board board;
    private HighscoreList highscoreList = null;
    private JFrame frame = null;
    private JLabel scoreLabel;
    private JLabel blockStateLabel;
    private Timer clockTimer;
    private int tickInterval = 0;
    private int timeElapsed = 0; // Tracks time passed in milliseconds
    private static final int SPEED_INCREASE_INTERVAL = 5000; // Increase speed every 5 seconds


    public TetrisViewerTimer(Board board) {
	this.board = board;
	loadHighscores();
	showTetrisLogo();
	show();
	startTimer();
    }

    private void show() {
	frame = new JFrame("Tetris Viewer");
	final JMenuBar bar = new JMenuBar();
	final JMenu file = new JMenu("Options");
	final JMenuItem exit = new JMenuItem("Exit");

	exit.addActionListener(actionEvent -> closeWindow());
	file.add(exit);

	final JMenuItem restart = new JMenuItem("Restart");
	restart.addActionListener(actionEvent -> restartGame());
	file.add(restart);

	final JMenuItem paused = new JMenuItem("Paused");
	paused.addActionListener(actionEvent -> pauseGame());
	file.add(paused);


	bar.add(file);
	scoreLabel = new JLabel("Score");
	scoreLabel.setFont(new Font("Arial",Font.BOLD,15));
	blockStateLabel = new JLabel("Blockstate");
	blockStateLabel.setFont(new Font("Arial",Font.BOLD,15));
	bar.add(scoreLabel);
	bar.add(blockStateLabel);
	frame.setJMenuBar(bar);
	TetrisComponent tetrisComponent = new TetrisComponent(board);
	frame.add(tetrisComponent);
	board.addBoardListener(tetrisComponent);
	frame.pack();
	frame.setVisible(true);
    }

    private void startTimer() {

	tickInterval = 400; // Start at 1 second (1000ms)
	 final int minInterval = 100; // Minimum allowed interval
	 final double speedIncreaseFactor = 0.95; // 5% speed increase


	final Action doOneStep	 = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		// board.generateRandom();
		board.tick();
		scoreLabel.setText("Score: " + board.getScore());
		blockStateLabel.setText(board.getBlockStateDescription());
		if (board.isGameOver()){
		    clockTimer.stop();
		    enterHighscore();
		    showHighscores();
		}
		if (timeElapsed >= SPEED_INCREASE_INTERVAL && !board.isPaused()) { // Only adjust speed every 5 seconds
		    timeElapsed = 0; // Reset 5 second timer for timer
		    if (tickInterval > minInterval) {
			// Conversion from double to int
			tickInterval *= speedIncreaseFactor; // Reduce delay
			clockTimer.setDelay( tickInterval); // Apply new speed
		    }
		}
		timeElapsed += tickInterval;
	    }

	};
	clockTimer = new Timer(tickInterval, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
    }

    private void closeWindow() {
	Object[] options = {
		"Yes, please",
		"No, thanks",
	};
	int optionChosen = JOptionPane.showOptionDialog(
		frame, 	// A window that “owns” the dialog
		"Would you like to exit?","Quit game?",
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		options,	// Custom text
		options[1]	// Default choice
	);
	if(optionChosen == 0){
	System.exit(0);
	}
    }

    private void restartGame() {
	Object[] options = {
		"Yes, please",
		"No, thanks",
	};
	int optionChosen = JOptionPane.showOptionDialog(
		frame, 	// A window that “owns” the dialog
		"Would you like to restart the game?","Restart the game?",
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.QUESTION_MESSAGE,
		null,
		options,	// Custom text
		options[1]	// Default choice
	);
	if(optionChosen == 0){
	    if(board.isGameOver()) {
		frame.dispose();
		BoardTester.startNewGame();
	    }
	    else{
	    board.setGameOver(true);
	    }
	}
    }

    private void pauseGame(){

	board.setPaused(!board.isPaused());
	if (board.isPaused()) {
	    clockTimer.stop();
	}
	else{clockTimer.start();}
	System.out.println("Klicked pause button. Game is : " + board.isPaused());
    }

    private void enterHighscore() {
	String name = JOptionPane.showInputDialog("Save your score", "Enter a name");

	if (name != null && !name.trim().isEmpty()) {
	    while (true) {
		try {
		    highscoreList.addHighscore(new Highscore(name.trim(), board.getScore()));
		    highscoreList.saveHighscores();
		    return;
		} catch (IOException e) { // Handle  errors
		    int option = JOptionPane.showConfirmDialog(
			    null,
			    "Error writing to highscore list.\nWould you like to try again?",
			    "Error",
			    JOptionPane.YES_NO_OPTION,
			    JOptionPane.ERROR_MESSAGE
		    );

		    if (option != JOptionPane.YES_OPTION) {
			System.err.println("Could not save highscore: " + e.getMessage());
			return;
		    }
		}
	    }
	}
    }

    private void loadHighscores(){
	while (true){
	    try {
		highscoreList = new HighscoreList();
		return;
	    }
	    catch (IOException e) {
		int option = JOptionPane.showConfirmDialog(null, "Could not load highscorelist.\nWould you like to try again?", "Error",
							   JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
		if (option != JOptionPane.YES_OPTION) {
		    System.err.println("Could not read highscore: " + e.getMessage());
		    return;
		}
	    }
	}
    }

    private void showHighscores(){
	StringBuilder highscoreText = new StringBuilder("Highscore : ");
	for (Highscore highscore : highscoreList.getHighscores()){
	    highscoreText.append(highscore).append("\n");
	}
	JOptionPane.showMessageDialog(frame , highscoreText);
    }

    private void showTetrisLogo() {
	ImageViewer imageViewer = new ImageViewer(frame);
	imageViewer.showTetrisLogo();
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException ignored) {
	    Thread.currentThread().interrupt();
	}
	imageViewer.removeTetrisLogo();
    }

//    private void showBlockState(){
//	StringBuilder blockStateText = new StringBuilder(board.getBlockStateDescription());
//	JOptionPane.showMessageDialog(frame , blockStateText);
//    }
}


