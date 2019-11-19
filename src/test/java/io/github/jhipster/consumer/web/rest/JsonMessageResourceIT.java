package io.github.jhipster.consumer.web.rest;

import io.github.jhipster.consumer.ConsumerApp;
import io.github.jhipster.consumer.domain.JsonMessage;
import io.github.jhipster.consumer.repository.JsonMessageRepository;
import io.github.jhipster.consumer.service.JsonMessageService;
import io.github.jhipster.consumer.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.consumer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JsonMessageResource} REST controller.
 */
@SpringBootTest(classes = ConsumerApp.class)
public class JsonMessageResourceIT {

    private static final String DEFAULT_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Instant DEFAULT_RECEIVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private JsonMessageRepository jsonMessageRepository;

    @Autowired
    private JsonMessageService jsonMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restJsonMessageMockMvc;

    private JsonMessage jsonMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JsonMessageResource jsonMessageResource = new JsonMessageResource(jsonMessageService);
        this.restJsonMessageMockMvc = MockMvcBuilders.standaloneSetup(jsonMessageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JsonMessage createEntity(EntityManager em) {
        JsonMessage jsonMessage = new JsonMessage()
            .field1(DEFAULT_FIELD_1)
            .field2(DEFAULT_FIELD_2)
            .number(DEFAULT_NUMBER)
            .receivedAt(DEFAULT_RECEIVED_AT);
        return jsonMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JsonMessage createUpdatedEntity(EntityManager em) {
        JsonMessage jsonMessage = new JsonMessage()
            .field1(UPDATED_FIELD_1)
            .field2(UPDATED_FIELD_2)
            .number(UPDATED_NUMBER)
            .receivedAt(UPDATED_RECEIVED_AT);
        return jsonMessage;
    }

    @BeforeEach
    public void initTest() {
        jsonMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllJsonMessages() throws Exception {
        // Initialize the database
        jsonMessageRepository.saveAndFlush(jsonMessage);

        // Get all the jsonMessageList
        restJsonMessageMockMvc.perform(get("/api/json-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jsonMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].field1").value(hasItem(DEFAULT_FIELD_1)))
            .andExpect(jsonPath("$.[*].field2").value(hasItem(DEFAULT_FIELD_2)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].receivedAt").value(hasItem(DEFAULT_RECEIVED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getJsonMessage() throws Exception {
        // Initialize the database
        jsonMessageRepository.saveAndFlush(jsonMessage);

        // Get the jsonMessage
        restJsonMessageMockMvc.perform(get("/api/json-messages/{id}", jsonMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jsonMessage.getId().intValue()))
            .andExpect(jsonPath("$.field1").value(DEFAULT_FIELD_1))
            .andExpect(jsonPath("$.field2").value(DEFAULT_FIELD_2))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.receivedAt").value(DEFAULT_RECEIVED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJsonMessage() throws Exception {
        // Get the jsonMessage
        restJsonMessageMockMvc.perform(get("/api/json-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JsonMessage.class);
        JsonMessage jsonMessage1 = new JsonMessage();
        jsonMessage1.setId(1L);
        JsonMessage jsonMessage2 = new JsonMessage();
        jsonMessage2.setId(jsonMessage1.getId());
        assertThat(jsonMessage1).isEqualTo(jsonMessage2);
        jsonMessage2.setId(2L);
        assertThat(jsonMessage1).isNotEqualTo(jsonMessage2);
        jsonMessage1.setId(null);
        assertThat(jsonMessage1).isNotEqualTo(jsonMessage2);
    }
}
