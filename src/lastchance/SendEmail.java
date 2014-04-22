package lastchance;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Anders Peterson
 */
public class SendEmail {
    private static final String username = ""; //add email address to send from here
    private static final String password = ""; //add email account password here

    public static void sendMatches(HashMap<String,Person> everyone) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            for(Person person : everyone.values()){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(person.getEmail()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(person.getEmail()));
                message.setSubject("Your Last Chance Dance Matches");
                message.setText("Dear " + person.getName() + ","
                        + "\n" + "\n" + "Here are your matches: " + "\n" +
                        person.stringMatches() + "\n" + "\n" + "Happy senior week");

                Transport.send(message);

                System.out.println("Complete: " + person.getEmail());
            }
        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
    
    public static void sendForm(String[] addresses) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            for(String address : addresses){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(address));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(address));
                message.setSubject("Class of 2014 Last Chance Dance");
                message.setText("Hello,\n\nSenior week is upon us and it is time for the Last Chance Dance!"
                        + " Below you will see a link to a Google Form that you will fill out for the dance. "
                        + "Please carefully read and follow all of the instructions listed in the form. "
                        + "You have no obligation to participate in this matching system."
                        + "\n\n" \\add a URL to a Google Form here
                        + "\n\nEnjoy senior week!\n\nP.S. I know you're not supposed to click on links from unknown"
                        + " email addresses but it's just a Google Form and I'm a student here also.");

                Transport.send(message);

                System.out.println("Sent to: " + address);
            }

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
}
