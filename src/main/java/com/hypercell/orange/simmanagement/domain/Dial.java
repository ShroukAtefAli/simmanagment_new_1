package com.hypercell.orange.simmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Dial.
 */
@Entity
@Table(name = "dial")
public class Dial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "dial_id", nullable = false, unique = true)
    private UUID dialId;

    @NotNull
    @Column(name = "dial", nullable = false)
    private String dial;

    @Column(name = "active_alert_id")
    private Long activeAlertId;

    @Column(name = "dial_consumption")
    private Long dialConsumption;

    @Column(name = "bucket_id")
    private Long bucketId;

    @Column(name = "bucketdate")
    private LocalDate bucketdate;

    @Column(name = "contract_status")
    private String contractStatus;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "sim_num")
    private String simNum;

    @Column(name = "vol_status_flag")
    private String volStatusFlag;

    @Column(name = "apn_name")
    private String apnName;

    @Column(name = "soft_disconnect")
    private String softDisconnect;

    @Column(name = "bill_cycle")
    private Integer billCycle;

    @Column(name = "m_2_m_monitoring_service")
    private String m2mMonitoringService;

    @OneToMany(mappedBy = "dial")
    @JsonIgnoreProperties(value = { "dial" }, allowSetters = true)
    private Set<ActiveAlert> activeAlerts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "dials", "buckets" }, allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getDialId() {
        return this.dialId;
    }

    public Dial dialId(UUID dialId) {
        this.setDialId(dialId);
        return this;
    }

    public void setDialId(UUID dialId) {
        this.dialId = dialId;
    }

    public String getDial() {
        return this.dial;
    }

    public Dial dial(String dial) {
        this.setDial(dial);
        return this;
    }

    public void setDial(String dial) {
        this.dial = dial;
    }

    public Long getActiveAlertId() {
        return this.activeAlertId;
    }

    public Dial activeAlertId(Long activeAlertId) {
        this.setActiveAlertId(activeAlertId);
        return this;
    }

    public void setActiveAlertId(Long activeAlertId) {
        this.activeAlertId = activeAlertId;
    }

    public Long getDialConsumption() {
        return this.dialConsumption;
    }

    public Dial dialConsumption(Long dialConsumption) {
        this.setDialConsumption(dialConsumption);
        return this;
    }

    public void setDialConsumption(Long dialConsumption) {
        this.dialConsumption = dialConsumption;
    }

    public Long getBucketId() {
        return this.bucketId;
    }

    public Dial bucketId(Long bucketId) {
        this.setBucketId(bucketId);
        return this;
    }

    public void setBucketId(Long bucketId) {
        this.bucketId = bucketId;
    }

    public LocalDate getBucketdate() {
        return this.bucketdate;
    }

    public Dial bucketdate(LocalDate bucketdate) {
        this.setBucketdate(bucketdate);
        return this;
    }

    public void setBucketdate(LocalDate bucketdate) {
        this.bucketdate = bucketdate;
    }

    public String getContractStatus() {
        return this.contractStatus;
    }

    public Dial contractStatus(String contractStatus) {
        this.setContractStatus(contractStatus);
        return this;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDate getContractDate() {
        return this.contractDate;
    }

    public Dial contractDate(LocalDate contractDate) {
        this.setContractDate(contractDate);
        return this;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public String getSimNum() {
        return this.simNum;
    }

    public Dial simNum(String simNum) {
        this.setSimNum(simNum);
        return this;
    }

    public void setSimNum(String simNum) {
        this.simNum = simNum;
    }

    public String getVolStatusFlag() {
        return this.volStatusFlag;
    }

    public Dial volStatusFlag(String volStatusFlag) {
        this.setVolStatusFlag(volStatusFlag);
        return this;
    }

    public void setVolStatusFlag(String volStatusFlag) {
        this.volStatusFlag = volStatusFlag;
    }

    public String getApnName() {
        return this.apnName;
    }

    public Dial apnName(String apnName) {
        this.setApnName(apnName);
        return this;
    }

    public void setApnName(String apnName) {
        this.apnName = apnName;
    }

    public String getSoftDisconnect() {
        return this.softDisconnect;
    }

    public Dial softDisconnect(String softDisconnect) {
        this.setSoftDisconnect(softDisconnect);
        return this;
    }

    public void setSoftDisconnect(String softDisconnect) {
        this.softDisconnect = softDisconnect;
    }

    public Integer getBillCycle() {
        return this.billCycle;
    }

    public Dial billCycle(Integer billCycle) {
        this.setBillCycle(billCycle);
        return this;
    }

    public void setBillCycle(Integer billCycle) {
        this.billCycle = billCycle;
    }

    public String getm2mMonitoringService() {
        return this.m2mMonitoringService;
    }

    public Dial m2mMonitoringService(String m2mMonitoringService) {
        this.setm2mMonitoringService(m2mMonitoringService);
        return this;
    }

    public void setm2mMonitoringService(String m2mMonitoringService) {
        this.m2mMonitoringService = m2mMonitoringService;
    }

    public Set<ActiveAlert> getActiveAlerts() {
        return this.activeAlerts;
    }

    public void setActiveAlerts(Set<ActiveAlert> activeAlerts) {
        if (this.activeAlerts != null) {
            this.activeAlerts.forEach(i -> i.setDial(null));
        }
        if (activeAlerts != null) {
            activeAlerts.forEach(i -> i.setDial(this));
        }
        this.activeAlerts = activeAlerts;
    }

    public Dial activeAlerts(Set<ActiveAlert> activeAlerts) {
        this.setActiveAlerts(activeAlerts);
        return this;
    }

    public Dial addActiveAlert(ActiveAlert activeAlert) {
        this.activeAlerts.add(activeAlert);
        activeAlert.setDial(this);
        return this;
    }

    public Dial removeActiveAlert(ActiveAlert activeAlert) {
        this.activeAlerts.remove(activeAlert);
        activeAlert.setDial(null);
        return this;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Dial customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dial)) {
            return false;
        }
        return id != null && id.equals(((Dial) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dial{" +
            "id=" + getId() +
            ", dialId='" + getDialId() + "'" +
            ", dial='" + getDial() + "'" +
            ", activeAlertId=" + getActiveAlertId() +
            ", dialConsumption=" + getDialConsumption() +
            ", bucketId=" + getBucketId() +
            ", bucketdate='" + getBucketdate() + "'" +
            ", contractStatus='" + getContractStatus() + "'" +
            ", contractDate='" + getContractDate() + "'" +
            ", simNum='" + getSimNum() + "'" +
            ", volStatusFlag='" + getVolStatusFlag() + "'" +
            ", apnName='" + getApnName() + "'" +
            ", softDisconnect='" + getSoftDisconnect() + "'" +
            ", billCycle=" + getBillCycle() +
            ", m2mMonitoringService='" + getm2mMonitoringService() + "'" +
            "}";
    }
}
