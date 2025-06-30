package ca.trovo.jkeycounter;


import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Utility class for loading properties from within the application's JAR.
 */
public class JKeyCounterProperties {

    /** Logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(JKeyCounterProperties.class.getName());

    /** The name of the properties file to read from within the application's JAR. */
    private static final String PROPERTIES_FILE_NAME = "application.properties";

    /** The properties with the values loaded from {@link #PROPERTIES_FILE_NAME}. */
    private static final Properties PROPERTIES = new Properties();

    /** The property name for the project's version number. */
    private static final String PROJECT_VERSION = "ca.trovo.jkeycounter.version";

    /** The default property name for the project's version number, should be replaced during build time. */
    private static final String PROJECT_VERSION_DEFAULT = "${project.version}";



    static {
        // Load the properties from the file
        try (InputStream input = JKeyCounterProperties.class.getResourceAsStream(PROPERTIES_FILE_NAME)) {
            PROPERTIES.load(input);

            // Validate the properties have been loaded successfully.
            testProperties();

        } catch (Exception ex) {
            LOGGER.log(
                Level.SEVERE,
                "Failed to load properties as resource from file %s from URL %s. %s".formatted(
                    PROPERTIES_FILE_NAME,
                    JKeyCounterProperties.class.getResource(PROPERTIES_FILE_NAME),
                    ex.getMessage()
                ),
                ex
            );
            System.exit(1);
        }
    }


    /**
     * Verifies that the application's properties have been read successfully.
     *
     * @throws JKeyCounterException
     *          When the application's properties failed to load.
     */
    private static void testProperties() throws JKeyCounterException {
        // Validate the project's version has been loaded successfully
        String version = getProjectVersion();
        if (version == null || version.isBlank()) {
            throw new JKeyCounterException("Cannot read version property or it is blank.");
        } else if (version.trim().equalsIgnoreCase(PROJECT_VERSION_DEFAULT)) {
            throw new JKeyCounterException("Version property has the default value.");
        }
    }

    /**
     * Get the value of the project's version.
     * @return The value of the project's version.
     * @see #PROJECT_VERSION
     */
    public static String getProjectVersion() {
        return PROPERTIES.getProperty(PROJECT_VERSION);
    }

    /** Avoid instantiation. */
    private JKeyCounterProperties() {}
}
