
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Junit test class for SRTDriver.class
 * @author mm
 * @version 0.1
 * @since 06/23/2018
 */

public class SRTDriverTest {
	/** 
	 * Test main() as an overall Junit program test
	 * 
	 * @throws Exception 
	 */
	@Test
	public void mainTest() throws Exception {

		//test output directory and input youtube file
		String[] args = new String[] {
				"/home/mm/tmp",
				"https://www.youtube.com/watch?v=I8XXfgF9GSc"
		};

		//use reflection to call the static main method
		Class<?> clazz = SRTDriver.class;
		Class<?>[] argTypes = new Class<?>[] { String[].class };
		Method main = null;
		try {
			main = clazz.getDeclaredMethod("main", argTypes);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		String[] mainArgs = args;

		//invoke main class with commandline arguments
		main.invoke(null, (Object)mainArgs);
		assertTrue(true); //passes if returns from main method successfully. Does not test results.
	}
	
	@Test
	public void urlExistsTest() {
		String arg = 
				"https://www.youtube.com/watch?v=I8XXfgF9GSc";
		boolean exists = SRTDriver.URLexists(arg);
		assertTrue(exists);
	}

}
