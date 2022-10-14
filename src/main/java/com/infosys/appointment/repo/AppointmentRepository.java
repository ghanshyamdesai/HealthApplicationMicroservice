package com.infosys.appointment.repo;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infosys.appointment.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByScheduledDateBetween(final ZonedDateTime start, final ZonedDateTime scheduledDate);

}