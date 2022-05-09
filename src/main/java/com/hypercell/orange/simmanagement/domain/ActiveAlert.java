package com.hypercell.orange.simmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hypercell.orange.simmanagement.domain.enumeration.AlertPeriod;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ActiveAlert.
 */
@Entity
@Table(name = "active_alert")
public class ActiveAlert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "active_alert_id", nullable = false)
    private Long activeAlertId;

    @NotNull
    @Column(name = "active_alert_name", nullable = false)
    private String activeAlertName;

    @NotNull
    @Column(name = "active_alert_capacity", nullable = false)
    private Long activeAlertCapacity;

    @NotNull
    @Column(name = "active_alert_customer_id", nullable = false)
    private Long activeAlertCustomerId;

    @NotNull
    @Column(name = "active_alert_dial_number", nullable = false)
    private Long activeAlertDialNumber;

    @Column(name = "active_alert_sms_message")
    private String activeAlertSmsMessage;

    @Column(name = "active_alert_email_message")
    private String activeAlertEmailMessage;

    @Column(name = "active_alert_sms_message_active")
    private Boolean activeAlertSmsMessageActive;

    @Column(name = "active_alert_email_message_active")
    private Boolean activeAlertEmailMessageActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_alert_period")
    private AlertPeriod activeAlertPeriod;

    @ManyToOne
    @JsonIgnoreProperties(value = { "activeAlerts", "customer" }, allowSetters = true)
    private Dial dial;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ActiveAlert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActiveAlertId() {
        return this.activeAlertId;
    }

    public ActiveAlert activeAlertId(Long activeAlertId) {
        this.setActiveAlertId(activeAlertId);
        return this;
    }

    public void setActiveAlertId(Long activeAlertId) {
        this.activeAlertId = activeAlertId;
    }

    public String getActiveAlertName() {
        return this.activeAlertName;
    }

    public ActiveAlert activeAlertName(String activeAlertName) {
        this.setActiveAlertName(activeAlertName);
        return this;
    }

    public void setActiveAlertName(String activeAlertName) {
        this.activeAlertName = activeAlertName;
    }

    public Long getActiveAlertCapacity() {
        return this.activeAlertCapacity;
    }

    public ActiveAlert activeAlertCapacity(Long activeAlertCapacity) {
        this.setActiveAlertCapacity(activeAlertCapacity);
        return this;
    }

    public void setActiveAlertCapacity(Long activeAlertCapacity) {
        this.activeAlertCapacity = activeAlertCapacity;
    }

    public Long getActiveAlertCustomerId() {
        return this.activeAlertCustomerId;
    }

    public ActiveAlert activeAlertCustomerId(Long activeAlertCustomerId) {
        this.setActiveAlertCustomerId(activeAlertCustomerId);
        return this;
    }

    public void setActiveAlertCustomerId(Long activeAlertCustomerId) {
        this.activeAlertCustomerId = activeAlertCustomerId;
    }

    public Long getActiveAlertDialNumber() {
        return this.activeAlertDialNumber;
    }

    public ActiveAlert activeAlertDialNumber(Long activeAlertDialNumber) {
        this.setActiveAlertDialNumber(activeAlertDialNumber);
        return this;
    }

    public void setActiveAlertDialNumber(Long activeAlertDialNumber) {
        this.activeAlertDialNumber = activeAlertDialNumber;
    }

    public String getActiveAlertSmsMessage() {
        return this.activeAlertSmsMessage;
    }

    public ActiveAlert activeAlertSmsMessage(String activeAlertSmsMessage) {
        this.setActiveAlertSmsMessage(activeAlertSmsMessage);
        return this;
    }

    public void setActiveAlertSmsMessage(String activeAlertSmsMessage) {
        this.activeAlertSmsMessage = activeAlertSmsMessage;
    }

    public String getActiveAlertEmailMessage() {
        return this.activeAlertEmailMessage;
    }

    public ActiveAlert activeAlertEmailMessage(String activeAlertEmailMessage) {
        this.setActiveAlertEmailMessage(activeAlertEmailMessage);
        return this;
    }

    public void setActiveAlertEmailMessage(String activeAlertEmailMessage) {
        this.activeAlertEmailMessage = activeAlertEmailMessage;
    }

    public Boolean getActiveAlertSmsMessageActive() {
        return this.activeAlertSmsMessageActive;
    }

    public ActiveAlert activeAlertSmsMessageActive(Boolean activeAlertSmsMessageActive) {
        this.setActiveAlertSmsMessageActive(activeAlertSmsMessageActive);
        return this;
    }

    public void setActiveAlertSmsMessageActive(Boolean activeAlertSmsMessageActive) {
        this.activeAlertSmsMessageActive = activeAlertSmsMessageActive;
    }

    public Boolean getActiveAlertEmailMessageActive() {
        return this.activeAlertEmailMessageActive;
    }

    public ActiveAlert activeAlertEmailMessageActive(Boolean activeAlertEmailMessageActive) {
        this.setActiveAlertEmailMessageActive(activeAlertEmailMessageActive);
        return this;
    }

    public void setActiveAlertEmailMessageActive(Boolean activeAlertEmailMessageActive) {
        this.activeAlertEmailMessageActive = activeAlertEmailMessageActive;
    }

    public AlertPeriod getActiveAlertPeriod() {
        return this.activeAlertPeriod;
    }

    public ActiveAlert activeAlertPeriod(AlertPeriod activeAlertPeriod) {
        this.setActiveAlertPeriod(activeAlertPeriod);
        return this;
    }

    public void setActiveAlertPeriod(AlertPeriod activeAlertPeriod) {
        this.activeAlertPeriod = activeAlertPeriod;
    }

    public Dial getDial() {
        return this.dial;
    }

    public void setDial(Dial dial) {
        this.dial = dial;
    }

    public ActiveAlert dial(Dial dial) {
        this.setDial(dial);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActiveAlert)) {
            return false;
        }
        return id != null && id.equals(((ActiveAlert) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActiveAlert{" +
            "id=" + getId() +
            ", activeAlertId=" + getActiveAlertId() +
            ", activeAlertName='" + getActiveAlertName() + "'" +
            ", activeAlertCapacity=" + getActiveAlertCapacity() +
            ", activeAlertCustomerId=" + getActiveAlertCustomerId() +
            ", activeAlertDialNumber=" + getActiveAlertDialNumber() +
            ", activeAlertSmsMessage='" + getActiveAlertSmsMessage() + "'" +
            ", activeAlertEmailMessage='" + getActiveAlertEmailMessage() + "'" +
            ", activeAlertSmsMessageActive='" + getActiveAlertSmsMessageActive() + "'" +
            ", activeAlertEmailMessageActive='" + getActiveAlertEmailMessageActive() + "'" +
            ", activeAlertPeriod='" + getActiveAlertPeriod() + "'" +
            "}";
    }
}
