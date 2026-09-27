package com.harp.demo.screenplay.stepsdefinitions.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HooksSteps {

    private static final Logger LOG = LoggerFactory.getLogger(HooksSteps.class);

    @Before
    public void prepareActor() {
        LOG.info("Preparing Screenplay cast for mobile scenario");
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Andres");
    }

    @After
    public void tearDown() {
        LOG.info("Tearing down Screenplay scenario");
        OnStage.drawTheCurtain();
    }
}
