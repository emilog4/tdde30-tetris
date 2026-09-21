package se.liu.emilo739.tetris;


import java.util.List;

public class Highscore
{

    private final String playerName;
    private final int score;

    public Highscore(final String playerName, final int score) {
	this.playerName = playerName;
	this.score = score;
    }

    public String getPlayerName() {
	return playerName;
    }

    public int getScore() {
	return score;
    }

    @Override public String toString() {
	return playerName + " - " + score;
    }
}

