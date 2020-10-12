package krzysztof.property.reader;

import org.junit.Assert;
import org.junit.Test;

public class PropertyReaderTest {

	@Test(expected = PropertyLoadException.class)
	public void no_config_should_throw_exception() {
		new PropertyReader(null, this.getClass().getClassLoader());
	}

	@Test
	public void read_prop() {
		PropertyReader pr = new PropertyReader("test.config.properties", this.getClass().getClassLoader());

		Assert.assertEquals("test.value", pr.getProperty("test.property"));
	}

	@Test(expected = PropertyLoadException.class)
	public void not_existing_prop() {
		PropertyReader pr = new PropertyReader("test.config.properties", this.getClass().getClassLoader());
		pr.getProperty("not.existing");
	}

}
