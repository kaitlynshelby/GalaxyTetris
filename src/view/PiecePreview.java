/*
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Point;
import model.TetrisPiece;


/**
 * A panel which displays the next piece to fall down the board.
 * 
 * @author Kaitlyn Kinerk
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class PiecePreview extends JPanel implements Observer {
    
    /** The width for this panel. */
    public static final int WIDTH = 270;
    
    /** The height for this panel. */
    public static final int HEIGHT = 190;
    
    /** A starting point to draw the preview shape from so that it appears centered. */
    public static final Point CENTER = new Point(WIDTH / 3, HEIGHT / 3);

    /** The side length of a block/grid square. */
    private static final int BLOCK_SIZE = 30;

    /** The Tetris piece to display. */
    private TetrisPiece myNextPiece;

    /**
     * Constructs a piece preview panel.
     */
    public PiecePreview() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
    }
    
    /**
     * Paints the panel.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (myNextPiece != null) {
            final model.Point[] points = myNextPiece.getPoints();
        
            Color color;
            for (final model.Point p : points) {
           
                final int x = translateXCoord(p.x());
                final int y = translateYCoord(p.y());
                
                // since this panel is connected to a Tetris board, use the same 
                // paint style/colors
                color = TetrisBoard.COLOR_MAP.get(myNextPiece.getBlock().getChar());
                g2d.setPaint(color);
                g2d.fill(new Rectangle2D.Double(x, y, BLOCK_SIZE, BLOCK_SIZE));
                g2d.setPaint(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new Rectangle2D.Double(x, y, BLOCK_SIZE, BLOCK_SIZE));
            }   
        }
    }
    
    /**
     * Returns a translated x-coordinate to draw a Tetris block at based on the
     * local coordinates of the Tetris shape. 
     *      
     * @param theLocalX the local x-coordinate of a Tetris block to translate.
     * @return the translated x-coordinate.
     */
    private int translateXCoord(final int theLocalX)  {
        final int n = theLocalX;
        final int x = CENTER.x();
        
        return (int) (x + n * BLOCK_SIZE);
    }
    
    /**
    * Returns translated y-coordinate to draw a Tetris block at based on the
    * local coordinates of the Tetris shape.
    * 
    * @param theLocalY the local y-coordinate of a Tetris block to translate.
    * @return the translated y-coordinate.
    */
    private int translateYCoord(final int theLocalY)  {
        int n = theLocalY;
        final int y = CENTER.y();
        
        if (n == 2) {
            n = y;
        } else if (n == 1) {
            n = (int) (y + BLOCK_SIZE);
        } else if (n == 0) {
            n = (int) (y + 2 * BLOCK_SIZE);
        } else {
            n = (int) (y - BLOCK_SIZE);
        }
        
        return n;
    }
    
    /**
     * Retrieves the next piece to fall and repaints the panel.
     */
    @Override
    public void update(final Observable theObj, final Object theArg) {
        if (theArg instanceof TetrisPiece) {
            myNextPiece = (TetrisPiece) theArg;
            repaint();
        }   
    }
}
