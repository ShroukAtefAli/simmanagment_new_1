package com.hypercell.orange.simmanagement.domain;

import com.hypercell.orange.simmanagement.domain.enumeration.AlertPeriod;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Alert.
 */
@Entity
@Table(name = "alert")
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "alert_id", nullable = false, unique = true)
    private UUID alertId;

    @NotNull
    @Column(name = "alert_name", nullable = false)
    private String alertName;

    @NotNull
    @Column(name = "alert_capacity", nullable = false)
    private Long alertCapacity;

    @Column(name = "alert_sms_message")
    private String alertSmsMessage;

    @Column(name = "alert_email_message")
    private String alertEmailMessage;

    @Column(name = "alert_sms_message_active")
    private Boolean alertSmsMessageActive;

    @Column(name = "alert_email_message_active")
    private Boolean alertEmailMessageActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_period")
    private AlertPeriod alertPeriod;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Alert id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAlertId() {
        return this.alertId;
    }

    public Alert alertId(UUID alertId) {
        this.setAlertId(alertId);
        return this;
    }

    public void setAlertId(UUID alertId) {
        this.alertId = alertId;
    }

    public String getAlertName() {
        return this.alertName;
    }

    public Alert alertName(String alertName) {
        this.setAlertName(alertName);
        return this;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public Long getAlertCapacity() {
        return this.alertCapacity;
    }

    public Alert alertCapacity(Long alertCapacity) {
        this.setAlertCapacity(alertCapacity);
        return this;
    }

    public void setAlertCapacity(Long alertCapacity) {
        this.alertCapacity = alertCapacity;
    }

    public String getAlertSmsMessage() {
        return this.alertSmsMessage;
    }

    public Alert alertSmsMessage(String alertSmsMessage) {
        this.setAlertSmsMessage(alertSmsMessage);
        return this;
    }

    public void setAlertSmsMessage(String alertSmsMessage) {
        this.alertSmsMessage = alertSmsMessage;
    }

    public String getAlertEmailMessage() {
        return this.alertEmailMessage;
    }

    public Alert alertEmailMessage(String alertEmailMessage) {
        this.setAlertEmailMessage(alertEmailMessage);
        return this;
    }

    public void setAlertEmailMessage(String alertEmailMessage) {
        this.alertEmailMessage = alertEmailMessage;
    }

    public Boolean getAlertSmsMessageActive() {
        return this.alertSmsMessageActive;
    }

    public Alert alertSmsMessageActive(Boolean alertSmsMessageActive) {
        this.setAlertSmsMessageActive(alertSmsMessageActive);
        return this;
    }

    public void setAlertSmsMessageActive(Boolean alertSmsMessageActive) {
        this.alertSmsMessageActive = alertSmsMessageActive;
    }

    public Boolean getAlertEmailMessageActive() {
        return this.alertEmailMessageActive;
    }

    public Alert alertEmailMessageActive(Boolean alertEmailMessageActive) {
        this.setAlertEmailMessageActive(alertEmailMessageActive);
        return this;
    }

    public void setAlertEmailMessageActive(Boolean alertEmailMessageActive) {
        this.alertEmailMessageActive = alertEmailMessageActive;
    }

    public AlertPeriod getAlertPeriod() {
        return this.alertPeriod;
    }

    public Alert alertPeriod(AlertPeriod alertPeriod) {
        this.setAlertPeriod(alertPeriod);
        return this;
    }

    public void setAlertPeriod(AlertPeriod alertPeriod) {
        this.alertPeriod = alertPeriod;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alert)) {
            return false;
        }
        return id != null && id.equals(((Alert) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Alert{" +
            "id=" + getId() +
            ", alertId='" + getAlertId() + "'" +
            ", alertName='" + getAlertName() + "'" +
            ", alertCapacity=" + getAlertCapacity() +
            ", alertSmsMessage='" + getAlertSmsMessage() + "'" +
            ", alertEmailMessage='" + getAlertEmailMessage() + "'" +
            ", alertSmsMessageActive='" + getAlertSmsMessageActive() + "'" +
            ", alertEmailMessageActive='" + getAlertEmailMessageActive() + "'" +
            ", alertPeriod='" + getAlertPeriod() + "'" +
            "}";
    }
}
