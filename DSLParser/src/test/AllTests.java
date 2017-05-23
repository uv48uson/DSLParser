package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Test_fileIO.class, Test_Parser.class, Test_Parser_Exception.class })
public class AllTests {

}
