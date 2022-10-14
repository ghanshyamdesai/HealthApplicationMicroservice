package com.infosys.appointment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.appointment.entities.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
