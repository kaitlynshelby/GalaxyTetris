/*
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import model.Board;

/**
 * The Tetris GUI.
 * 
 * @author Kaitlyn Kinerk
 * @version 1.0
 */
public class TetrisGUI extends JFrame implements PropertyChangeListener {

    /** A generated ID number. */
    private static final long serialVersionUID = -633517226180045637L;

    /** The amount of padding to put around components. */
    private static final int PADDING = 20;

    /** The playable game board to add to this frame. */
    private final TetrisBoard myTetrisBoard;

    /** The piece preview panel to add to this frame. */
    private final PiecePreview myPiecePreview;

    /** The info panel to add to this frame. */
    private final InfoPanel myInfoPanel;

    /** This frame's left column which contains the game board. */
    private final Box myLeftColumn;
 
    /** This frame's right column which contains the preview panel and info panel. */
    private final Box myRightColumn;

    /**
     * Constructs the GUI with all necessary components and registers property change
     * listeners.
     */
    public TetrisGUI() {
        super();
        
        myLeftColumn = new Box(BoxLayout.X_AXIS);
        myRightColumn = new Box(BoxLayout.Y_AXIS);
        myTetrisBoard = new TetrisBoard();
        myPiecePreview = new PiecePreview();
        myInfoPanel = new InfoPanel();
        
        myTetrisBoard.addPropertyChangeListener(this);
        myInfoPanel.addPropertyChangeListener(myTetrisBoard);
       
        setupFrame();
        setVisible(true);
    }
    
    /**
     * Sets up the JFrame.
     */
    private void setupFrame() {
        setTitle("Galaxy Tetris");
        final ImageIcon icon = new ImageIcon("icon.gif"); 
        setIconImage(icon.getImage());
        
        final Image bg = Toolkit.getDefaultToolkit().getImage("galaxy.jpg");
        setContentPane(new BackgroundImage(bg));

        setLayout(new FlowLayout(FlowLayout.CENTER, PADDING, PADDING));

        addLeftColumn();
        addRightColumn();
       
        pack();
        setResizable(false);
    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
    }
    
    
    /**
     * Adds the left column containing the game board to the frame.
     */
    private void addLeftColumn() {
        myLeftColumn.add(myTetrisBoard);
        add(myLeftColumn);   
    }
    
    /**
     * Adds the right column containing the piece preview panel and game information panel to 
     * the frame.
     */
    private void addRightColumn() {
     
        myRightColumn.add(myPiecePreview);
        myRightColumn.add(Box.createVerticalStrut(PADDING));
        myRightColumn.add(myInfoPanel);
        add(myRightColumn);
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("new game".equals(theEvent.getPropertyName())) {
            myInfoPanel.resetInfo();

            myLeftColumn.invalidate();
            myLeftColumn.validate();
            myLeftColumn.repaint();
            
            myRightColumn.invalidate();
            myRightColumn.validate();
            myRightColumn.repaint();
            
            invalidate();
            validate();
            repaint();
            pack();
            
            final Board board = (Board) theEvent.getNewValue();
            
            board.addObserver(myInfoPanel);
            board.addObserver(myPiecePreview);

        }
    }

    /** 
     * Creates an image that can be used as a background for a frame. 
     * 
     * @author Kaitlyn Kinerk
     * @version 1.0
     */
    private class BackgroundImage extends JComponent {
        
        /** A generated ID number. */
        private static final long serialVersionUID = 5224298006783507524L;
        
        /** The image to use for the background. */
        private final Image myImage;
        
        /**
         * Constructs a BackgroundImage using the given image.
         * 
         * @param theImage the image to use.
         */
        BackgroundImage(final Image theImage) {
            super();
            myImage = theImage;
        }
        
        @Override
        public void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);
            final Graphics2D g2d = (Graphics2D) theGraphics;
            
            g2d.drawImage(myImage, 0, 0, this);
        }
    }

}
