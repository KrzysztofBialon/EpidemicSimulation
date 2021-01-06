package com.app.epidemicsimulation.controller;

import com.app.epidemicsimulation.model.SimulationDay;
import com.app.epidemicsimulation.model.SimulationSetUp;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
class MainControllerTest
{
    @Autowired
    WebTestClient webTestClient;

    @Test
    void createSimulation()
    {
        SimulationSetUp testSetUp = new SimulationSetUp("Test Simulation", 100, 10, 0.5, 0.1, 2, 1, 10);
        testSetUp.setId("1234");

        webTestClient
                .post()
                .uri("/simulation/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(testSetUp))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(SimulationDay.class);
    }

    @Test
    void getAllSimulationSetUps()
    {
        webTestClient.get()
                .uri("/simulation/search/set_ups/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(JSONObject.class);
    }

    @Test
    void getSimulationSetUpByName()
    {
        webTestClient.get()
                .uri("/simulation/search/set_ups/simulationTest")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(JSONObject.class);
    }

    @Test
    void getSimulationSetUpById()
    {
        webTestClient.get()
                .uri("/simulation/search/set_ups/5ff37e68bd8adf4246a5e3da")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(JSONObject.class);
    }

    @Test
    void getAllSimulationRecords()
    {
        webTestClient.get()
                .uri("/simulation/search/record/all")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(JSONObject.class);
    }

    @Test
    void getSimulationRecordByOwnerId()
    {
        webTestClient.get()
                .uri("/simulation/search/set_ups/5ff37e68bd8adf4246a5e3da")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(JSONObject.class);
    }

    @Test
    void modifySimulation()
    {
        SimulationSetUp testSetUp = new SimulationSetUp("Test Simulation", 100, 10, 0.5, 0.1, 2, 1, 10);

        webTestClient.put()
                .uri("/simulation/simulationSetUps/5ff37e7bbd8adf4246a5e3dc")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(testSetUp))
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void deleteSimulation()
    {
        webTestClient.delete()
                .uri("/simulation/simulationSetUps/5ff37e68bd8adf4246a5e3da")
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}