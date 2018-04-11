package clientSide;

import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailKit {
	private String from; 
	private String password;
	private String fromName; 
	private String to; 
	private final String HOST = "smtp.gmail.com"; 
	private final int PORT = 587;
	
	private String body = String.join(
    	    System.getProperty("line.separator"),
    	    "<h1>Amazon SES SMTP Email Test</h1>",
    	    "<p>This email was sent with Amazon SES using the ", 
    	    "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
    	    " for <a href='https://www.java.com'>Java</a>."
    	);
	public static boolean sendEmail(String from, String fromName, String[] to, String password){
		boolean ret = true;
		try {
			
		} catch(Exception e) {
			System.err.println( e.getMessage() );
			ret = false;
		}
		return ret;
	}
	private EmailKit(String from, String fromName, String[] to, String password) throws Exception{
		InternetAddress[] addys = new InternetAddress[to.length];
		for(int i = 0; i < to.length; i++) addys[i] = new InternetAddress( to[i] );
		
		setup(addys);
	}
	private void setup(InternetAddress[] to) throws Exception {
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT); 
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		
		MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from,fromName));
        //send to all
        
        msg.setRecipients(Message.RecipientType.TO, to);
        
        msg.setSubject("Learning Platform Email");
        msg.setContent(body,"text/html");
        
        Transport t = session.getTransport();

        t.connect(HOST, from, password);
        t.sendMessage(msg, msg.getAllRecipients());
        
        t.close();
	}
	
	

}
