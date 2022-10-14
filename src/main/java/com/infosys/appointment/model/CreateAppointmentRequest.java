package com.infosys.appointment.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateAppointmentRequest {
	
	@NotEmpty(message = "Hospital Name should not be null.")
    public final String hospitalName;

    @NotNull
    @Future
    @DateTimeFormat(iso = ISO.DATE_TIME)
    public final ZonedDateTime scheduledDate;

    @NotNull
    @Positive
    public final Integer durationInMinutes;

    @NotBlank
    public final String doctorFullName;

    @Pattern(regexp = "Available|Booked")
    public final String status;


    @JsonCreator
    public CreateAppointmentRequest(
    	@JsonProperty final String hospitalName,
        @JsonProperty final ZonedDateTime scheduledDate,
        @JsonProperty final Integer durationInMinutes,
        @JsonProperty final String doctorFullName,
        @JsonProperty final String status,
        @JsonProperty final BigDecimal price
    ) {
    	this.hospitalName = hospitalName;
        this.scheduledDate = scheduledDate;
        this.durationInMinutes = durationInMinutes;
        this.doctorFullName = doctorFullName;
        this.status = status;
    }
}