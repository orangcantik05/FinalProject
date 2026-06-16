package runners;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenerateCucumberReport {
    public static void main(String[] args) {
        File reportOutputDirectory = new File("build/reports/cucumber/masterthought");
        List<String> jsonFiles = new ArrayList<>();

        // Add all JSON report files
        File reportsDir = new File("build/reports/cucumber");
        if (reportsDir.exists()) {
            for (File f : reportsDir.listFiles()) {
                if (f.getName().endsWith(".json")) {
                    jsonFiles.add(f.getAbsolutePath());
                    System.out.println("Found JSON report: " + f.getAbsolutePath());
                }
            }
        }

        if (jsonFiles.isEmpty()) {
            System.out.println("No JSON report files found. Using default path.");
            jsonFiles.add("build/reports/cucumber/cucumber.json");
        }

        String projectName = "FinalProject - Automation Test Report";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber("1.0");
        configuration.addClassifications("Platform", "Linux");
        configuration.addClassifications("Browser", "Chrome (Headless)");
        configuration.addClassifications("Branch", "main");
        configuration.addClassifications("API", "DummyAPI.io");
        configuration.addClassifications("Web App", "The Internet Herokuapp & SauceDemo");

        try {
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
            System.out.println("Cucumber report generated at: " + reportOutputDirectory.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
