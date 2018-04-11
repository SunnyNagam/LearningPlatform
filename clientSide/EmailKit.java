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
	private InternetAddress[] to; 
	private final String HOST = "smtp.gmail.com"; 
	private final int PORT = 587;
	
	private String body;
	public static EmailKit defineEmail(String from, String fromName, String[] to, String password){
		try {
			return  new EmailKit(from,fromName,to,password);
		} catch(Exception e) { System.err.println( e.getMessage() ); }
		return null;
	}
	//sends an email with 
	public boolean send(String bod) {
		this.body = bod;
		boolean ret = true;
		try {
			setup();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			ret = false;
		}
		return ret;
	}
	private EmailKit(String from, String fromName, String[] to, String password) throws Exception{
		this.from = from;
		this.fromName = fromName;
		this.password = password;
		
		InternetAddress[] addys = new InternetAddress[to.length];
		for(int i = 0; i < to.length; i++) addys[i] = new InternetAddress( to[i] );
		
		this.to = addys;
	}
	private void setup() throws Exception {
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
        
        //System.out.println("SUCCESS");
	}
	public boolean sendFormatted(String header, String ... lines) {
		String arg = "";
		for (String a : lines) arg += a + "<br>";
		return send( String.join( System.getProperty("line.separator"), "<h1>", header, "</h1>", "<p>", arg , "</p>" ));
	}
	//testy test
	public static void main (String[] args) {
		String[] to = {"keenangaudio@me.com"};
		EmailKit.defineEmail("profemail409@gmail.com", "Keenan", to, "rootpass").sendFormatted("NICE TITLE","hi", "suh", "hola","next line","wowzers");
	}
	

}
