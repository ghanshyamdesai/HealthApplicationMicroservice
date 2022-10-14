package com.infosys.appointment.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.infosys.appointment.entities.Appointment;
import com.infosys.appointment.entities.Status;
import com.infosys.appointment.exception.GenericException;
import com.infosys.appointment.model.AppointmentResponse;
import com.infosys.appointment.model.CreateAppointmentRequest;
import com.infosys.appointment.model.NewAppointmentResponse;
import com.infosys.appointment.repo.AppointmentRepository;
import com.infosys.appointment.repo.StatusRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StatusRepository statusRepository;

    public NewAppointmentResponse create(final CreateAppointmentRequest request, String userEmail) {

        // Find the requested status - if it doesn't exist, we will not move forward.
        final Status status = findStatus(request.getStatus());
//    	Status status = new Status();
//    	status.setStatus(request.getStatus());
    	
        // TODO : Ensure all datetimes are in UTC
        final Appointment appointment = new Appointment(
        	userEmail,
            request.getDoctorFullName(),
            request.getHospitalName(),
            request.getScheduledDate(),
            request.getDurationInMinutes(),
            status
        );

        // Persist to the database
        
        appointmentRepository.save(appointment);
        NewAppointmentResponse newAppointmentResponse = new NewAppointmentResponse();
        newAppointmentResponse.setResponse("Appointment has been added successfully");
        return newAppointmentResponse;
    }

    public List<AppointmentResponse> list() {
        return appointmentRepository
            .findAll()
            .stream()
            .map(AppointmentService::from)
            .collect(Collectors.toList());
    }

    public AppointmentResponse find(final Long id) throws GenericException {
    	
    	Optional<Appointment> appointmentFromDB = appointmentRepository.findById(id);
    	
    	if(appointmentFromDB.isPresent()) {
    		return from(appointmentFromDB.get());
    	}
    	else {
    		throw new GenericException(HttpStatus.NOT_FOUND, "Appointment Does not Exists");
    	}
    }

    public AppointmentResponse update(final Long id, final String status) throws GenericException {
    	
    	Optional<Appointment> foundAppointment = appointmentRepository.findById(id);
    	
    	if(foundAppointment.isPresent()) {
    		Appointment appointment = foundAppointment.get();
    		appointment.setStatus(findStatus(status));
            return from(appointmentRepository.save(appointment));
    	}
    	else {
    		throw new GenericException(HttpStatus.NOT_FOUND, "Appointment Does not Exists");
    	}

    }

    public NewAppointmentResponse delete(final Long id) throws GenericException {
    	
    	Optional<Appointment> foundAppointment = appointmentRepository.findById(id);
    	
    	if(foundAppointment.isPresent()) {
    		Appointment appointment = foundAppointment.get();
    		appointmentRepository.delete(appointment);
            NewAppointmentResponse deleteResponse = new NewAppointmentResponse();
            deleteResponse.setResponse("Appointment Id "+ id + " has been deleted successfully");
            return deleteResponse;
    	}
    	else {
    		throw new GenericException(HttpStatus.NOT_FOUND, "Appointment Does not Exists");
    	}

    }

    public List<AppointmentResponse> search(final ZonedDateTime start, final ZonedDateTime end) {
        return appointmentRepository.findByScheduledDateBetween(start, end)
            .stream()
            .map(AppointmentService::from)
            .collect(Collectors.toList());
    }

    public Status findStatus(final String status) {
        return statusRepository
            .findOne(Example.of(new Status(status)))
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown status %s", status)));
    }

    private static AppointmentResponse from(final Appointment appointment) {
        return new AppointmentResponse(
            appointment.getAppointmentId(),
            appointment.getCreatedAt(),
            appointment.getScheduledDate(),
            appointment.getDurationInMinutes(),
            appointment.getDoctorFullName(),
            appointment.getStatus().getStatus(),
            appointment.getUserId(),
            appointment.getHospitalName()
        );
    }

}
