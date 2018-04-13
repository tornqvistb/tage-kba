package se.goteborg.retursidan.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import se.goteborg.retursidan.model.form.MailComposition;

@Service
public class MailService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	private final static String IMAGE_MARKUP = "<p><img src=\"cid:ad-image\"></img></p>";
	private final static String LOGO_MARKUP = "<p><img src=\"cid:logo-image\"></img></p>";
	
	@Autowired
	JavaMailSender mailAccount;
	
	private void sendMultiPartMail(MailComposition composition, MimeMultipart multipart) {
		
		MimeMessage message = mailAccount.createMimeMessage();
		try {
			System.out.println("Sending multipart mail, start");
			logger.log(Level.FINEST, "Sending multipart mail");
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(composition.getSenderAdress());
			helper.setTo(composition.getReveiverAdress());
			String subject = composition.getMailHeader();
			subject = subject.replaceAll("\\{title\\}", composition.getTitle());
			message.setSubject(subject, "UTF-8");
			message.setContent(multipart);						
			if (StringUtils.isNotEmpty(composition.getReplyToAdress())) {
				helper.setReplyTo(composition.getReplyToAdress());
			}
			mailAccount.send(message);
			logger.log(Level.FINEST, "Mail sent to " + composition.getReveiverAdress());
			System.out.println("Mail sent to " + composition.getReveiverAdress());
		} catch (MessagingException e) {
			logger.log(Level.WARNING, "Could not send mail", e);
			System.out.println("Could not send mail");
		}
	}

	public void composeAndSendMail(MailComposition composition) {
		System.out.println("composeAndSendMail, start");
		MimeMultipart multipart = new MimeMultipart("related");
		BodyPart messageBodyPart = new MimeBodyPart();
		
		String mailBody = composition.getConfigMailBody();
		mailBody = mailBody.replaceAll("\\{title\\}", composition.getTitle());
		mailBody = mailBody.replaceAll("\\{bookerName\\}", composition.getBookerName());
		mailBody = mailBody.replaceAll("\\{bookerPhone\\}", composition.getBookerPhone());
		mailBody = mailBody.replaceAll("\\{bookerMail\\}", composition.getBookerMail());
		mailBody = mailBody.replaceAll("\\{advertiserName\\}", composition.getAdvertiserName());
		mailBody = mailBody.replaceAll("\\{advertiserPhone\\}", composition.getAdvertiserPhone());
		mailBody = mailBody.replaceAll("\\{advertiserMail\\}", composition.getAdvertiserMail());
		mailBody = mailBody.replaceAll("\\{respondentName\\}", composition.getRespondentName());
		mailBody = mailBody.replaceAll("\\{respondentPhone\\}", composition.getRespondentPhone());
		mailBody = mailBody.replaceAll("\\{respondentMail\\}", composition.getRespondentMail());
		mailBody = mailBody.replaceAll("\\{requesterName\\}", composition.getRequesterName());
		mailBody = mailBody.replaceAll("\\{requesterPhone\\}", composition.getRequesterPhone());
		mailBody = mailBody.replaceAll("\\{requesterMail\\}", composition.getRequesterMail());
		mailBody = mailBody.replaceAll("\\{link\\}", "<a href=\"" + composition.getLink() + "\" >Länk</a>");
		mailBody = mailBody.replaceAll("\\{rules\\}", "Regler för TAGE kan du läsa om " + "<a href=\"" + composition.getRulesUrl() + "\" >här</a>");
		mailBody = mailBody.replaceAll("\\{publishedDays\\}", String.valueOf(composition.getPublishedDays()));
		
		if (composition.getPhoto() != null) {
			mailBody = mailBody.replaceAll("\\{image\\}", IMAGE_MARKUP);
		} else {
			mailBody = mailBody.replaceAll("\\{image\\}", "");
		}
		mailBody = mailBody.replaceAll("\\{logotype\\}", LOGO_MARKUP);
		mailBody = mailBody.replaceAll("\\[", "<");
		mailBody = mailBody.replaceAll("\\]", ">");
		
		try {
			messageBodyPart.setContent(mailBody, "text/html; charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			if (composition.getPhoto() != null) {
				Blob image = composition.getPhoto().getThumbnail();
				messageBodyPart = new MimeBodyPart();			
				DataSource bds = new ByteArrayDataSource(image.getBytes(1L, (int) image.length()), composition.getPhoto().getMimeType());				
				messageBodyPart.setDataHandler(new DataHandler(bds));
			    messageBodyPart.setHeader("Content-ID", "<ad-image>");
			    multipart.addBodyPart(messageBodyPart);
			}
			// Logotype
			if (StringUtils.isNotEmpty(composition.getLogoUrl())) {
				try {
					messageBodyPart = new MimeBodyPart();			
					messageBodyPart.setDataHandler(new DataHandler(getLogoDataSource(composition.getLogoUrl())));
					messageBodyPart.setHeader("Content-ID", "<logo-image>");
					multipart.addBodyPart(messageBodyPart);
				} catch (Exception e) {
					logger.log(Level.WARNING, "Could not attach logo to mail, continue anyway.", e);
				}			
			}
			sendMultiPartMail(composition, multipart);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.WARNING, "Could not send mail", e);
			System.out.println("composeAndSendMail, Could not send mail: " + e.getMessage());
		}

	}

	private DataSource getLogoDataSource(String logotypeUrl) throws IOException {
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		URL logoURL = new URL(logotypeUrl);
		is = logoURL.openStream();
		byte[] byteChunk = new byte[4096];
		int n;
		while ((n = is.read(byteChunk)) > 0) {
			baos.write(byteChunk, 0, n);
		}
		DataSource bds = new ByteArrayDataSource(baos.toByteArray(), "image/png");
		return bds;
	}	
}
