package com.hypercell.orange.simmanagement.web.rest;

import com.hypercell.orange.simmanagement.domain.ActiveAlert;
import com.hypercell.orange.simmanagement.repository.ActiveAlertRepository;
import com.hypercell.orange.simmanagement.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hypercell.orange.simmanagement.domain.ActiveAlert}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ActiveAlertResource {

    private final Logger log = LoggerFactory.getLogger(ActiveAlertResource.class);

    private static final String ENTITY_NAME = "simManagementActiveAlert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActiveAlertRepository activeAlertRepository;

    public ActiveAlertResource(ActiveAlertRepository activeAlertRepository) {
        this.activeAlertRepository = activeAlertRepository;
    }

    /**
     * {@code POST  /active-alerts} : Create a new activeAlert.
     *
     * @param activeAlert the activeAlert to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activeAlert, or with status {@code 400 (Bad Request)} if the activeAlert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/active-alerts")
    public ResponseEntity<ActiveAlert> createActiveAlert(@Valid @RequestBody ActiveAlert activeAlert) throws URISyntaxException {
        log.debug("REST request to save ActiveAlert : {}", activeAlert);
        if (activeAlert.getId() != null) {
            throw new BadRequestAlertException("A new activeAlert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActiveAlert result = activeAlertRepository.save(activeAlert);
        return ResponseEntity
            .created(new URI("/api/active-alerts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /active-alerts/:id} : Updates an existing activeAlert.
     *
     * @param id the id of the activeAlert to save.
     * @param activeAlert the activeAlert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeAlert,
     * or with status {@code 400 (Bad Request)} if the activeAlert is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activeAlert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/active-alerts/{id}")
    public ResponseEntity<ActiveAlert> updateActiveAlert(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActiveAlert activeAlert
    ) throws URISyntaxException {
        log.debug("REST request to update ActiveAlert : {}, {}", id, activeAlert);
        if (activeAlert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activeAlert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activeAlertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActiveAlert result = activeAlertRepository.save(activeAlert);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeAlert.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /active-alerts/:id} : Partial updates given fields of an existing activeAlert, field will ignore if it is null
     *
     * @param id the id of the activeAlert to save.
     * @param activeAlert the activeAlert to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activeAlert,
     * or with status {@code 400 (Bad Request)} if the activeAlert is not valid,
     * or with status {@code 404 (Not Found)} if the activeAlert is not found,
     * or with status {@code 500 (Internal Server Error)} if the activeAlert couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/active-alerts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActiveAlert> partialUpdateActiveAlert(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActiveAlert activeAlert
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActiveAlert partially : {}, {}", id, activeAlert);
        if (activeAlert.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activeAlert.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activeAlertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActiveAlert> result = activeAlertRepository
            .findById(activeAlert.getId())
            .map(existingActiveAlert -> {
                if (activeAlert.getActiveAlertId() != null) {
                    existingActiveAlert.setActiveAlertId(activeAlert.getActiveAlertId());
                }
                if (activeAlert.getActiveAlertName() != null) {
                    existingActiveAlert.setActiveAlertName(activeAlert.getActiveAlertName());
                }
                if (activeAlert.getActiveAlertCapacity() != null) {
                    existingActiveAlert.setActiveAlertCapacity(activeAlert.getActiveAlertCapacity());
                }
                if (activeAlert.getActiveAlertCustomerId() != null) {
                    existingActiveAlert.setActiveAlertCustomerId(activeAlert.getActiveAlertCustomerId());
                }
                if (activeAlert.getActiveAlertDialNumber() != null) {
                    existingActiveAlert.setActiveAlertDialNumber(activeAlert.getActiveAlertDialNumber());
                }
                if (activeAlert.getActiveAlertSmsMessage() != null) {
                    existingActiveAlert.setActiveAlertSmsMessage(activeAlert.getActiveAlertSmsMessage());
                }
                if (activeAlert.getActiveAlertEmailMessage() != null) {
                    existingActiveAlert.setActiveAlertEmailMessage(activeAlert.getActiveAlertEmailMessage());
                }
                if (activeAlert.getActiveAlertSmsMessageActive() != null) {
                    existingActiveAlert.setActiveAlertSmsMessageActive(activeAlert.getActiveAlertSmsMessageActive());
                }
                if (activeAlert.getActiveAlertEmailMessageActive() != null) {
                    existingActiveAlert.setActiveAlertEmailMessageActive(activeAlert.getActiveAlertEmailMessageActive());
                }
                if (activeAlert.getActiveAlertPeriod() != null) {
                    existingActiveAlert.setActiveAlertPeriod(activeAlert.getActiveAlertPeriod());
                }

                return existingActiveAlert;
            })
            .map(activeAlertRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activeAlert.getId().toString())
        );
    }

    /**
     * {@code GET  /active-alerts} : get all the activeAlerts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activeAlerts in body.
     */
    @GetMapping("/active-alerts")
    public List<ActiveAlert> getAllActiveAlerts() {
        log.debug("REST request to get all ActiveAlerts");
        return activeAlertRepository.findAll();
    }

    /**
     * {@code GET  /active-alerts/:id} : get the "id" activeAlert.
     *
     * @param id the id of the activeAlert to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activeAlert, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/active-alerts/{id}")
    public ResponseEntity<ActiveAlert> getActiveAlert(@PathVariable Long id) {
        log.debug("REST request to get ActiveAlert : {}", id);
        Optional<ActiveAlert> activeAlert = activeAlertRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activeAlert);
    }

    /**
     * {@code DELETE  /active-alerts/:id} : delete the "id" activeAlert.
     *
     * @param id the id of the activeAlert to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/active-alerts/{id}")
    public ResponseEntity<Void> deleteActiveAlert(@PathVariable Long id) {
        log.debug("REST request to delete ActiveAlert : {}", id);
        activeAlertRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
