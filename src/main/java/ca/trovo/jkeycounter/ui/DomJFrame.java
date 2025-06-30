package ca.trovo.jkeycounter.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Base frame that uses an HTML's DOM-style map that holds all components by their names.
 *
 * @see #addToSelf(String, Component)
 * @see #add(String, Component, Container)
 *
 */
abstract class DomJFrame extends JFrame {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(DomJFrame.class.getName());

    /** HTML's DOM-style map that holds all components by their names. */
    protected final Map<String, Component> components;


    /**
     * Instantiate this class with a title.
     *
     * @param title the title for the frame
     *
     */
    protected DomJFrame(String title) {
        super(title);
        components = new HashMap<>();
    }

    /**
     * Adds the {@link Component} using the given {@code uuid} as a child of this frame.
     *
     * @param uuid
     *          The key to use for the {@code child} component being added to this frame.
     *          This {@code uuid} should be unique within all the children of this frame.
     * @param child
     *          The {@code child} component being added to this frame.
     *
     * @see #add(String, Component, Container)
     *
     */
    protected final void addToSelf(String uuid, Component child) {
        add(uuid, child, this);
    }

    /**
     * Adds the {@code child} {@link Component} onto the given {@code parent} using the {@code uuid} DOM key.
     *
     * @param uuid
     *          The key to use for the {@code child} component being added.
     *          This {@code uuid} should be unique within all the children of this DOM tree.
     * @param child
     *          The {@code child} component being added to the {@code parent}.
     * @param parent
     *          The {@code parent} component for the {@code child}
     *
     * @see #components
     *
     */
    protected final void add(String uuid, Component child, Container parent) {
        parent.add(child);
        Component previous = components.put(uuid, child);
        if (previous != null) {
            LOGGER.log(Level.WARNING, () -> "Component %s has been replaced.".formatted(uuid));
        }
    }


    /**
     * Searches the DOM for the component with the given {@code uuid}.
     *
     * @param <T>
     *          The component class to return, e.g. a button
     *
     * @param uuid
     *          The key to search for the component in the DOM.
     * @param clazz
     *          The class of the component to cast, e.g. a label
     *
     * @return The component with the given {@code uuid}.<br>
     *         {@code null} when not found.
     *
     * @throws ClassCastException When the component exists in the DOM with the given {@code uuid}
     *                            but the component's class is different from the given {@code clazz}.
     *
     */
    protected final <T extends Component> T get(String uuid, Class<T> clazz) {
        Component component = components.get(uuid);
        return clazz.cast(component);
    }

    /**
     * Utility method to search the DOM for a button with the given {@code uuid}.
     *
     * @param uuid
     *          The key to search for the button in the DOM.
     *
     * @return The button with the given {@code uuid}.<br>
     *         {@code null} when not found.
     *
     */
    protected final JToggleButton getToggleButton(String uuid) {
        return get(uuid, JToggleButton.class);
    }

    /**
     * Utility method to search the DOM for a label with the given {@code uuid}.
     *
     * @param uuid
     *          The key to search for the label in the DOM.
     *
     * @return The label with the given {@code uuid}.<br>
     *         {@code null} when not found.
     *
     */
    protected final JLabel getLabel(String uuid) {
        return get(uuid, JLabel.class);
    }
}
