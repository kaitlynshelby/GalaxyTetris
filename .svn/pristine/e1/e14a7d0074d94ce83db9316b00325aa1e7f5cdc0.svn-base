/*
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris 
 */
package view;

import java.awt.EventQueue;

/**
 * A driver class for the Tetris program.
 * 
 * @author Kaitlyn Kinerk
 * @version 1.0
 *
 */
public final class TetrisMain {

    /**
     * Private constructor to inhibit instantiation.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }
    
    /**
     * Main method.
     * 
     * @param theArgs command-line arguments; ignored.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI();
            }
        });
    }
}
