package com.hypercell.orange.simmanagement.repository;

import com.hypercell.orange.simmanagement.domain.ActiveAlert;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ActiveAlert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActiveAlertRepository extends JpaRepository<ActiveAlert, Long> {}
