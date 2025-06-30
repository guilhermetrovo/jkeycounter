package ca.trovo.jkeycounter;

import ca.trovo.jkeycounter.ui.Application;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * The main class to start up this application.
 */
public class Main {

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // Set the system's look & feel
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        // Initialize the application
        SwingUtilities.invokeLater(() -> {
            Application app = new Application();
            app.init();
        });
    }
}