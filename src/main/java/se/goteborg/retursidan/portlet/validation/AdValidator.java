package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.entity.Advertisement;

@Component
public class AdValidator implements Validator {
	private Logger logger = Logger.getLogger(AdValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clz) {
		return Advertisement.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Advertisement ad = (Advertisement)obj;

		if(ad.getTopCategory() == null || ad.getTopCategory().getId() == -1) {
			errors.rejectValue("topCategory", "topCategory.missing");
		}
		
		if(ad.getCategory() == null || ad.getCategory().getId() == -1) {
			errors.rejectValue("category", "category.missing");
		}

		if(ad.getUnit() == null || ad.getUnit().getId() == -1) {
			errors.rejectValue("unit.id", "unit.missing");
		}

		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.missing");
		
		PersonValidator personValidator = new PersonValidator();
		ValidationUtils.invokeValidator(personValidator, ad.getContact(), errors);

		ValidationUtils.rejectIfEmpty(errors, "pickupAddress", "pickupAddress.missing");
		
		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
