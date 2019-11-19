package io.github.jhipster.consumer.repository;
import io.github.jhipster.consumer.domain.StringMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StringMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StringMessageRepository extends JpaRepository<StringMessage, Long> {

}
