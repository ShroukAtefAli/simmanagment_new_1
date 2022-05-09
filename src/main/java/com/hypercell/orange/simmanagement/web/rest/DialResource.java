package com.hypercell.orange.simmanagement.web.rest;

import com.hypercell.orange.simmanagement.domain.Dial;
import com.hypercell.orange.simmanagement.repository.DialRepository;
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
 * REST controller for managing {@link com.hypercell.orange.simmanagement.domain.Dial}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DialResource {

    private final Logger log = LoggerFactory.getLogger(DialResource.class);

    private static final String ENTITY_NAME = "simManagementDial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DialRepository dialRepository;

    public DialResource(DialRepository dialRepository) {
        this.dialRepository = dialRepository;
    }

    /**
     * {@code POST  /dials} : Create a new dial.
     *
     * @param dial the dial to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dial, or with status {@code 400 (Bad Request)} if the dial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dials")
    public ResponseEntity<Dial> createDial(@Valid @RequestBody Dial dial) throws URISyntaxException {
        log.debug("REST request to save Dial : {}", dial);
        if (dial.getId() != null) {
            throw new BadRequestAlertException("A new dial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dial result = dialRepository.save(dial);
        return ResponseEntity
            .created(new URI("/api/dials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dials/:id} : Updates an existing dial.
     *
     * @param id the id of the dial to save.
     * @param dial the dial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dial,
     * or with status {@code 400 (Bad Request)} if the dial is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dials/{id}")
    public ResponseEntity<Dial> updateDial(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Dial dial)
        throws URISyntaxException {
        log.debug("REST request to update Dial : {}, {}", id, dial);
        if (dial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Dial result = dialRepository.save(dial);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dial.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dials/:id} : Partial updates given fields of an existing dial, field will ignore if it is null
     *
     * @param id the id of the dial to save.
     * @param dial the dial to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dial,
     * or with status {@code 400 (Bad Request)} if the dial is not valid,
     * or with status {@code 404 (Not Found)} if the dial is not found,
     * or with status {@code 500 (Internal Server Error)} if the dial couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Dial> partialUpdateDial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Dial dial
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dial partially : {}, {}", id, dial);
        if (dial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dial.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dial> result = dialRepository
            .findById(dial.getId())
            .map(existingDial -> {
                if (dial.getDialId() != null) {
                    existingDial.setDialId(dial.getDialId());
                }
                if (dial.getDial() != null) {
                    existingDial.setDial(dial.getDial());
                }
                if (dial.getActiveAlertId() != null) {
                    existingDial.setActiveAlertId(dial.getActiveAlertId());
                }
                if (dial.getDialConsumption() != null) {
                    existingDial.setDialConsumption(dial.getDialConsumption());
                }
                if (dial.getBucketId() != null) {
                    existingDial.setBucketId(dial.getBucketId());
                }
                if (dial.getBucketdate() != null) {
                    existingDial.setBucketdate(dial.getBucketdate());
                }
                if (dial.getContractStatus() != null) {
                    existingDial.setContractStatus(dial.getContractStatus());
                }
                if (dial.getContractDate() != null) {
                    existingDial.setContractDate(dial.getContractDate());
                }
                if (dial.getSimNum() != null) {
                    existingDial.setSimNum(dial.getSimNum());
                }
                if (dial.getVolStatusFlag() != null) {
                    existingDial.setVolStatusFlag(dial.getVolStatusFlag());
                }
                if (dial.getApnName() != null) {
                    existingDial.setApnName(dial.getApnName());
                }
                if (dial.getSoftDisconnect() != null) {
                    existingDial.setSoftDisconnect(dial.getSoftDisconnect());
                }
                if (dial.getBillCycle() != null) {
                    existingDial.setBillCycle(dial.getBillCycle());
                }
                if (dial.getm2mMonitoringService() != null) {
                    existingDial.setm2mMonitoringService(dial.getm2mMonitoringService());
                }

                return existingDial;
            })
            .map(dialRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dial.getId().toString())
        );
    }

    /**
     * {@code GET  /dials} : get all the dials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dials in body.
     */
    @GetMapping("/dials")
    public List<Dial> getAllDials() {
        log.debug("REST request to get all Dials");
        return dialRepository.findAll();
    }

    /**
     * {@code GET  /dials/:id} : get the "id" dial.
     *
     * @param id the id of the dial to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dial, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dials/{id}")
    public ResponseEntity<Dial> getDial(@PathVariable Long id) {
        log.debug("REST request to get Dial : {}", id);
        Optional<Dial> dial = dialRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dial);
    }

    /**
     * {@code DELETE  /dials/:id} : delete the "id" dial.
     *
     * @param id the id of the dial to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dials/{id}")
    public ResponseEntity<Void> deleteDial(@PathVariable Long id) {
        log.debug("REST request to delete Dial : {}", id);
        dialRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
