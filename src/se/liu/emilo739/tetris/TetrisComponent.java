package se.liu.emilo739.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EnumMap;



    public class TetrisComponent extends JComponent implements BoardListener {
	private static final int SQUARE_SIZE = 30; // Storlek på varje ruta
	private Board board; // Referens till spelbrädet

	// EnumMap för att mappa SquareType till färger
	private final static EnumMap<SquareType, Color> SQUARE_COLORS = createColorMap();

	public TetrisComponent(Board board) {
	    this.board = board;
	    setUpKeyBindings();
	}

	private static EnumMap<SquareType, Color> createColorMap() {
	    EnumMap<SquareType, Color> map = new EnumMap<>(SquareType.class);
	    map.put(SquareType.EMPTY, Color.WHITE);
	    map.put(SquareType.OUTSIDE, Color.MAGENTA);
	    map.put(SquareType.I, Color.CYAN);
	    map.put(SquareType.O, Color.YELLOW);
	    map.put(SquareType.T, Color.MAGENTA);
	    map.put(SquareType.S, Color.GREEN);
	    map.put(SquareType.Z, Color.RED);
	    map.put(SquareType.J, Color.BLUE);
	    map.put(SquareType.L, Color.ORANGE);
	    return map;
	}

	public Dimension getPreferredSize(){
	    return new Dimension(SQUARE_SIZE* board.getWidth(),SQUARE_SIZE*board.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    final Graphics2D g2d = (Graphics2D) g;

	    int rows = board.getHeight();
	    int cols = board.getWidth();

	    for (int row = 0; row < rows; row++) {
		for (int col = 0; col < cols; col++) {
		    SquareType square = board.getVisibleSquareAt(col, row);

		    if (square != SquareType.EMPTY) { // Only draw non-empty squares
			g2d.setColor(SQUARE_COLORS.get(square));
			g2d.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		    }
			// Draw a border
			g2d.setColor(Color.GRAY);
			g2d.drawRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

		}
	    }
	}


	@Override public void boardChanged() {
	    repaint();
	}

	private void setUpKeyBindings() {
	    // Register keybindings
	    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "move_left");
	    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "move_right");
	    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "rotate_left");
	    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "rotate_right");

	    // Map actions to the keys
	    this.getActionMap().put("move_left", new MoveAction(Direction.LEFT));
	    this.getActionMap().put("move_right", new MoveAction(Direction.RIGHT));
	    this.getActionMap().put("rotate_left", new RotateAction(Direction.LEFT));
	    this.getActionMap().put("rotate_right", new RotateAction(Direction.RIGHT));
	}


	private class MoveAction extends AbstractAction {
	    private final Direction direction;

	    private MoveAction(Direction direction) {
		this.direction = direction;
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
		board.move(direction);
	    }
	}

	private class RotateAction extends AbstractAction {
	    private final Direction direction;

	    private RotateAction(Direction direction) {
		this.direction = direction;
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
		board.rotate(direction);
	    }
	}
    }

