package view;

import controller.CrapsController;
import javax.swing.UIManager;
import javax.swing.*;
import java.awt.*;

/**
 * The driver for the craps game.
 * @author Peter Madin
 * @version 0.0.1 November 27, 2023
 */
public class CrapsApplication {

    /**
     * Private empty constructor to avoid accidental instantiation.
     */
    private CrapsApplication() {}

    /**
     * Creates the JFrame to display the game of craps.
     * @param theArgs Command line arguments, ignored.
     */
    public static void main(String[] theArgs) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIDefaults defaults = UIManager.getLookAndFeelDefaults();
                    defaults.put("Label.font", new Font("Helvetica", Font.BOLD, 16));

                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CrapsController.createAndShowGUI();
            }
        });
    }
}