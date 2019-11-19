package io.github.jhipster.consumer.web.rest;

import io.github.jhipster.consumer.domain.StringMessage;
import io.github.jhipster.consumer.service.StringMessageService;
import io.github.jhipster.consumer.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.consumer.domain.StringMessage}.
 */
@RestController
@RequestMapping("/api")
public class StringMessageResource {

    private final Logger log = LoggerFactory.getLogger(StringMessageResource.class);

    private final StringMessageService stringMessageService;

    public StringMessageResource(StringMessageService stringMessageService) {
        this.stringMessageService = stringMessageService;
    }

    /**
     * {@code GET  /string-messages} : get all the stringMessages.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stringMessages in body.
     */
    @GetMapping("/string-messages")
    public ResponseEntity<List<StringMessage>> getAllStringMessages(Pageable pageable) {
        log.debug("REST request to get a page of StringMessages");
        Page<StringMessage> page = stringMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /string-messages/:id} : get the "id" stringMessage.
     *
     * @param id the id of the stringMessage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stringMessage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/string-messages/{id}")
    public ResponseEntity<StringMessage> getStringMessage(@PathVariable Long id) {
        log.debug("REST request to get StringMessage : {}", id);
        Optional<StringMessage> stringMessage = stringMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stringMessage);
    }
}
