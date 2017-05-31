package me.satyen.tests.junit;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(value = Suite.class)
//@SuiteClasses(Test2.class)
public class SampleJunit{
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test public void sampleTest() throws IOException{
		assertEquals("Not equal",1,1);
		assertTrue("Satyen" == "Satyen");
		exception.expect(IOException.class);
		//exception.expectMessage("Dude, this is invalid!");
		throw(new IOException());
	}
}