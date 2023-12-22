/**
 * A controller for craps game.
 *
 * @author Peter Madin
 * @version 0.0.5  November 27, 2023
 */
package controller;

import res.R;

import javax.swing.*;
import java.awt.*;

/**
 * The CrapsController class manages the
 * main GUI layout and components for a Craps game.
 * It sets up the main window and organizes
 * various panels for game and display.
 */
public class CrapsController extends JPanel {
    /**
     * A ToolKit.
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /**
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    /**
     * Default size of window.
     */
    private static final int WINDOW_SIZE = 1000;

    /**
     * private constructor to prevent call.
     */
    private CrapsController() {
    }

    /**
     * Creates and displays the main GUI for the Craps game.
     * It sets up the main window, menu bar,
     * and different panels for game functionality.
     */
    public static void createAndShowGUI() {
        final JFrame frame = new JFrame("Craps");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(R.Colors.BACKGROUND);
        MenuBar menuBar = new MenuBar(frame); // Create an instance of MenuBar
        RollPlayPanel rollPlayPanel = new RollPlayPanel(new CurrentRollPanel());
        menuBar.addPropertyChangeListener(rollPlayPanel);

        JPanel north = new JPanel();
        north.setLayout(new FlowLayout(FlowLayout.CENTER));
        north.setBackground(R.Colors.BACKGROUND);
        north.add(new CurrentRollPanel());
        frame.add(north, BorderLayout.NORTH);

        //Center Section
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER));
        center.setBackground(R.Colors.BACKGROUND);
        center.add(rollPlayPanel);
        frame.add(center, BorderLayout.CENTER);

        //bottom section
        JPanel south = new JPanel();
        south.setBackground(R.Colors.BACKGROUND);
        south.setLayout(new FlowLayout());
        south.add(new WinPanel(frame));
        frame.add(south, BorderLayout.SOUTH);
        south.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Right Section
        JPanel east = new JPanel();
        east.setLayout(new FlowLayout(FlowLayout.CENTER));
        east.setBackground(R.Colors.BACKGROUND);
        east.add(new BankPanel());
        frame.add(east, BorderLayout.EAST);

        //Left Section
        JPanel west = new JPanel();
        west.setLayout(new FlowLayout(FlowLayout.CENTER));
        west.setPreferredSize(new Dimension(155,0));
        west.setBackground(R.Colors.BACKGROUND);
        frame.add(west, BorderLayout.WEST);

        // Display the window.
        frame.setResizable(false);
        frame.pack();
        frame.setSize(WINDOW_SIZE,WINDOW_SIZE);
        frame.setLocation(SCREEN_SIZE.width / 2 - frame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - frame.getHeight() / 2);
        frame.setSize(WINDOW_SIZE ,WINDOW_SIZE);
        frame.setVisible(true);
    }
}