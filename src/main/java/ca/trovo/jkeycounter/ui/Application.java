package ca.trovo.jkeycounter.ui;

import ca.trovo.jkeycounter.JKeyCounterException;
import ca.trovo.jkeycounter.JKeyCounterProperties;
import ca.trovo.jkeycounter.numpad.NumPadTracker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The application's main entry-point.<br>
 * Draws the UI using {@code swing} and manages the button's actions with the native keyboard listener.
 *
 * <p>Exits on close.</p>
 *
 * @see NumPadTracker
 *
 */
public final class Application extends DomJFrame {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    /** Keeps track of the numpad usage. */
    private final NumPadTracker tracker;

    /**
     * Instantiate the application, building all its dependencies in the background.
     * <p><b>Call {@link #init()} to make the window visible when ready.</b></p>
     */
    public Application() {
        super("JKeyCounter v%s".formatted(JKeyCounterProperties.getProjectVersion()));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tracker = new NumPadTracker(this::updateCounter);

        setLayout();
        buildForm();
    }

    /**
     * Initializes the application by making it visible.
     */
    public void init() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Set the window's layout.
     */
    private void setLayout() {
        // ___________
        // | labels  |
        // | buttons |
        setLayout(new GridLayout(2, 1));
        setResizable(false);
    }

    /**
     * Builds the window's form.
     */
    private void buildForm() {
        buildLabels();
        buildButtons();
    }

    /**
     * Builds the form's labels.
     */
    private void buildLabels() {
        // The panel to serve as parent of all labels
        JPanel labelsPanel = new JPanel(new SpringLayout());
        addToSelf("labelsPanel", labelsPanel);

        // The two labels for the count, one for the "real label" and another for the "value"
        JLabel numpadUsageLabel = new JLabel("NumPad Usage: ");
        add("numpadUsageLabel", numpadUsageLabel, labelsPanel);

        JLabel numpadUsageValue = new JLabel();
        add("numpadUsageValue", numpadUsageValue, labelsPanel);
        updateCounter(0L);

        SpringLayouts.makeGrid(labelsPanel, 1, 2, 5, 5, 0, 2);
    }

    /**
     * Builds the form's buttons.
     */
    private void buildButtons() {
        // The panel to serve as parent of all buttons
        JPanel buttonsPanel = new JPanel(new SpringLayout());
        addToSelf("buttonsPanel", buttonsPanel);

        // Create the reset button
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> updateCounter(tracker.reset()));
        add("resetBtn", resetBtn, buttonsPanel);

        // Create the start/stop button
        JToggleButton startStopBtn = new JToggleButton("      Start      ");
        startStopBtn.addActionListener(e -> {
            if (startStopBtn.isSelected()) {
                doStart();
            } else {
                doStop();
            }

        });
        add("startStopBtn", startStopBtn, buttonsPanel);

        SpringLayouts.makeGrid(buttonsPanel, 1, 2, 5, 0, 5, 5);
    }

    /**
     * Action method to trigger when the start button is clicked.
     */
    private void doStart() {
        try {
            tracker.start();
            getToggleButton("startStopBtn").setText("Recording...");
        } catch (JKeyCounterException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            doStop();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Uh oh!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Action method to trigger when the stop button is clicked.
     */
    private void doStop() {
        tracker.stop();

        JToggleButton startStopBtn = getToggleButton("startStopBtn");
        startStopBtn.setSelected(false);
        startStopBtn.setText("      Start      ");
    }

    /**
     * Action method to trigger when the counter value should be updated.
     *
     * @param value
     *          The number to set in the "value label".
     */
    private void updateCounter(Long value) {
        getLabel("numpadUsageValue").setText("%-50s".formatted(value.toString()));
    }
}
