package org.github.sgoeschl.gatling.blueprint.extensions;

import org.junit.Test;

import static org.github.sgoeschl.gatling.blueprint.extensions.SimulationCoordinates.from;
import static org.junit.Assert.assertEquals;

public class SimulationCoordinatesTest {

    private static final String SIMULATION_CLASS_NAME = "application.tenant.scenario.Test";

    private final SimulationCoordinates simulationCoordinates = new SimulationCoordinates("application", "tenant", "local", "scenario");

    @Test
    public void shouldCreateSimulationFromSimulationClassName() {
        assertEquals(simulationCoordinates, from(SIMULATION_CLASS_NAME));
    }

    @Test
    public void shouldCreateSimulationFromSimulationClassNameAndProperties() {
        assertEquals(simulationCoordinates, from(SIMULATION_CLASS_NAME, System.getProperties()));
    }

}
