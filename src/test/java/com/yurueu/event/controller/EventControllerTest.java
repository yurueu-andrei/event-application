package com.yurueu.event.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = {EventControllerTest.Initializer.class})
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testPostgres")
            .withUsername("postgres")
            .withPassword("postgres");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of(
                            "database.url=" + postgreSQLContainer.getJdbcUrl(),
                            "database.user=" + postgreSQLContainer.getUsername(),
                            "database.password=" + postgreSQLContainer.getPassword(),
                            "spring.liquibase.contexts=data, test")
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @Transactional
    public void findAllTest_shouldReturnOnlyFiveEvents() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/events?limit=5"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[5]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();
        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @Transactional
    public void findAllTest_shouldReturnOnlyFiveEventsFromTheSixthId() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/events?limit=5&offset=5"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[1].id").value(7))
                .andExpect(jsonPath("$[2].id").value(8))
                .andExpect(jsonPath("$[3].id").value(9))
                .andExpect(jsonPath("$[4].id").value(10))
                .andExpect(jsonPath("$[5]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();
        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @Transactional
    public void findAllTest_shouldReturnEventsWhichDateIsBetween2020And2022() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/events?limit=5&dateFrom=2020-01-01T00:00&dateTo=2022-01-01T00:00"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].eventDate").value("2021-01-10T18:02:00"))
                .andExpect(jsonPath("$[1].eventDate").value("2020-09-17T17:53:00"))
                .andExpect(jsonPath("$[2].eventDate").value("2020-08-21T17:12:00"))
                .andExpect(jsonPath("$[3].eventDate").value("2021-11-27T15:25:00"))
                .andExpect(jsonPath("$[4].eventDate").value("2020-02-22T05:48:00"))
                .andExpect(jsonPath("$[5]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();
        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @Transactional
    public void findAllTest_shouldReturnEventsWhichDateIsBetween2020And2022WithTopicValentineDay2() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/events?dateFrom=2020-01-01T00:00&dateTo=2022-01-01T00:00&topic=Valentine Day2"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(11))
                .andExpect(jsonPath("$[0].topic").value("Valentine Day2"))
                .andExpect(jsonPath("$[0].description").value("The day when lovers express their affection with greetings and gifts"))
                .andExpect(jsonPath("$[0].organizer").value("organizer №99"))
                .andExpect(jsonPath("$[0].eventDate").value("2021-11-27T15:25:00"))
                .andExpect(jsonPath("$[0].place").value("place543"))
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();
        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @Transactional
    public void findAllTest_shouldReturnEventsWhichDateIsBetween2015And2020WithTopicSensation7AndOrganizer500() throws Exception {
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/events?dateFrom=2015-01-01T00:00&dateTo=2020-01-01T00:00&topic=Sensation №7&organizer=organizer №500"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].topic").value("Sensation №7"))
                .andExpect(jsonPath("$[0].description").value("An annual electronic music festival"))
                .andExpect(jsonPath("$[0].organizer").value("organizer №500"))
                .andExpect(jsonPath("$[0].eventDate").value("2018-11-13T14:38:00"))
                .andExpect(jsonPath("$[0].place").value("place11"))
                .andExpect(jsonPath("$[1]").doesNotExist())
                .andExpect(status().isOk())
                .andReturn();
        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}
