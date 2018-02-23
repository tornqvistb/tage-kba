package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.entity.Person;

@Component
public class PersonValidator implements Validator {
	private Logger logger = Logger.getLogger(PersonValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clz) {
		return Person.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Person person = (Person)obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.name", "contact.name.missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contact.email", "contact.email.missing");
		
		if (person.getEmail() != null && person.getEmail().length() > 0 && !EmailValidator.getInstance().isValid(person.getEmail())) {
			errors.rejectValue("contact.email", "contact.email.badformat");
		}
		
		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
