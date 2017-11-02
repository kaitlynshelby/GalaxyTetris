/*
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris 
 */
package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Board;

/**
 * The panel to place on the pop-up dialog which displays every time a new Tetris game is
 * started. This panel allows the user to choose a board size and a game mode.
 * 
 * @author Kaitlyn Kinerk
 * @version 1.0
 */
public class GameSettingsPanel extends JPanel {

    /** A generated ID number. */
    private static final long serialVersionUID = -383964227137787540L;
    
    /** The major tick spacing for a JSlider. */
    private static final int MAJOR_TICK_SPACING = 5;
    
    /** The minor tick spacing for a JSlider. */
    private static final int MINOR_TICK_SPACING = 1;

    /** The default width slider setting. */
    private static final int INITIAL_WIDTH_CHOICE = 10;

    /** The default height slider setting. */
    private static final int INITAL_HEIGHT_CHOICE = 20;

    /** The maximum board width value. */
    private static final int MAX_WIDTH_VALUE = 30;

    /** The maximum board height value. */
    private static final int MAX_HEIGHT_VALUE = 25;

    /** The minimum board width value. */
    private static final int MIN_WIDTH_VALUE = 5;

    /** The minimum board height value. */
    private static final int MIN_HEIGHT_VALUE = 10;

    /** A string to identify classic mode. */
    private static final String CLASSIC_MODE = "classic";

    /** A string to identify anti-gravity mode. */
    private static final String ANTI_GRAVITY_MODE = "gravity";
    
    /** The width slider to use for this panel. */
    private final JSlider myWidthSlider;
    
    /** The height slider to use for this panel. */
    private final JSlider myHeightSlider;
    
    /** The selected grid width size. */
    private int myGridWidth;
    
    /** The selected grid height size. */
    private int myGridHeight;
    
    /** The selected game mode. */
    private String myMode;

    /**
     * Constructs a game settings panel with all necessary components.
     */
    public GameSettingsPanel() {
        super();
        myWidthSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_WIDTH_VALUE, 
                                    MAX_WIDTH_VALUE, INITIAL_WIDTH_CHOICE);
        
        myHeightSlider = new JSlider(SwingConstants.HORIZONTAL, MIN_HEIGHT_VALUE,
                                     MAX_HEIGHT_VALUE, INITAL_HEIGHT_CHOICE);
        
        myGridWidth = myWidthSlider.getValue();
        myGridHeight = myHeightSlider.getValue();
        myMode = CLASSIC_MODE;

        setupSliders();
        layoutPanel();
    }

    /**
     * Constructs width and height sliders with default settings and liseteners.
     */
    private void setupSliders() {
        myWidthSlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        myWidthSlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        myWidthSlider.setPaintTicks(true);
        myWidthSlider.setPaintLabels(true);

        myHeightSlider.setMajorTickSpacing(MAJOR_TICK_SPACING);
        myHeightSlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        myHeightSlider.setPaintTicks(true);
        myHeightSlider.setPaintLabels(true);

        myWidthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myGridWidth = myWidthSlider.getValue();
            }
        });

        myHeightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                myGridHeight = myHeightSlider.getValue();
            }
        });
    }

    /**
     * Sets the panels layout manager and adds all necessary components.
     */
    private void layoutPanel() {
        setLayout(new GridLayout(0, 1));

        add(new JLabel("CHOOSE BOARD SIZE"));
        add(new JLabel("Width"));
        add(myWidthSlider);
        add(new JLabel("Height"));
        add(myHeightSlider);

        add(new JLabel("CHOOSE GAME MODE"));
        addGameModeButtons();

    }

    /**
     * Adds game mode selection radio buttons.
     */
    private void addGameModeButtons() {
        final JRadioButton classic =
                        new JRadioButton("Classic mode - A standard game of" + " Tetris.");
        final JRadioButton gravity =
                        new JRadioButton("Anti-Gravity mode - Pieces float"
                                         + " upwards; there's no gravity in space!");

        final ButtonGroup btns = new ButtonGroup();

        btns.add(classic);
        btns.add(gravity);

        classic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myMode = CLASSIC_MODE;
            }
        });

        gravity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myMode = ANTI_GRAVITY_MODE;

            }
        });

        add(classic);
        add(gravity);

        classic.setSelected(true);

    }
    
    /**
     * Updates connected Tetris board with all user-selected settings.
     */
    public void applyChanges() {
        final int width = myGridWidth * TetrisBoard.BLOCK_SIZE;
        final int height = myGridHeight * TetrisBoard.BLOCK_SIZE;
        final String mode = myMode;
        final Board board = new Board(myGridWidth, myGridHeight);
        
        firePropertyChange("change dimensions", width, height);
        firePropertyChange("update board", mode, board);

    }
}
