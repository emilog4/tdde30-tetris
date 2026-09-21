package se.liu.emilo739.tetris;

import javax.swing.*;
import java.net.URL;

public class ImageViewer
{
    private JFrame frame = null;

    public ImageViewer(final JFrame frame) {
	this.frame = frame;
    }

    public void showTetrisLogo() {
	frame = new JFrame("Tetris Viewer");
	final URL image = ClassLoader.getSystemResource("images/Tetris_logo.png");
	frame.setSize(1000, 694);
	ImageIcon icon = new ImageIcon(image);
	JLabel label = new JLabel(icon);
	frame.add(label);
	frame.setVisible(true);

    }

    public void removeTetrisLogo() {
	frame.dispose();
    }

}
