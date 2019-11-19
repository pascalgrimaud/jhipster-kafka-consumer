package io.github.jhipster.consumer.service;

import io.github.jhipster.consumer.domain.JsonMessage;
import io.github.jhipster.consumer.repository.JsonMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JsonMessage}.
 */
@Service
@Transactional
public class JsonMessageService {

    private final Logger log = LoggerFactory.getLogger(JsonMessageService.class);

    private final JsonMessageRepository jsonMessageRepository;

    public JsonMessageService(JsonMessageRepository jsonMessageRepository) {
        this.jsonMessageRepository = jsonMessageRepository;
    }

    /**
     * Save a jsonMessage.
     *
     * @param jsonMessage the entity to save.
     * @return the persisted entity.
     */
    public JsonMessage save(JsonMessage jsonMessage) {
        log.debug("Request to save JsonMessage : {}", jsonMessage);
        return jsonMessageRepository.save(jsonMessage);
    }

    /**
     * Get all the jsonMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JsonMessage> findAll(Pageable pageable) {
        log.debug("Request to get all JsonMessages");
        return jsonMessageRepository.findAll(pageable);
    }


    /**
     * Get one jsonMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JsonMessage> findOne(Long id) {
        log.debug("Request to get JsonMessage : {}", id);
        return jsonMessageRepository.findById(id);
    }

    /**
     * Delete the jsonMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JsonMessage : {}", id);
        jsonMessageRepository.deleteById(id);
    }
}
