package com.harp.demo.screenplay.runners;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Cucumber + Serenity Screenplay runner (mobile e2e).
 * Native reports (always generated when this suite runs):
 * <ul>
 *   <li>Serenity HTML — {@code target/site/serenity/index.html} (via aggregate)</li>
 *   <li>Cucumber HTML — {@code target/cucumber-reports/cucumber.html}</li>
 *   <li>Cucumber JSON — {@code target/cucumber-reports/cucumber.json}</li>
 *   <li>Cucumber JUnit XML — {@code target/cucumber-reports/cucumber.xml}</li>
 * </ul>
 */
@Suite
@IncludeEngines("cucumber")
@ExtendWith(SerenityJUnit5Extension.class)
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.harp.demo.screenplay.stepsdefinitions")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel,"
                + "pretty,"
                + "html:target/cucumber-reports/cucumber.html,"
                + "json:target/cucumber-reports/cucumber.json,"
                + "junit:target/cucumber-reports/cucumber.xml"
)
@ConfigurationParameter(key = "cucumber.snippet-type", value = "camelcase")
public class LoginRunner {
}
