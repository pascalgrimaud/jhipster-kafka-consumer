package io.github.jhipster.consumer.web.rest;

import io.github.jhipster.consumer.ConsumerApp;
import io.github.jhipster.consumer.domain.StringMessage;
import io.github.jhipster.consumer.repository.StringMessageRepository;
import io.github.jhipster.consumer.service.StringMessageService;
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
 * Integration tests for the {@link StringMessageResource} REST controller.
 */
@SpringBootTest(classes = ConsumerApp.class)
public class StringMessageResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_RECEIVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StringMessageRepository stringMessageRepository;

    @Autowired
    private StringMessageService stringMessageService;

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

    private MockMvc restStringMessageMockMvc;

    private StringMessage stringMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StringMessageResource stringMessageResource = new StringMessageResource(stringMessageService);
        this.restStringMessageMockMvc = MockMvcBuilders.standaloneSetup(stringMessageResource)
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
    public static StringMessage createEntity(EntityManager em) {
        StringMessage stringMessage = new StringMessage()
            .message(DEFAULT_MESSAGE)
            .receivedAt(DEFAULT_RECEIVED_AT);
        return stringMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StringMessage createUpdatedEntity(EntityManager em) {
        StringMessage stringMessage = new StringMessage()
            .message(UPDATED_MESSAGE)
            .receivedAt(UPDATED_RECEIVED_AT);
        return stringMessage;
    }

    @BeforeEach
    public void initTest() {
        stringMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllStringMessages() throws Exception {
        // Initialize the database
        stringMessageRepository.saveAndFlush(stringMessage);

        // Get all the stringMessageList
        restStringMessageMockMvc.perform(get("/api/string-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stringMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].receivedAt").value(hasItem(DEFAULT_RECEIVED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getStringMessage() throws Exception {
        // Initialize the database
        stringMessageRepository.saveAndFlush(stringMessage);

        // Get the stringMessage
        restStringMessageMockMvc.perform(get("/api/string-messages/{id}", stringMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stringMessage.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.receivedAt").value(DEFAULT_RECEIVED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStringMessage() throws Exception {
        // Get the stringMessage
        restStringMessageMockMvc.perform(get("/api/string-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StringMessage.class);
        StringMessage stringMessage1 = new StringMessage();
        stringMessage1.setId(1L);
        StringMessage stringMessage2 = new StringMessage();
        stringMessage2.setId(stringMessage1.getId());
        assertThat(stringMessage1).isEqualTo(stringMessage2);
        stringMessage2.setId(2L);
        assertThat(stringMessage1).isNotEqualTo(stringMessage2);
        stringMessage1.setId(null);
        assertThat(stringMessage1).isNotEqualTo(stringMessage2);
    }
}
