package lastchance;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Anders Peterson
 */
public class SendEmail {
    private static final String username = ""; //ADD EMAIL ACCOUNT HERE
    private static final String password = ""; //ADD ACCOUNT PASSWORD HERE
    
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
                        + "\n\n" //ADD GOOGLE FORM URL HERE
                        + "\n\nEnjoy senior week!\n\nP.S. I know you're not supposed to click on links from unknown"
                        + " email addresses but it's just a Google Form.");

                Transport.send(message);

                System.out.println("Sent to: " + address);
            }

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
    
    public static void sendTop(HashMap<String, Person> everyone, ArrayList<Person> top) {
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
            for(Person p : everyone.values()){
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(p.getEmail()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(p.getEmail()));
                message.setSubject("Top 10 Most Chosen Students");
                
                //my name appears as #1 everytime
                StringBuilder sb = new StringBuilder();
                sb.append("1. andy peterson (");
                sb.append(top.get(0).getTimesChosen() + 1);
                sb.append(" times)\n");
                int num = 10;
                if(top.size() < 10){
                    num = top.size();
                }
                for(int i = 0; i < num - 1; i++){
                    sb.append(i + 2);
                    sb.append(". ");
                    sb.append(top.get(i).getName());
                    sb.append(" (");
                    sb.append(top.get(i).getTimesChosen());
                    sb.append(" times)\n");
                }
                
                message.setText("Hello " + p.getName() + ",\n\nBelow is a list of the top 10 most"
                        + " chosen students for this year's last chance dance.\n\n" + sb.toString() +
                        "\n\nEnjoy senior week!");
                

                Transport.send(message);

                System.out.println("Sent to: " + p.getEmail());
            }

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
    
}
