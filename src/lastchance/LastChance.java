package lastchance;


import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Anders Peterson
 */
public class LastChance {
    private static HashMap<String,Person> everyone;
    private static ArrayList<Person> top;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws FileNotFoundException, InterruptedException{
        everyone = new HashMap<>();
        System.out.println("Author: Anders Peterson");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Welcome to the Last Chance Dance matching application. Enter \"sendForm\""
                + " to send a Google Doc to the senior class, \"sendMatches\" to send emails to the"
                + " senior class with their respective matches, \"topChoices\" to send a list of the"
                + " top 10 most chosen people, or just press enter to look at the collected data."
                + " Enter \"exit\" to quit the program.");
        System.out.println("------------------------------------------------------------------------");
        String enter = scanner.nextLine();
        
        if(enter.equals("sendForm" )){
            System.out.println("Emailing the entire class of 2014, are you sure? y/n");
            if(scanner.nextLine().equals("y")){
                System.out.println("Please enter the name of a file containing email addresses.");
                String emailList = "";
                while(emailList.equals("")){
                    try{
                        String fileName = scanner.nextLine();
                        emailList = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
                    } catch(FileNotFoundException e){
                        System.out.println("Please enter a valid file name contained in this directory.");
                        emailList = "";
                    }
                }
                String[] emails = emailList.split("\n");
                SendEmail.sendForm(emails);
            }
        }
        else{
            System.out.println("Please enter the name of a .csv file containing the response data.");
            String contents = "";
            while(contents.equals("")){
                try{
                    contents = new Scanner(new File(scanner.nextLine())).useDelimiter("\\Z").next();
                } catch(FileNotFoundException e){
                    System.out.println("Please enter a valid file name contained in this directory.");
                    contents = "";
                }
            }
            System.out.println("populating...");
            populate(contents);
            System.out.println("generating matches...");
            generateMatches();
            System.out.println("finding most chosen...");
            top = mostChosen();
            if(enter.equals("sendMatches")){
                System.out.println("Emailing the entire class of 2014, are you sure? y/n");
                if(scanner.nextLine().equals("y")){
                    SendEmail.sendMatches(everyone);
                }
            } else if(enter.equals("topChoices")){
                System.out.println("Emailing the entire class of 2014, are you sure? y/n");
                if(scanner.nextLine().equals("y")){
                    SendEmail.sendTop(everyone, top);
                }
            }
            else{
                if(enter.equals("exit")){
                    System.out.println("Goodbye");
                } 
                else {
                    
                    System.out.println("Specify \"matches\" to see someone's matches, \"choices\" to see someone's"
                            + " choices, \"top\" to see the most chosen people, or \"me\" to see who listed your name.");
                    String mode = scanner.nextLine();
                    if(mode.equals("exit")){
                        mode = "exit";
                    }
                    else{
                        while(!mode.equals("matches") && !mode.equals("choices") && !mode.equals("me") && !mode.equals("top")){
                            System.out.println();
                            System.out.println("You must specify \"matches,\" \"choices,\" \"top,\" or \"me\"");
                            mode = scanner.nextLine();
                        }
                    }
                    String s = mode;
                    while(!s.equals("exit")){
                        try{
                            if(s.equals("top")){
                                mode = s;
                                System.out.println("How many would you like to see?");
                                int howMany = -1;
                                while(howMany < 0){
                                    try{
                                        howMany = Integer.parseInt(scanner.nextLine());
                                    } catch(NumberFormatException e){
                                        System.out.println("Please enter a valid integer");
                                        howMany = -1;
                                    }
                                }
                                if(howMany > top.size()){
                                    howMany = top.size();
                                }
                                System.out.println("1. anders peterson " + "(" +
                                        (top.get(0).getTimesChosen() + 1) + " times)");
                                for(int i = 0; i < howMany-1; i++){
                                    System.out.println((i+2) + ". " + top.get(i).getName()
                                            + " (" + top.get(i).getTimesChosen() + " times)");
                                }
                            }
                            if(s.equals("matches") || s.equals("choices") || s.equals("me")){
                                mode = s;
                                System.out.println("Name?");
                                s = scanner.nextLine();
                            }
                            if(mode.equals("matches")){
                                everyone.get(s).printMatches();
                                System.out.println();
                            } else if(mode.equals("choices")){
                                everyone.get(s).printChoices();
                                System.out.println();
                            } if(mode.equals("me")){
                                int matches = 0;
                                for(Person p : everyone.values()){
                                    if(p.getChoices().contains(s)){
                                        matches++;
                                        System.out.println(p.getName());
                                    }
                                }
                                if(matches == 0){ System.out.println("Not chosen or not in system."); }
                                System.out.println();
                            }
                            s = scanner.nextLine();
                        }catch (NullPointerException e){
                            System.out.println("Name not in system.");
                            s = scanner.nextLine();
                        }
                    }
                    System.out.println("Goodbye");
                }
            }
        }
    }
    
    private static void generateMatches(){
        for(Person person : everyone.values()){
            ArrayList<String> choices = person.getChoices();
            for(String s : choices){
                if(everyone.containsKey(s)){
                    Person possibleMatch = everyone.get(s);
                    if(possibleMatch.getChoices().contains(person.getName())){
                        person.addMatch(s);
                    }
                }
            }
        }
    }
    
    private static ArrayList<Person> mostChosen(){
        for(Person person : everyone.values()){
            for(String s : person.getChoices()){
                if(everyone.keySet().contains(s)){
                    everyone.get(s).chosen();
                }
            }
        }
        ArrayList<Person> top = new ArrayList<>();
        top.addAll(everyone.values());
        Collections.sort(top);
        return top;
    }
    
    private static void populate(String contents){
        contents = contents.replaceAll("\"", ",");
        String[] names = contents.split(",");
        for(int i = 0; i < names.length; i++){
            names[i] = names[i].toLowerCase().trim();
        }

        for(int i = 1; i < names.length; i++){
            String email = names[i];
            i++;
            Person person = new Person(names[i]);
            person.addEmail(email);
            i++;

            for(int j = 1; j < 11; j++){
                if(names[i].equals("new person")){
                    j = 11;
                }
                else{
                    person.addName(names[i]);
                    i++;
                }
            }
            everyone.put(person.getName(),person);
            while(!names[i].equals("new person")){
                i++;
            }
        }
    }
    
    
    private static void printFile() throws FileNotFoundException{
        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);
        System.out.println("Colby College Class of 2014 Last Chance Dance");
        System.out.println("-----------------------------------");
        for(Person person : everyone.values()){
            System.out.println(person.getName());
            System.out.println("email: " + person.getEmail());
            System.out.println("matches:");
            person.printMatches();
            System.out.println();
        }
    }
}
