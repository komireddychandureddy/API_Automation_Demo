package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;

import controllers.BaseActions;

/**
 * @author Chandu
 *
 */
public class SendMail extends BaseActions{
	
	
	protected Properties PROP = new Properties();
	public String USERNAME =  null;
	public String PASSWORD = null;
	public String EMAILTO = null;
	public String EMAILTOCC = null;
	public String STARTTLS = null;
	public String HOST =null;
	public String PORT =null;
	
	public String SOCKETFACTORYCLASS = null;
	public String FALLBACK = null;
	public String PATH = null;
	public String MODULENAME = null;
	public int INDEXOFCOMMA = 0;
	public String USERFULLNAME = null;
	public String EMAIL_REGEX = "[a-z0-9\\_\\-\\.]+@[a-z0-9\\_\\-\\.]+\\.[a-z]+";
	public String REPORT_PATH="\\ExecutionReports\\ExecutionReports";
	public String DIR_PATH="user.dir";
	public String BLANK_VARIABLE="";
	protected Properties PROPS=System.getProperties();
	
	
	
	

	/**
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public  void sendEmailToClient() throws IOException, MessagingException {
		
		String mailPropertiesFile = USERDIR + ConfigReader.getValue("emailconfig");
		PROP.load(new FileInputStream(mailPropertiesFile));

		final String subject = PROP.getProperty("subject");
		
		
		USERNAME =  PROP.getProperty("USERNAME");
		PASSWORD = PROP.getProperty("PASSWORD");
		EMAILTO = PROP.getProperty("EMAILTO");
		EMAILTOCC = PROP.getProperty("EMAILTOCC");
		STARTTLS = PROP.getProperty("starttls");
		HOST = PROP.getProperty("HOST");
		PORT =PROP.getProperty("PORT");
		SOCKETFACTORYCLASS = PROP.getProperty("socketFactoryClass");
		FALLBACK = PROP.getProperty("fallback");

		PROPS.put("mail.smtp.user", USERNAME);
		PROPS.put("mail.smtp.HOST", HOST);
		PROPS.put("mail.smtp.auth", "true");

		if (!"".equals(PORT)) {
			PROPS.put("mail.smtp.port", PORT);
			PROPS.put("mail.smtp.socketFactory.port", PORT);
		}

		if (!"".equals(STARTTLS))
			PROPS.put("mail.smtp.starttls.enable", STARTTLS);

		if (!"".equals(SOCKETFACTORYCLASS))
			PROPS.put("mail.smtp.socketFactory.class", SOCKETFACTORYCLASS);

		if (!"".equals(FALLBACK))
			PROPS.put("mail.smtp.socketFactory.fallback", FALLBACK);

		Session session = Session.getDefaultInstance(PROPS, null);
		session.setDebug(false);


			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(USERNAME, PROP.getProperty("userFullName")));
			msg.setSubject(subject);

			if (!"".equals(EMAILTOCC)) {

				if (EMAILTO.contains(",")) {
					String[] multipleEmailTo = EMAILTO.split(",");
					for (int j = 0; j < multipleEmailTo.length; j++) {
						if (j == 0)
							msg.addRecipient(Message.RecipientType.TO, new InternetAddress(multipleEmailTo[j]));
						else
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress(multipleEmailTo[j]));
					}

				} else {
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAILTO));
				}
			}

			

			else if (EMAILTOCC.equals(BLANK_VARIABLE) || EMAILTOCC == null)  {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAILTO));
			}

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("Hi client, \n Please find Email Report for suite:- "
					+ ConfigReader.getValue("SuiteName") + " \n \n \n Thanks & Regards \n Test Engineer");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (new File(USERDIR + REPORT_PATH).exists()) {
				delDirectory(new File(USERDIR + REPORT_PATH));
			}

			if (ConfigReader.getValue("HtmlReport").contains("Y")) {
				copyDirectoryData("HtmlReport", "HtmlReport");
			}

			/*if (getCommonSettings().getXlsReport().contains("Y")) {
				copyDirectoryData("ExcelReport", "ExcelReport");
			}*/

			if (ConfigReader.getValue("Logs").contains("Y")) {
				copyDirectoryData("Logs", "Logs");
			}

			createZipFile();

			messageBodyPart = new MimeBodyPart();
			String path = USERDIR + "\\ExecutionReports\\ExecutionReports.zip";
			DataSource source = new FileDataSource(path);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("ExecutionReports.zip");
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect(HOST, USERNAME, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			delDirectory(new File(USERDIR + "\\ExecutionReports\\ExecutionReports"));
	 }
		
	

	/**
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public void copyDirectoryData(String sourceDir, String targetDir) throws IOException {
		File srcDir = new File(USERDIR+ "\\ExecutionReports\\" + sourceDir);
		File destDir = new File(USERDIR+ "\\ExecutionReports\\ExecutionReports\\" + targetDir);
		FileUtils.copyDirectory(srcDir, destDir);
	}

}