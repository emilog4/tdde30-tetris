package se.liu.emilo739.tetris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HighscoreList {
    // Code analysis complains that the FILE_PATH is not utilising ClassLoader.getSystemResource(). From my understanding its not possible to write to that location.
    private final static String FILE_PATH = "src/se/liu/emilo739/tetris/score/highscore.json";
    private static final String TEMP_FILE_PATH = FILE_PATH + ".tmp";
    private final List<Highscore> highscores;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public HighscoreList() throws IOException, FileNotFoundException {
	this.highscores = readHighscoreFromFile();
    }

    public void addHighscore(Highscore highscore) {
	highscores.add(highscore);
	highscores.sort(new ScoreComparator());
    }

    public List<Highscore> getHighscores() {
	return highscores.subList(0, Math.min(10, highscores.size()));
    }

    public void saveHighscores() throws IOException {
	File tempFile = new File(TEMP_FILE_PATH);
	try (Writer writer = new FileWriter(tempFile)) {
	    GSON.toJson(highscores, writer);
	}

	File originalFile = new File(FILE_PATH);
	if (!tempFile.renameTo(originalFile)) {
	    throw new IOException("Could not rename " + tempFile + " to " + originalFile);
	}
    }

    private List<Highscore> readHighscoreFromFile() throws IOException, FileNotFoundException {
	File file = new File(FILE_PATH);
	if (!file.exists()) {
	    return new ArrayList<>();
	}
	try (Reader reader = new FileReader(file)) {
	    Type highscoreTypeToken = new TypeToken<List<Highscore>>() {}.getType();
	    return GSON.fromJson(reader, highscoreTypeToken);
	}
    }

}