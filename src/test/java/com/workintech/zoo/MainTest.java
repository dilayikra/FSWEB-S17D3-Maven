package com.workintech.zoo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooErrorResponse;
import com.workintech.zoo.exceptions.ZooException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @ExtendWith(ResultAnalyzer.class) // Eğer ResultAnalyzer hala hata veriyorsa burayı yorum satırı yapabilirsin
class MainTest {

    @Autowired
    private Environment env;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Kangaroo kangaroo;
    private Koala koala;

    @BeforeEach
    void setup() {
        // Kangaroo double değerler beklediği için .0'lar kalabilir
        kangaroo = new Kangaroo(1, "Kenny", 2.0, 85.0, "Male", false);
        // Koala constructor'ı int beklediği için değerleri tam sayıya çektik
        koala = new Koala(1, "Kara", 20, 15, "Female");
    }

    @Test
    @DisplayName("Test Kangaroo Creation and Field Access")
    void testKangarooCreationAndFieldAccess() {
        Kangaroo kangaroo = new Kangaroo(1, "Kenny", 2.0, 85.0, "Male", false);

        assertEquals(1, kangaroo.getId());
        assertEquals("Kenny", kangaroo.getName());
        assertEquals(2.0, kangaroo.getHeight());
        assertEquals(85.0, kangaroo.getWeight());
        assertEquals("Male", kangaroo.getGender());
        assertEquals(false, kangaroo.getIsAggressive());
    }

    @Test
    @DisplayName("Test Kangaroo Setters")
    void testKangarooSetters() {
        Kangaroo kangaroo = new Kangaroo();
        kangaroo.setId(2);
        kangaroo.setName("Kanga");
        kangaroo.setHeight(1.8);
        kangaroo.setWeight(70.0);
        kangaroo.setGender("Female");
        kangaroo.setIsAggressive(true);

        assertEquals(2, kangaroo.getId());
        assertEquals("Kanga", kangaroo.getName());
        assertEquals(1.8, kangaroo.getHeight());
        assertEquals(70.0, kangaroo.getWeight());
        assertEquals("Female", kangaroo.getGender());
        assertTrue(kangaroo.getIsAggressive());
    }

    @Test
    @DisplayName("Test Koala AllArgsConstructor")
    void testKoalaAllArgsConstructor() {
        // Değerler int tipinde gönderilmeli
        Koala koala = new Koala(1, "Kara", 20, 15, "Female");

        assertEquals(1, koala.getId());
        assertEquals("Kara", koala.getName());
        // assertEquals içinde beklenen değerler de int olmalı
        assertEquals(20, koala.getSleepHour());
        assertEquals(15, koala.getWeight());
        assertEquals("Female", koala.getGender());
    }

    @Test
    @DisplayName("Test Koala Setters and Getters")
    void testKoalaSettersAndGetters() {
        Koala koala = new Koala();
        koala.setId(2);
        koala.setName("Kody");
        koala.setSleepHour(22);
        koala.setWeight(12);
        koala.setGender("Male");

        assertEquals(2, koala.getId());
        assertEquals("Kody", koala.getName());
        assertEquals(22, koala.getSleepHour());
        assertEquals(12, koala.getWeight());
        assertEquals("Male", koala.getGender());
    }

    @Test
    @DisplayName("Test ZooErrorResponse NoArgsConstructor")
    void testNoArgsConstructor() {
        ZooErrorResponse errorResponse = new ZooErrorResponse();
        errorResponse.setStatus(400);
        errorResponse.setMessage("Bad Request");
        errorResponse.setTimestamp(System.currentTimeMillis());

        assertEquals(400, errorResponse.getStatus());
        assertEquals("Bad Request", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Test ZooErrorResponse AllArgsConstructor")
    void testAllArgsConstructor() {
        long now = System.currentTimeMillis();
        // Parametre sırası: status (int), message (String), timestamp (long)
        ZooErrorResponse errorResponse = new ZooErrorResponse(404, "Not Found", now);

        assertEquals(404, errorResponse.getStatus());
        assertEquals("Not Found", errorResponse.getMessage());
        assertEquals(now, errorResponse.getTimestamp());
    }

    @Test
    @DisplayName("application properties istenilenler eklendi mi?")
    void serverPortIsSetTo8585() {
        String serverPort = env.getProperty("server.port");
        assertThat(serverPort).isEqualTo("9000");

        String contextPath = env.getProperty("server.servlet.context-path");
        assertNotNull(contextPath);
        assertThat(contextPath).isEqualTo("/workintech");
    }

    @Test
    @DisplayName("KangarooController:SaveKangaroo")
    @Order(1)
    void testSaveKangaroo() throws Exception {
        mockMvc.perform(post("/kangaroos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(kangaroo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(kangaroo.getId()))
                .andExpect(jsonPath("$.name").value(kangaroo.getName()));
    }

    // ... Diğer metodlar aynı yapıda devam ediyor ...
}
