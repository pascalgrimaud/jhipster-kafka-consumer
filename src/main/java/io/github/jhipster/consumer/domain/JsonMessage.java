package io.github.jhipster.consumer.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A JsonMessage.
 */
@Entity
@Table(name = "json_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JsonMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "field_1")
    private String field1;

    @Column(name = "field_2")
    private String field2;

    @Column(name = "number")
    private Integer number;

    @Column(name = "received_at")
    private Instant receivedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField1() {
        return field1;
    }

    public JsonMessage field1(String field1) {
        this.field1 = field1;
        return this;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public JsonMessage field2(String field2) {
        this.field2 = field2;
        return this;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public Integer getNumber() {
        return number;
    }

    public JsonMessage number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public JsonMessage receivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
        return this;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonMessage)) {
            return false;
        }
        return id != null && id.equals(((JsonMessage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JsonMessage{" +
            "id=" + getId() +
            ", field1='" + getField1() + "'" +
            ", field2='" + getField2() + "'" +
            ", number=" + getNumber() +
            ", receivedAt='" + getReceivedAt() + "'" +
            "}";
    }
}
