package com.hypercell.orange.simmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "customer_id", nullable = false, unique = true)
    private UUID customerId;

    @Column(name = "contarct_id")
    private Long contarctId;

    @Column(name = "customer_parent")
    private String customerParent;

    @Column(name = "dummy_contract")
    private Long dummyContract;

    @Column(name = "customer_id_hight")
    private Long customerIdHight;

    @Column(name = "customer_code")
    private String customerCode;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "activeAlerts", "customer" }, allowSetters = true)
    private Set<Dial> dials = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<Bucket> buckets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public Customer customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public Customer customerId(UUID customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Long getContarctId() {
        return this.contarctId;
    }

    public Customer contarctId(Long contarctId) {
        this.setContarctId(contarctId);
        return this;
    }

    public void setContarctId(Long contarctId) {
        this.contarctId = contarctId;
    }

    public String getCustomerParent() {
        return this.customerParent;
    }

    public Customer customerParent(String customerParent) {
        this.setCustomerParent(customerParent);
        return this;
    }

    public void setCustomerParent(String customerParent) {
        this.customerParent = customerParent;
    }

    public Long getDummyContract() {
        return this.dummyContract;
    }

    public Customer dummyContract(Long dummyContract) {
        this.setDummyContract(dummyContract);
        return this;
    }

    public void setDummyContract(Long dummyContract) {
        this.dummyContract = dummyContract;
    }

    public Long getCustomerIdHight() {
        return this.customerIdHight;
    }

    public Customer customerIdHight(Long customerIdHight) {
        this.setCustomerIdHight(customerIdHight);
        return this;
    }

    public void setCustomerIdHight(Long customerIdHight) {
        this.customerIdHight = customerIdHight;
    }

    public String getCustomerCode() {
        return this.customerCode;
    }

    public Customer customerCode(String customerCode) {
        this.setCustomerCode(customerCode);
        return this;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Set<Dial> getDials() {
        return this.dials;
    }

    public void setDials(Set<Dial> dials) {
        if (this.dials != null) {
            this.dials.forEach(i -> i.setCustomer(null));
        }
        if (dials != null) {
            dials.forEach(i -> i.setCustomer(this));
        }
        this.dials = dials;
    }

    public Customer dials(Set<Dial> dials) {
        this.setDials(dials);
        return this;
    }

    public Customer addDial(Dial dial) {
        this.dials.add(dial);
        dial.setCustomer(this);
        return this;
    }

    public Customer removeDial(Dial dial) {
        this.dials.remove(dial);
        dial.setCustomer(null);
        return this;
    }

    public Set<Bucket> getBuckets() {
        return this.buckets;
    }

    public void setBuckets(Set<Bucket> buckets) {
        if (this.buckets != null) {
            this.buckets.forEach(i -> i.setCustomer(null));
        }
        if (buckets != null) {
            buckets.forEach(i -> i.setCustomer(this));
        }
        this.buckets = buckets;
    }

    public Customer buckets(Set<Bucket> buckets) {
        this.setBuckets(buckets);
        return this;
    }

    public Customer addBucket(Bucket bucket) {
        this.buckets.add(bucket);
        bucket.setCustomer(this);
        return this;
    }

    public Customer removeBucket(Bucket bucket) {
        this.buckets.remove(bucket);
        bucket.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", contarctId=" + getContarctId() +
            ", customerParent='" + getCustomerParent() + "'" +
            ", dummyContract=" + getDummyContract() +
            ", customerIdHight=" + getCustomerIdHight() +
            ", customerCode='" + getCustomerCode() + "'" +
            "}";
    }
}
