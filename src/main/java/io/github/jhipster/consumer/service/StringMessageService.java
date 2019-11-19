package io.github.jhipster.consumer.service;

import io.github.jhipster.consumer.domain.StringMessage;
import io.github.jhipster.consumer.repository.StringMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StringMessage}.
 */
@Service
@Transactional
public class StringMessageService {

    private final Logger log = LoggerFactory.getLogger(StringMessageService.class);

    private final StringMessageRepository stringMessageRepository;

    public StringMessageService(StringMessageRepository stringMessageRepository) {
        this.stringMessageRepository = stringMessageRepository;
    }

    /**
     * Save a stringMessage.
     *
     * @param stringMessage the entity to save.
     * @return the persisted entity.
     */
    public StringMessage save(StringMessage stringMessage) {
        log.debug("Request to save StringMessage : {}", stringMessage);
        return stringMessageRepository.save(stringMessage);
    }

    /**
     * Get all the stringMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StringMessage> findAll(Pageable pageable) {
        log.debug("Request to get all StringMessages");
        return stringMessageRepository.findAll(pageable);
    }


    /**
     * Get one stringMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StringMessage> findOne(Long id) {
        log.debug("Request to get StringMessage : {}", id);
        return stringMessageRepository.findById(id);
    }

    /**
     * Delete the stringMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StringMessage : {}", id);
        stringMessageRepository.deleteById(id);
    }
}
