package ca.trovo.jkeycounter.numpad;

import ca.trovo.jkeycounter.JKeyCounterException;
import lc.kra.system.keyboard.GlobalKeyboardHook;

import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * Handles native bindings to listen for keystrokes and listen for numpad events.
 */
public class NumPadTracker implements AutoCloseable {

    /** The logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(NumPadTracker.class.getName());


    /** The native keyboard hook to listen for keystrokes. */
    private GlobalKeyboardHook hook;

    /** The numpad listener. */
    private final NumPadKeysCounter listener;


    /**
     * Instantiate the keyboard listener and all dependencies.
     * <p><b>Call {@link #start()} to listen.</b></p>
     */
    public NumPadTracker(Consumer<Long> counterUpdatedCallback) {
        listener = new NumPadKeysCounter(counterUpdatedCallback);
    }

    /**
     * Initializes the native keyboard binder and start listening for keystrokes.
     * @return the native keyboard hook.
     * @throws JKeyCounterException when the native hook fails to bind.
     */
    private GlobalKeyboardHook newHookAndListen() throws JKeyCounterException {
        try {
            // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
            GlobalKeyboardHook hook = new GlobalKeyboardHook(true);
            hook.addKeyListener(listener);
            return hook;
        } catch (UnsatisfiedLinkError | RuntimeException ex) {
            throw JKeyCounterException.nativeBinding(ex);
        }
    }

    /**
     * Initializes the native keyboard binder and start listening for keystrokes.
     * <p>Only one binding can exist at a time, so {@link #stop()} will be called if one exists.</p>
     *
     * @throws JKeyCounterException when the native hook fails to bind.
     * @see #stop()
     */
    public void start() throws JKeyCounterException {
        if (hook != null) {
            LOGGER.fine("Trying to start an already-started tracker. Pausing it first...");
            stop();
        }

        hook = newHookAndListen();
    }

    /**
     * Stops listening for keystrokes and unhook the native keyboard binder.
     */
    public void stop() {
        if (hook == null) {
            LOGGER.fine("Trying to stop an already-stopped tracker. Nothing to do...");
            return;
        }

        hook.removeKeyListener(listener);
        hook.shutdownHook();
        hook = null;
    }

    /**
     * Gets the number of numpad keystrokes that were heard.
     * @return The number of numpad keystrokes that were heard.
     */
    public long value() {
        return listener.value();
    }

    /**
     * Reset the numpad's key counter to zero.
     */
    public long reset() {
        listener.reset();
        return 0L;
    }

    /**
     * Same as {@link #stop()}, used for {@link AutoCloseable} to help cleaning up.
     */
    @Override
    public void close() {
        stop();
    }
}
