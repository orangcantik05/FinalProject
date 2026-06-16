package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api_user.feature",
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "json:build/reports/cucumber/api-cucumber.json",
                "html:build/reports/cucumber/api-report",
                "junit:build/reports/cucumber/api-junit-report.xml"
        },
        monochrome = true,
        tags = "@api"
)
public class RunApiTest {
}
