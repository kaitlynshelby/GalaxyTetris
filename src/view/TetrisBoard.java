 //TCSS 305 - Spring 2016
 // Assignment 6 - Tetris
 

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import model.Block;
import model.Board;

/**
 * An interactive playing area for the Tetris GUI.
 * 
 * @author Kaitlyn Kinerk
 * @version 1.0
 */
public class TetrisBoard extends JPanel implements Observer, PropertyChangeListener {

    /** The length of the side of a block/grid square in pixels. */
    public static final int BLOCK_SIZE = 30;

    /** Light Blue: The color to paint an "I" piece. */
    public static final Color I_COLOR = new Color(160, 151, 188);

    /** Navy Blue: The color to paint an "O" piece. */
    public static final Color O_COLOR = new Color(51, 62, 108);

    /** Magenta: The color to paint a "J" piece. */
    public static final Color J_COLOR = new Color(213, 0, 117);

    /** Lavender: The color to paint an "L" piece. */
    public static final Color L_COLOR = new Color(163, 105, 238);

    /** Orchid: The color to paint a "Z" piece. */
    public static final Color Z_COLOR = new Color(193, 16, 208);

    /** Pink: The color to paint an "S" piece. */
    public static final Color S_COLOR = new Color(255, 4, 255);

    /** Purple: The color to paint a "T" piece. */
    public static final Color T_COLOR = new Color(104, 39, 165);

    /** An invisible placeholder color to paint empty grid squares. */
    public static final Color NO_COLOR = new Color(0, 0, 0, 0);

    /**
     * A transparent black color that overlays the screen when the game is
     * paused or over.
     */
    public static final Color STOPPED_COLOR = new Color(0, 0, 0, 230);

    /** Stores data regarding the color to paint a specific Tetris shape. */
    public static final Map<Character, Color> COLOR_MAP = TetrisBoard.createColorMap();

    /** The thickness of the outline to paint around a single Tetris block. */
    private static final float STROKE_WIDTH = 2;

    /** The font type this panel will use to display messages. */
    private static final String FONT_TYPE = "Agency FB";

    /** The default timer delay. */
    private static final int INITIAL_TIMER_DELAY = 1000;

    /**
     * The height of the preview/next piece area of the string representation of
     * the board.
     */
    private static final int PREVIEW_BOARD_HEIGHT = 5;

    /** A generated serial number. */
    private static final long serialVersionUID = -6841062090904867790L;

    /** A string representing the classic game mode. */
    private static final String CLASSIC_MODE = "classic";

    /** The amount to speed up the game when next level is reached. */
    private static final double SPEED_FACTOR = 0.75;

    /** A Swing timer. */
    private Timer myTimer;

    /** The back-end board for this UI board. */
    private Board myBoard;

    /** A string representing a playable Tetris board. */
    private String myBoardString;

    /** Whether or not the game is over. */
    private boolean myGameOver;

    /** The width of this TetrisBoard. */
    private int myWidth;

    /** The height of this TetrisBoard. */
    private int myHeight;

    /** The game mode. */
    private String myGameMode;

    /** Whether or not the user is viewing the welcome screen. */
    private boolean myWelcome;

    /**
     * Constructs a new TetrisBoard.
     */
    public TetrisBoard() {
        super();

        myBoard = new Board();
        myHeight = myBoard.getHeight() * BLOCK_SIZE;
        myWidth = myBoard.getWidth() * BLOCK_SIZE;
        myGameMode = CLASSIC_MODE;
        myBoardString = myBoard.toString();
        myGameOver = false;
        myWelcome = true;
        myTimer = new Timer(INITIAL_TIMER_DELAY, new TimerListener());

        setupBoard();
    }

