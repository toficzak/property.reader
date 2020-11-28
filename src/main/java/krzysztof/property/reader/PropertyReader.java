package krzysztof.property.reader;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * <p>
 * How to use this class:<br />
 * <br />
 * <em>private final PropertyReader propertyReader;</em><br />
 * <em>private final String dbHost;</em>
 * </p>
 * <p>
 * <em>public DatabaseConnector() {</em><br />
 * <em>this.propertyReader = new PropertyReader("config.properties",
 * this.getClass().getClassLoader());</em>
 * </p>
 * <p>
 * <em>this.dbHost = propertyReader.getProperty("db.host");</em><br />
 * <em>}</em>
 * </p>
 * 
 * @author krzysztof
 *
 */
public class PropertyReader {

  private static final Logger LOGGER = Logger.getLogger(PropertyReader.class.getSimpleName());

  private final String propertyFilePath;
  private final Properties properties;
  private final ClassLoader cl;

  public PropertyReader(String propertyFilePath, ClassLoader cl) {
    this.propertyFilePath = propertyFilePath;
    this.cl = cl;
    this.properties = this.loadPropValues();
  }

  public String getProperty(String key) {
    Object prop = properties.get(key);
    if (prop == null) {
      String message = String.format("No property: %s.", key);
      LOGGER.log(Level.WARNING, message);
      throw new PropertyLoadException(message);
    }
    return (String) prop;
  }

  private Properties loadPropValues() {

    if (this.propertyFilePath == null || this.propertyFilePath.isBlank()
        || Objects.isNull(cl.getResource(propertyFilePath))) {
      String message =
          String.format("property file '%s' not found in the classpath", propertyFilePath);
      LOGGER.log(Level.WARNING, message);
      throw new PropertyLoadException("No config file.");
    }

    try (InputStream inputStream = cl.getResourceAsStream(propertyFilePath)) {
      Properties props = new Properties();
      props.load(inputStream);
      return props;
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, String.format("Exception: %s", e));
      throw new PropertyLoadException();
    }
  }
}
