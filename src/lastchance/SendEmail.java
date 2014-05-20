package lastchance;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Anders Peterson
 */
public class SendEmail {
    private static final String username = ""; //enter gmail address here
    private static final String password = ""; //enter account password here
    private static Scanner scanner = new Scanner(System.in);
    
    public static void sendMatches(HashMap<String,Person> everyone) throws InterruptedException {
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
        
        Object[] people = everyone.values().toArray();
        for(int i = 0; i < people.length; i++){
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(((Person) people[i]).getEmail()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(((Person) people[i]).getEmail()));
                message.setSubject("Your Last Chance Dance Matches");
                message.setText("Dear " + ((Person) people[i]).getName() + ","
                        + "\n" + "\n" + "Here are your matches: " + "\n" +
                        ((Person) people[i]).stringMatches() + "\n\n" + "Happy senior week");

                Transport.send(message);

                System.out.println("Matches sent to: " + ((Person) people[i]).getEmail());
            } catch (SendFailedException e) {
                System.out.println("Invalid email address " + ((Person) people[i]).getEmail() + " encountered, enter new email address or press enter to skip");
                String newEmail = scanner.nextLine();
                if(newEmail.length() != 0){
                    ((Person) people[i]).addEmail(newEmail);
                    i--;
                }
            } catch (MessagingException e) {
                System.out.println("Not sent to " + ((Person) people[i]).getEmail());
                System.out.println("Too many login attempts, waiting 2 minutes...");
                Thread.sleep(120000);
                i--;
            }
        }
    }
    
    public static void sendForm(String[] addresses) throws InterruptedException {
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
                    
        for(int i = 0; i < addresses.length; i++){
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(addresses[i]));
                message.setSubject("Class of 2014 Last Chance Dance");
                message.setText("Hello,\n\nSenior week is approaching and it is time for the Last Chance Dance!"
                        + " Below you will see a link to a Google Form that you will fill out for the dance. "
                        + "Please carefully read and follow all of the instructions listed in the form. "
                        + "Additionally, please complete the form by 5:00pm on Monday May 19th. "
                        + "You have no obligation to participate in this matching system."
                        + "" //enter gmail URL here
                        + "\n\nGood luck with finals!");

                Transport.send(message);

                System.out.println("Sent to: " + addresses[i]);
            } catch (SendFailedException e) {
                System.out.println("Invalid email address " + addresses[i] + " encountered, enter new email address or press enter to skip");
                String newEmail = scanner.nextLine();
                if(newEmail.length() != 0){
                    addresses[i] = newEmail;
                    i--;
                }
            } catch (MessagingException e) {
                System.out.println("Not sent to " + addresses[i]);
                System.out.println("Too many login attempts, waiting 2 minutes...");
                i--;
                Thread.sleep(120000);
            }
        }
    }
    
    public static void sendTop(HashMap<String, Person> everyone, ArrayList<Person> top) throws InterruptedException {
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
        Object[] people = everyone.values().toArray();
        for(int i = 0; i < people.length; i++){
            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(((Person) people[i]).getEmail()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(((Person) people[i]).getEmail()));
                message.setSubject("Top 10 Most Chosen Students");

                StringBuilder sb = new StringBuilder();
                sb.append("1. anders peterson (");
                sb.append(top.get(0).getTimesChosen() + 1);
                sb.append(" times)\n");
                
                int num = top.size() < 10 ? top.size() : 10;
                int index = 2;
                for(int j = 0; j < num - 1; j++){
                    sb.append(index);
                    sb.append(". ");
                    sb.append(top.get(j).getName());
                    int temp = j;
                    while(top.get(j).getTimesChosen() == top.get(j + 1).getTimesChosen()){
                        j++;
                        num++;
                        sb.append(", " + top.get(j).getName());
                    }
                    sb.append(" (");
                    sb.append(top.get(j).getTimesChosen());
                    sb.append(" times)\n");
                    index++;
                    if(index == num - 1){
                        j = num - 1;
                    }
                }

                message.setText("Hello " + ((Person) people[i]).getName() + ",\n\nBelow is a ranking of the most"
                        + " chosen students for this year's last chance dance.\n\n" + sb.toString() + 
                        "\n\nEnjoy senior week!");


                Transport.send(message);

                System.out.println("Sent to: " + ((Person) people[i]).getEmail());
            } catch (SendFailedException e) {
                System.out.println("Invalid email address " + ((Person) people[i]).getEmail() + " encountered, enter new email address or press enter to skip");
                String newEmail = scanner.nextLine();
                if(newEmail.length() != 0){
                    ((Person) people[i]).addEmail(newEmail);
                    i--;
                }
            } catch (MessagingException e) {
                System.out.println("Not sent to " + ((Person) people[i]).getEmail());
                System.out.println("Too many login attempts, waiting 2 minutes...");
                Thread.sleep(120000);
                i--;
            }
        }
    }
    
}
