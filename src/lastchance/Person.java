package lastchance;

import java.util.ArrayList;
/**
 *
 * @author Anders Peterson
 */
public class Person {
    private String myName;
    private String email;
    private ArrayList<String> choices;
    private ArrayList<String> matches;
    
    public Person(String name){
        myName = name;
        choices = new ArrayList<>();
        matches = new ArrayList<>();
    }
    
    public String getName(){
        return myName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void addName(String choice){
        choices.add(choice);
    }
    
    public void addEmail(String email){
        this.email = email;
    }
    
    public void addMatch(String name){
        matches.add(name);
    }
    
    public ArrayList<String> getMatches(){
        return matches;
    }
    
    public ArrayList<String> getChoices(){
        return choices;
    }
    
    public void printMatches(){
        if(matches.size() == 0){
            System.out.println("No matches.");
        }
        for(String choice : matches){
            System.out.println(choice);
        }
    }
    
    public String stringMatches(){
        String matchList = "";
        if(matches.size() == 0){
            matchList = "Sorry, no matches. There are plenty of fish in the sea though!";
        }
        else{
            for(String s : matches){
                matchList += s + "\n";
            }
        }
        return matchList;
    }
    
    public void printChoices(){
        for(String choice : choices){
            System.out.println(choice);
        }
    }
}
