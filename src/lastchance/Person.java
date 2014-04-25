package lastchance;

import java.util.ArrayList;
/**
 *
 * @author Anders Peterson
 */
public class Person implements Comparable<Person>{
    private String myName;
    private String email;
    private int timesChosen;
    private ArrayList<String> choices;
    private ArrayList<String> matches;
    
    public int compareTo(Person person){
        if(this.getTimesChosen() < person.getTimesChosen()){
            return 1;
        } else if(this.getTimesChosen() == person.getTimesChosen()){
            return 0;
        } else{
            return -1;
        }
    }
    
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
    
    public void chosen(){
        timesChosen++;
    }
    
    public ArrayList<String> getMatches(){
        return matches;
    }
    
    public ArrayList<String> getChoices(){
        return choices;
    }
    
    public int getTimesChosen(){
        return timesChosen;
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
