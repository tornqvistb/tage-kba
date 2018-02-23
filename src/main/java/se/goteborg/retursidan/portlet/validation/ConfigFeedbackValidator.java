package se.goteborg.retursidan.portlet.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.goteborg.retursidan.model.form.ConfigFeedback;


@Component
public class ConfigFeedbackValidator implements Validator {
	private Logger logger = Logger.getLogger(ConfigFeedbackValidator.class.getName());

	@Override
	public boolean supports(Class<?> clz) {
		return ConfigFeedback.class.isAssignableFrom(clz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ConfigFeedback config = (ConfigFeedback)obj;
		
		ValidationUtils.rejectIfEmpty(errors, "confirmationText", "config.confirmtext.empty");
		ValidationUtils.rejectIfEmpty(errors, "feedbackMailSubject", "config.mailsubject.empty");
		ValidationUtils.rejectIfEmpty(errors, "feedbackMailBody", "config.mailbody.empty");

		ValidationUtils.rejectIfEmpty(errors, "feedbackMailSender", "config.mailsenderaddress.empty");
		if (config.getFeedbackMailSender() != null && config.getFeedbackMailSender().length() > 0 && !EmailValidator.getInstance().isValid(config.getFeedbackMailSender())) {
			errors.rejectValue("feedbackMailSender", "config.mailaddress.badformat");
		}

		ValidationUtils.rejectIfEmpty(errors, "feedbackMailReceiver", "config.mailreveiveraddress.empty");
		if (config.getFeedbackMailReceiver() != null && config.getFeedbackMailReceiver().length() > 0 && !EmailValidator.getInstance().isValid(config.getFeedbackMailReceiver())) {
			errors.rejectValue("feedbackMailReceiver", "config.mailaddress.badformat");
		}

		if (errors.hasErrors()) {
			logger.log(Level.FINE, errors.toString());
		}
	}

}
