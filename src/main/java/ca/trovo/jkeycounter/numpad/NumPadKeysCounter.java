package ca.trovo.jkeycounter.numpad;

import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;


/**
 * Listener class that counters how many times the numpad keys are hit.
 */
public class NumPadKeysCounter extends GlobalKeyAdapter {
    
    /** Counter for how many numpad keys were hit. */
    private final AtomicLong counter = new AtomicLong();
    
    /** A callback function that is called when the counter is updated */
    private final Consumer<Long> counterUpdatedCallback;
    
    
    /**
     * Constructor that executes a callback function once the numpad counter is updated.
     *
     * @param counterUpdatedCallback
     *         The callback function to call, the current count will be sent as parameter.
     */
    public NumPadKeysCounter(Consumer<Long> counterUpdatedCallback) {
        this.counterUpdatedCallback = counterUpdatedCallback;
    }
    
    /**
     * Increment {@link #counter} when a numpad key is released.
     *
     * @see GlobalKeyEvent#getVirtualKeyCode()
     */
    @Override
    public void keyReleased(GlobalKeyEvent event) {
        int keyCode = event.getVirtualKeyCode();
        if (NumPads.isKeyCodeInNumPad(keyCode)) {
            this.counterUpdatedCallback.accept(this.counter.incrementAndGet());
        }
    }
    
    /**
     * Reset the numpad's key counter to zero.
     */
    public void reset() {
        counter.set(0L);
        this.counterUpdatedCallback.accept(0L);
    }
    
    /**
     * Get the current value of the numpad's key counter.
     *
     * @return The current value of the numpad's key counter.
     */
    public long value() {
        return counter.get();
    }
    
}
