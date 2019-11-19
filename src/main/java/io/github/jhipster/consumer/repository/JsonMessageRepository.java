package io.github.jhipster.consumer.repository;
import io.github.jhipster.consumer.domain.JsonMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JsonMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JsonMessageRepository extends JpaRepository<JsonMessage, Long> {

}
