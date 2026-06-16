package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "json:build/reports/cucumber/cucumber.json",
                "html:build/reports/cucumber/cucumber-html-report",
                "junit:build/reports/cucumber/junit-report.xml"
        },
        monochrome = true,
        tags = "not @ignore"
)
public class RunCucumberTest {
}
