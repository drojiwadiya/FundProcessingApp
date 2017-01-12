package all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import service.AppTest;
import util.DataExtractorUtilTest;

@RunWith(Suite.class)
@SuiteClasses({
	AppTest.class,
	DataExtractorUtilTest.class
})
public class TestSuite {

}
