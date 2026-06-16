package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/web_login.feature",
                    "src/test/resources/features/e2e_checkout.feature"},
        glue = {"steps", "hooks"},
        plugin = {
                "pretty",
                "json:build/reports/cucumber/web-cucumber.json",
                "html:build/reports/cucumber/web-report",
                "junit:build/reports/cucumber/web-junit-report.xml"
        },
        monochrome = true,
        tags = "@web or @e2e"
)
public class RunWebTest {
}
