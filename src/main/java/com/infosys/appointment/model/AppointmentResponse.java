package com.infosys.appointment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AppointmentResponse {

    public final Long id;
    public final ZonedDateTime createdAt;
    public final ZonedDateTime scheduledDate;
    public final Integer durationInMinutes;
    public final String doctorFullName;
    public final String status;
    public final String email;
    public final String hospitalName;

    @JsonCreator
    public AppointmentResponse(
        @JsonProperty final Long id,
        @JsonProperty final ZonedDateTime createdAt,
        @JsonProperty final ZonedDateTime scheduledDate,
        @JsonProperty final Integer durationInMinutes,
        @JsonProperty final String doctorFullName,
        @JsonProperty final String status,
        @JsonProperty final String email,
        @JsonProperty final String hospitalName
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.scheduledDate = scheduledDate;
        this.durationInMinutes = durationInMinutes;
        this.doctorFullName = doctorFullName;
        this.status = status;
        this.email = email;
        this.hospitalName = hospitalName;
    }

    

    @Override
	public String toString() {
		return "AppointmentResponse [id=" + id + ", createdAt=" + createdAt + ", scheduledDate=" + scheduledDate
				+ ", durationInMinutes=" + durationInMinutes + ", doctorFullName=" + doctorFullName + ", status="
				+ status + ", email=" + email + ", hospitalName=" + hospitalName + "]";
	}



	@Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AppointmentResponse)) {
            return false;
        }

        final AppointmentResponse that = (AppointmentResponse) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(createdAt, that.createdAt)
            .append(scheduledDate, that.scheduledDate)
            .append(durationInMinutes, that.durationInMinutes)
            .append(doctorFullName, that.doctorFullName)
            .append(status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(createdAt)
            .append(scheduledDate)
            .append(durationInMinutes)
            .append(doctorFullName)
            .append(status)
            .toHashCode();
    }
}
