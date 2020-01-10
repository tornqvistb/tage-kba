package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.form.Booking;

@Component
public class BookingValidator implements Validator {
	private Logger logger = Logger.getLogger(BookingValidator.class.getName());

	@Autowired
	PersonValidator personValidator;
	
	@Override
	public boolean supports(Class<?> clz) {
		return Booking.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Booking booking = (Booking)obj;

		if (!booking.isTransportationConfirmed()) {
			errors.rejectValue("transportationConfirmed", "transportationConfirmed.mandatory");
		}

		ValidationUtils.invokeValidator(personValidator, booking.getContact(), errors);
		
		// Check quantity
		if (booking.getBookedQuantity() == null || booking.getBookedQuantity() <= 0) {
			errors.rejectValue("bookedQuantity", "bookedQuantity.mandatory");
		} else if (booking.getBookedQuantity() > booking.getAdQuantity()) {
			errors.rejectValue("bookedQuantity", "bookedQuantity.toobig");
		}
		
		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
