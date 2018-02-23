package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.entity.Request;

@Component
public class RequestValidator implements Validator {
	private Logger logger = Logger.getLogger(RequestValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clz) {
		return Request.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Request request = (Request)obj;

		if(request.getTopCategory() == null || request.getTopCategory().getId() == -1) {
			errors.rejectValue("topCategory", "topCategory.missing");
		}
		
		if(request.getCategory() == null || request.getCategory().getId() == -1) {
			errors.rejectValue("category", "category.missing");
		}
		
		if(request.getUnit() == null || request.getUnit().getId() == -1) {
			errors.rejectValue("unit.id", "unit.missing");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.missing");
		
		PersonValidator personValidator = new PersonValidator();
		ValidationUtils.invokeValidator(personValidator, request.getContact(), errors);
		
		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
