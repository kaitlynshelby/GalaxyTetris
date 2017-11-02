/*
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * A game information panel to display score, current level, lines cleared, and any other 
 * relevant information on the Tetris GUI. 
 * 
 * @author Kaitlyn Kinerk
 * @version 1.0
 *
 */
public class InfoPanel extends JPanel implements Observer {
    
    /** A generated serial number. */
    private static final long serialVersionUID = 3330512423720685267L;

    /** This panel's width. */
    private static final int WIDTH = 270;

    /** This panel's height. */
    private static final int HEIGHT = 390;
    
    /** The font to use for this panel. */
    private static final String FONT = "Agency FB";
    
    /** The indentation level to use for this panel. */
    private static final int INDENT = 20;
    
    /** The number of lines that must be cleared before moving on to the next level. */
    private static final int LINES_TO_LEVEL_UP = 5;

    /** A large font size. */
    private static final int LARGE_FONT = 40;

    /** A medium font size. */
    private static final int MEDIUM_FONT = 30;

    /** A small font size. */
    private static final int SMALL_FONT = 22;

    /** A constant to multiply the score by. */
    private static final int SCORE_MULTIPLIER = 100;
    
    /** The total number of lines cleared. */
    private int myLinesCleared;
    
    /** The remaining number of lines to clear to level up. */
    private int myLinesTilNextLevel;
  
    /** The score. */
    private int myScore;
    
    /** The level. */
    private int myLevel;

    /**
     * Constructs an InfoPanel.
     */
    public InfoPanel() {
        super();
        myLinesTilNextLevel = LINES_TO_LEVEL_UP;
        myLinesCleared = 0;         
        myScore = 0;
        myLevel = 1;
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
    }
    
    
    /**
     * Paints the panel with all necessary information including the score, the level, lines 
     * cleared, lines to next level, and key control information.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        final int spacing = 30;
        
        final int scoreStartY = 60;
        // draw score
        g2d.setFont(new Font(FONT, Font.PLAIN, LARGE_FONT));
        g2d.setPaint(Color.WHITE);
        drawCenteredString("SCORE", g2d, scoreStartY); 
        g2d.setFont(new Font(FONT, Font.PLAIN, SMALL_FONT));
        drawCenteredString(String.valueOf(myScore), g2d, scoreStartY + spacing);
       
        // draw game info
        final int infoStartY = 140;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(FONT, Font.PLAIN, MEDIUM_FONT));
        g2d.drawString("Level: " + myLevel, INDENT, infoStartY);
        g2d.drawString("Lines cleared: " + myLinesCleared, INDENT, infoStartY + spacing);
        g2d.drawString("Lines to next level: " + myLinesTilNextLevel, INDENT, 
                                                               infoStartY + spacing * 2);
        
        // draw key control info
        final int keysStartY =  220;
        g2d.setFont(new Font(FONT, Font.PLAIN, SMALL_FONT));
        g2d.setColor(TetrisBoard.L_COLOR);
        g2d.drawString("N - Start new game", INDENT, keysStartY + spacing);
        g2d.setColor(Color.WHITE);
        g2d.drawString("E - End current game", INDENT, keysStartY + spacing * 2);
        g2d.drawString("P - Pause ", INDENT, keysStartY + spacing * 3);
        g2d.drawString("C - View controls / scoring ", INDENT, keysStartY + spacing * 4);
        g2d.drawString("A - About", INDENT, keysStartY + spacing * 5);
      
    }

    /** 
     * Draws a string centered on the panel. 
     * 
     * @param theString the string to center.
     * @param theGraphics the graphics object used for painting.
     * @param theY the y-coordinate to draw the string at.
     */
    private void drawCenteredString(final String theString, final Graphics2D theGraphics, 
                                    final int theY) {
        final String str = theString;
        int strWidth = 0;
        int x = 0;
        final FontMetrics fm = theGraphics.getFontMetrics();
        
        strWidth = fm.stringWidth(str);
        x = WIDTH / 2 - strWidth / 2;
        theGraphics.drawString(str, x, theY);
    }
    
    /**
     * Resets all fields to initial, new game values.
     */
    public void resetInfo() {
        myScore = 0;
        myLevel = 1;
        myLinesCleared = 0;
        myLinesTilNextLevel = LINES_TO_LEVEL_UP;
    }


    /**
     * Updates fields based on lines cleared.
     */
    @Override
    public void update(final Observable theObj, final Object theArgs) {
        if (theArgs instanceof Object[]) {
            myLinesCleared += ((Object[]) theArgs).length;
            
            // calculate level based on lines cleared 
            myLevel = myLinesCleared / LINES_TO_LEVEL_UP + 1;
            
            myLinesTilNextLevel -= ((Object[]) theArgs).length;
            if (myLinesTilNextLevel <= 0) {
                myLinesTilNextLevel += LINES_TO_LEVEL_UP;
            }

            myScore += myLevel * SCORE_MULTIPLIER * ((Object[]) theArgs).length;
            if (myLinesCleared % LINES_TO_LEVEL_UP == 0) {
                firePropertyChange("speed up", null, null);
            }
            repaint();
        } 
      
    }
}


