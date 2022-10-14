package com.infosys.appointment.entities;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long appointmentId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "name_of_doctor")
	private String doctorFullName;

	@Column(name = "hospital_name")
	private String hospitalName;

	@Column(name = "created_at", updatable = false)
	private ZonedDateTime createdAt;

	@Column(name = "appointment_date")
	private ZonedDateTime scheduledDate;

	@Column(name = "appointment_duration")
	private Integer durationInMinutes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;

	public Appointment() {
	}

	public Appointment(String userId, String doctorFullName, String hospitalName, ZonedDateTime scheduledDate,
			Integer durationInMinutes, Status status) {
		super();
		this.userId = userId;
		this.doctorFullName = doctorFullName;
		this.hospitalName = hospitalName;
		this.scheduledDate = scheduledDate;
		this.durationInMinutes = durationInMinutes;
		this.status = status;
		this.createdAt = Instant.now().atZone(ZoneOffset.UTC);
	}

}
