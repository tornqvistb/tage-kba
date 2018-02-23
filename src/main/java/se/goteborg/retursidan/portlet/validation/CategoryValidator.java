package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.entity.Category;

@Component
public class CategoryValidator implements Validator {
	private Logger logger = Logger.getLogger(CategoryValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clz) {
		return Category.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "category.title.missing");
	
		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