    /**
     * Sets up and adds functionality to this board.
     */
    private void setupBoard() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(myWidth, myHeight));
        myBoard.addObserver(this);
        enableStartupKeys();
    }

    /**
     * Creates the color map to use with all instances of this class.
     * 
     * @return a collection of mappings from characters to colors.
     */
    private static Map<Character, Color> createColorMap() {

        final Map<Character, Color> map = new HashMap<Character, Color>();
        map.put(' ', NO_COLOR);
        map.put(Block.I.getChar(), I_COLOR);
        map.put(Block.O.getChar(), O_COLOR);
        map.put(Block.J.getChar(), J_COLOR);
        map.put(Block.L.getChar(), L_COLOR);
        map.put(Block.Z.getChar(), Z_COLOR);
        map.put(Block.S.getChar(), S_COLOR);
        map.put(Block.T.getChar(), T_COLOR);
        return map;
    }

    /**
     * Pauses the timer and disables game play.
     */
    private void pauseTimer() {
        myTimer.stop();
        disableGamePlayKeys();
        repaint();
    }

    /**
     * Starts the timer and enables game play.
     */
    private void startTimer() {
        myTimer.start();
        enableGamePlayKeys();
        repaint();
    }

    /**
     * Speeds up the game.
     */
    public void faster() {
        myTimer.setDelay((int) (myTimer.getDelay() * SPEED_FACTOR));
    }

    /**
     * Sets up key bindings related to game play.
     */
    @SuppressWarnings("serial")
    private void enableGamePlayKeys() {
        if (CLASSIC_MODE.equals(myGameMode)) {
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "rotateCW");
        }
        else {
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "left");
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "right");
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "down");
            getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "rotateCW");
        }

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "drop");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "rotateCCW");

        getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.left();
            }
        });

        getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.right();
            }
        });

        getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.down();
            }
        });

        getActionMap().put("rotateCW", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.rotateCW();
            }
        });

        getActionMap().put("rotateCCW", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.rotateCCW();
            }
        });

        getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.right();
            }
        });

        getActionMap().put("drop", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.drop();
            }
        });

        getActionMap().put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                if (myTimer.isRunning()) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
    }

    /**
     * Sets up keys bindings which should always be enabled.
     */
    @SuppressWarnings("serial")
    private void enableStartupKeys() {
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "controls");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "about");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "end game");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0), "new game");

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0), "new game");
        getActionMap().put("new game", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                endGame();
                if (myGameOver || myWelcome) {
                    startNewGame();
                }
            }
        });

        getActionMap().put("controls", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                pauseTimer();
                displayControlDialog();
                if (!myWelcome) {
                    startTimer();
                }
            }
        });

        getActionMap().put("about", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                pauseTimer();
                displayAboutDialog();
                if (!myWelcome) {
                    startTimer();
                }
            }
        });

        getActionMap().put("end game", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                endGame();
            }
        });
    }

    /**
     * Disables all key bindings which control the game (pause, view controls,
     * about, and new game keys are still enabled).
     */
    private void disableGamePlayKeys() {
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "none");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "none");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "none");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "none");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "none");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "none");
    }

    /**
     * Displays a dialog explaining controls and scoring.
     */
    private void displayControlDialog() {
        final String gameInfo = "CLASSIC MODE" + "\nMove Left:  Left Arrow"
                               + "\nMove Right:  Right Arrow" + "\nMove Down:  Down Arrow"
                               + "\nDrop:  Space" + "\nRotate Clockwise:  Up Arrow"
                               + "\nRotate Counter-Clockwise:  Z\n" + "\nANTI-GRAVITY MODE"
                               + "\nMove up:  Up Arrow" + "\nRotate Clockwise:  Down Arrow\n"
                               + "\nSCORING"
                               + "\nEarn 100 points for each line cleared. As you\n"
                               + "level up, each line cleared will be worth 100 points times\n"
                               + "the number of the level you are on. For example, one line\n"
                               + "cleared at level 3 is worth 300 points.";
        JOptionPane.showMessageDialog(null, gameInfo, "Controls and scoring",
                                      JOptionPane.PLAIN_MESSAGE);

    }

    /**
     * Displays a dialog with information about this program.
     */
    private void displayAboutDialog() {
        final String message = "AUTHORS\n" + "Kaitlyn Kinerk and Alan Fowler\n"
                               + "TCSS 305 - UW Tacoma - 6/8/2016\n" + "\nIMAGE CREDIT\n"
                               + "Background: "
                               + "http://wallpapercave.com/purple-galaxy-wallpaper";
        JOptionPane.showMessageDialog(null, message, "About", JOptionPane.PLAIN_MESSAGE,
                                      new ImageIcon("icon.gif"));
    }

    /**
     * Displays a new game settings panel and resets fields based on
     * user-selected settings.
     */
    private void startNewGame() {
        final GameSettingsPanel settings = new GameSettingsPanel();
        settings.addPropertyChangeListener(this);
        final int result = JOptionPane.showOptionDialog(null, settings, "New Game Settings",
                                                        JOptionPane.DEFAULT_OPTION,
                                                        JOptionPane.PLAIN_MESSAGE, null, null,
                                                        null);

        if (result == 0) {
            settings.applyChanges();
            myWelcome = false;
            setPreferredSize(new Dimension(myWidth, myHeight));

            myBoardString = myBoard.toString();
            myGameOver = false;
            myTimer = new Timer(INITIAL_TIMER_DELAY, new TimerListener());

            myBoard.addObserver(this);
            firePropertyChange("new game", null, myBoard);

            myTimer.start();
            myBoard.newGame();
            enableGamePlayKeys();

        }
    }

    /**
     * Displays a dialog which asks the user to confirm end game, then either
     * ends the game or resumes the game based on the user's response.
     */
    private void endGame() {
        pauseTimer();
        if (!myGameOver && !myWelcome) {
            final int result = JOptionPane.showOptionDialog(null, 
                                              "Are you sure you want to end this game?",
                                              "End Current Game", JOptionPane.YES_NO_OPTION,
                                              JOptionPane.QUESTION_MESSAGE, null, null,
                                              JOptionPane.NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                myGameOver = true;
                repaint();
            } else {
                startTimer();
            }
        }
    }

    /**
     * Paints the board.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        int x;
        int y;
        if (CLASSIC_MODE.equals(myGameMode)) {
            x = 0;
            y = 0;
        } else {
            x = myWidth - BLOCK_SIZE;
            y = myHeight - BLOCK_SIZE;
        }

        // ignore preview area when starting loop
        for (int i = PREVIEW_BOARD_HEIGHT * (myBoard.getWidth() + 2); 
                        i < myBoardString.length(); i++) {

            // ignore characters that represent board edges
            if (COLOR_MAP.containsKey(myBoardString.charAt(i))) {

                paintGridSquare(g2d, x, y, i);

                // advance x and y coordinates as necessary
                if (CLASSIC_MODE.equals(myGameMode)) {
                    if (x == myWidth - BLOCK_SIZE) {
                        x = 0;
                        y += BLOCK_SIZE;
                    } else {
                        x += BLOCK_SIZE;
                    }
                } else {
                    if (x == 0) {
                        x = myWidth - BLOCK_SIZE;
                        y -= BLOCK_SIZE;
                    } else {
                        x -= BLOCK_SIZE;
                    }
                }
            }
        }

        if (!myTimer.isRunning() || myGameOver || myWelcome) {
            displayStoppedScreen(g2d);
        }
    }

    /**
     * Paints one grid square on the board.
     * 
     * @param theGraphics the Graphics2D object used for painting.
     * @param theX the x coordinate.
     * @param theY the y coordinate.
     * @param theIdx the index of the character in the board string this grid
     *            square represents.
     */
    private void paintGridSquare(final Graphics2D theGraphics, final int theX, final int theY,
                                 final int theIdx) {
        theGraphics.setPaint(COLOR_MAP.get(myBoardString.charAt(theIdx)));
        theGraphics.fill(new Rectangle2D.Double(theX, theY, BLOCK_SIZE, BLOCK_SIZE));

        // paint outline on tetris blocks only
        if (myBoardString.charAt(theIdx) != ' ') {
            theGraphics.setStroke(new BasicStroke(STROKE_WIDTH));
            theGraphics.setPaint(Color.WHITE);
            // make height and width of outline one pixel smaller so the outline
            // sits inside the bounds of the already filled shape
            theGraphics.draw(new Rectangle2D.Double(theX, theY, BLOCK_SIZE, BLOCK_SIZE));
        }

    }

    /**
     * Displays appropriate screen when game is stopped.
     * 
     * @param theGraphics the Graphics2D object used for painting.
     */
    private void displayStoppedScreen(final Graphics2D theGraphics) {
        final int relativeFontSize = myWidth / 5;
        theGraphics.setFont(new Font(FONT_TYPE, Font.BOLD, relativeFontSize));
        theGraphics.setPaint(STOPPED_COLOR);
        theGraphics.fill(new Rectangle2D.Double(0, 0, myWidth, myHeight));
        theGraphics.setPaint(Color.WHITE);

        if (myWelcome) {
            drawCenteredString("WELCOME", theGraphics);
        } else if (myGameOver) {
            drawCenteredString("GAME OVER", theGraphics);
        } else {
            drawCenteredString("PAUSED", theGraphics);
        }
    }

    /**
     * Draws a string centered horizontally and vertically on the panel.
     * 
     * @param theString the string to center.
     * @param theGraphics the graphics object used for painting.
     */
    private void drawCenteredString(final String theString, final Graphics2D theGraphics) {
        final String str = theString;
        int strWidth = 0;
        int strAscent = 0;
        int x = 0;
        int y = 0;
        final FontMetrics fm = theGraphics.getFontMetrics();

        strWidth = fm.stringWidth(str);
        strAscent = fm.getAscent();
        x = myWidth / 2 - strWidth / 2;
        y = myHeight / 2 + strAscent / 2;
        theGraphics.drawString(str, x, y);
    }

    /**
     * Repaints the board based on the updated string representation of the
     * board.
     */
    @Override
    public void update(final java.util.Observable theObj, final Object theArg) {
        if (theArg instanceof String) {
            myBoardString = (String) theArg;
            repaint();
        } else if (theArg instanceof Boolean) {
            myGameOver = (boolean) theArg;
        }
    }

    /**
     * Speeds up the game or resets fields for a new game based on user-selected
     * settings.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("speed up".equals(theEvent.getPropertyName())) {
            faster();
        } else if ("change dimensions".equals(theEvent.getPropertyName())) {
            myWidth = (int) theEvent.getOldValue();
            myHeight = (int) theEvent.getNewValue();
        } else if ("update board".equals(theEvent.getPropertyName())) {
            myGameMode = (String) theEvent.getOldValue();
            myBoard = (Board) theEvent.getNewValue();
        }
    }

    /**
     * A listener for the timer which advances the board one step.
     */
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
    }
}
