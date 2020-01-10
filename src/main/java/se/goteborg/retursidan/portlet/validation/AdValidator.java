package se.goteborg.retursidan.portlet.validation;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.util.DateHelper;

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

		ValidationUtils.rejectIfEmpty(errors, "count", "count.missing");
		if (ad.getCount() != null && ad.getCount() <= 0) {
			errors.rejectValue("count", "count.mustbegreaterthanzero");
		}
		
		if(ad.getExpireType() == null) {
			errors.rejectValue("expireType", "expireType.missing");
		} else {
			if(Advertisement.ExpireType.FIXED_DATE.equals(ad.getExpireType())) {
				if (StringUtils.isEmpty(ad.getExpireDateString())) {
					errors.rejectValue("expireDate", "expireDate.missing");
				} else {
					// Check that Date is valid and in future
					try {
						Date expireDate = DateHelper.getDateFromString(ad.getExpireDateString());
						Date now = DateHelper.getCurrentDate();
						if (expireDate.before(now)) {
							errors.rejectValue("expireDate", "expireDate.mustbeinfuture");
						}
					} catch (ParseException e) {
						errors.rejectValue("expireDate", "expireDate.badformat");
					} 
				}
			}
		}

		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
