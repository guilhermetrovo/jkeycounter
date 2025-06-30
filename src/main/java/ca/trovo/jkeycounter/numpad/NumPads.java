package ca.trovo.jkeycounter.numpad;

/**
 * Utility class to help with NumPads.
 */
class NumPads {

    /** Avoid instantiation. */
    private NumPads() {}

    /** The virtual key's index where numpad keys start. */
    public static final int NUMPAD_KEYS_INDEX_START = 96;

    /** The virtual key's index where numpad keys end. */
    public static final int NUMPAD_KEYS_INDEX_END = 111;

    /**
     * Check whether the given {@code keyCode} is within the numpad.
     *
     * @param keyCode
     *          The virtual key code to check.
     *
     * @return {@code true} when the given {@code keyCode} is within the numpad.
     *         {@code false} otherwise.
     *
     * @see #NUMPAD_KEYS_INDEX_START
     * @see #NUMPAD_KEYS_INDEX_END
     *
     */
    public static boolean isKeyCodeInNumPad(int keyCode) {
        return keyCode >= NUMPAD_KEYS_INDEX_START && keyCode <= NUMPAD_KEYS_INDEX_END;
    }
}
