package org.example.runners;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@ExtendWith(SerenityJUnit5Extension.class)
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.example.stepsdefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =  "net.serenitybdd.cucumber.core.plugin.SerenityReporter,pretty")
//Para CD y CI pruebas en paralelo
//@ConfigurationParameter(key = "serenity.batch.strategy", value = "DIVIDE_BY_TEST_COUNT")
//@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value =  "net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel ,pretty")
@ConfigurationParameter(key = "cucumber.snippet-type", value = "camelcase")
public class LoginRunner {
}