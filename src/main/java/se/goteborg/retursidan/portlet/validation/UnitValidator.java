package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.entity.Unit;

@Component
public class UnitValidator implements Validator {
	private Logger logger = Logger.getLogger(UnitValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clz) {
		return Unit.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "unit.name.missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "administrationId", "unit.administrationId.missing");
	
		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
