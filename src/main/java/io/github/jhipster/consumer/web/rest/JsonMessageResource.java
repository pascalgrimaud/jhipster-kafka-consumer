package io.github.jhipster.consumer.web.rest;

import io.github.jhipster.consumer.domain.JsonMessage;
import io.github.jhipster.consumer.service.JsonMessageService;
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
 * REST controller for managing {@link io.github.jhipster.consumer.domain.JsonMessage}.
 */
@RestController
@RequestMapping("/api")
public class JsonMessageResource {

    private final Logger log = LoggerFactory.getLogger(JsonMessageResource.class);

    private final JsonMessageService jsonMessageService;

    public JsonMessageResource(JsonMessageService jsonMessageService) {
        this.jsonMessageService = jsonMessageService;
    }

    /**
     * {@code GET  /json-messages} : get all the jsonMessages.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jsonMessages in body.
     */
    @GetMapping("/json-messages")
    public ResponseEntity<List<JsonMessage>> getAllJsonMessages(Pageable pageable) {
        log.debug("REST request to get a page of JsonMessages");
        Page<JsonMessage> page = jsonMessageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /json-messages/:id} : get the "id" jsonMessage.
     *
     * @param id the id of the jsonMessage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jsonMessage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/json-messages/{id}")
    public ResponseEntity<JsonMessage> getJsonMessage(@PathVariable Long id) {
        log.debug("REST request to get JsonMessage : {}", id);
        Optional<JsonMessage> jsonMessage = jsonMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jsonMessage);
    }
}
