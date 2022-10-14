package com.infosys.appointment.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.appointment.exception.GenericException;
import com.infosys.appointment.model.AppointmentResponse;
import com.infosys.appointment.model.CreateAppointmentRequest;
import com.infosys.appointment.model.NewAppointmentResponse;
import com.infosys.appointment.service.AppointmentService;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
	private Environment env;

    @PostMapping
    public ResponseEntity<NewAppointmentResponse> create(@Valid @RequestBody final CreateAppointmentRequest request, @RequestHeader("authorization") String token) throws GenericException {
        
    	String jwt = token.replace("Bearer", "");
    	String userEmail = getEmailFromJwt(jwt);
    	
    	NewAppointmentResponse response = appointmentService.create(request,userEmail);
        if(response != null) {
        	return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String getEmailFromJwt(String jwt) throws GenericException {
		String subject = null;
		try{
			subject = Jwts.parser()
					.setSigningKey(env.getProperty("token.secret"))
					.parseClaimsJws(jwt)
					.getBody()
					.getSubject();
		}catch(Exception e) {
			throw new GenericException(HttpStatus.NOT_FOUND, "User associated with token does not Exists");
		}
		
		return subject;
		
	}

	@GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> find(@PathVariable final Long id) throws GenericException {
        
    	AppointmentResponse response = appointmentService.find(id);
    	if(response != null) {
        	return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<AppointmentResponse> update(@PathVariable final Long id, @PathVariable final String status) throws GenericException {
        
    	AppointmentResponse response = appointmentService.update(id, status);
    	if(response != null) {
        	return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewAppointmentResponse> delete(@PathVariable final Long id) throws GenericException {
        
    	NewAppointmentResponse response = appointmentService.delete(id);
    	if(response != null) {
        	return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<AppointmentResponse> search(
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) final ZonedDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) final ZonedDateTime end
    ) {
        if (Optional.ofNullable(start).isPresent() && Optional.ofNullable(end).isPresent()) {
            return appointmentService.search(start, end);
        } else {
            return appointmentService.list();
        }
    }
}
