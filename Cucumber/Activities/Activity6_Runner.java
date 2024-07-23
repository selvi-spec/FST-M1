package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/Features",
        glue = {"stepDefinitions"},
        tags = "@SimpleAlert",
        plugin = {"pretty"},
        //plugin = {"html: test-reports"},
        //plugin = {"json: test/reports/json-report.json"},
        monochrome = true
)

public class Activity6_Runner {

}

