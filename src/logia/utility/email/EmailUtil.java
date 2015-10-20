package logia.utility.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * The Class EmailUtil.
 * 
 * @author Paul Mai
 */
public class EmailUtil {

	/** The _email properties path. */
	private static String  _emailPropertiesPath = "email.properties";

	/** The password. */
	private static String  _password;

	/** The session. */
	private static Session _session;

	/** The username. */
	private static String  _username;

	/**
	 * Sets the properties path.
	 *
	 * @param propertiesPath the new properties path
	 */
	public static void setPropertiesPath(String propertiesPath) {
		EmailUtil._emailPropertiesPath = propertiesPath;
	}

	/**
	 * Send email.
	 *
	 * @param recipients the recipients
	 * @param nameRecipients the name recipients
	 * @param subject the subject
	 * @param content the content
	 * @param attachment the attachment file
	 * @param receipientType the receipient type
	 * @return true, if successful
	 * @throws AddressException the address exception
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws MessagingException the messaging exception
	 */
	public void sendEmail(String recipients, String nameRecipients, String subject, String content, File attachment, RecipientType receipientType)
	        throws AddressException, FileNotFoundException, IOException, MessagingException {
		List<File> attachments = new ArrayList<File>(1);
		attachments.add(attachment);
		this.sendEmail(recipients, nameRecipients, subject, content, attachments, receipientType);
	}

	/**
	 * Send email.
	 *
	 * @param recipients the recipients
	 * @param nameRecipients the name recipients
	 * @param subject the subject
	 * @param content the content
	 * @param attachments the list of attachment files
	 * @param receipientType the receipient type
	 * @return true, if successful
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	public void sendEmail(String recipients, String nameRecipients, String subject, String content, List<File> attachments,
	        RecipientType receipientType) throws FileNotFoundException, IOException, AddressException, MessagingException {
		if (EmailUtil._session == null || EmailUtil._username == null || EmailUtil._password == null) {
			this.initialized();
		}
		// Create a default MimeMessage object.
		MimeMessage message = new MimeMessage(EmailUtil._session);

		message.setFrom(new InternetAddress(EmailUtil._username));
		message.setRecipients(receipientType, InternetAddress.parse(recipients));
		message.setSubject(subject, "UTF-8");

		Multipart multipart = new MimeMultipart();

		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html; charset=UTF-8");
		multipart.addBodyPart(messageBodyPart);

		for (File file : attachments) {
			this.attachFile(multipart, file);
		}

		message.setContent(multipart);

		Transport.send(message);
	}

	/**
	 * Send email.
	 *
	 * @param recipients the recipients
	 * @param nameRecipients the name recipients
	 * @param subject the subject
	 * @param content the content
	 * @param receipientType the receipient type (TO, CC, BCC)
	 * @return true, if successful
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	public void sendEmail(String recipients, String nameRecipients, String subject, String content, RecipientType receipientType)
	        throws FileNotFoundException, IOException, AddressException, MessagingException {
		if (EmailUtil._session == null || EmailUtil._username == null || EmailUtil._password == null) {
			this.initialized();
		}
		MimeMessage message = new MimeMessage(EmailUtil._session);

		message.setFrom(new InternetAddress(EmailUtil._username));
		message.setRecipients(receipientType, InternetAddress.parse(recipients));
		message.setSubject(subject, "UTF-8");
		message.setContent(content, "text/html; charset=UTF-8");

		Transport.send(message);
	}

	/**
	 * Attach file.
	 *
	 * @param multipart the multipart
	 * @param file the file
	 * @throws MessagingException the messaging exception
	 */
	private void attachFile(Multipart multipart, File file) throws MessagingException {
		BodyPart attachPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		attachPart.setDataHandler(new DataHandler(source));
		attachPart.setFileName(file.getName());
		multipart.addBodyPart(attachPart);
	}

	/**
	 * Initialized mail session.
	 *
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void initialized() throws FileNotFoundException, IOException {
		Properties props = new Properties();
		String propFile = EmailUtil.class.getClassLoader().getResource(EmailUtil._emailPropertiesPath).getPath();
		props.load(new FileInputStream(propFile));
		EmailUtil._username = props.containsKey("email.username") ? props.getProperty("email.username") : "n/a";
		EmailUtil._password = props.containsKey("email.password") ? props.getProperty("email.password") : "n/a";

		EmailUtil._session = Session.getInstance(props, new javax.mail.Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EmailUtil._username, EmailUtil._password);
			}
		});
	}

}
