import java.io.*;
import java.util.*;

public class config {
    
    public config(){}

    //takes in the config array, which holds the user configurations, and writes these configurations into the text file
    public void setConfig(int[] config){ //instead of taking in the config array, have the setConfig method directly linked to the buttons in the GUI, so in the action listener of the combo box, the configurations are changed
        try{
            String fileName = "configurations.txt";
            File file = new File(fileName);
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.println(config[0]); //number of sides
            writer.println(config[1]); //number of dice
            writer.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
    }

    //returns the number of sides
    public int getSides(){
        String fileName = "configurations.txt";
        int sides = 0;
        try{
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            
            sides = input.nextInt(); //numer of sides

        }   catch(Exception ex){
            ex.printStackTrace();
        }

        return sides;
    }

    //returns the number of dice the user wants to play with
    public int getDice(){ //could I just use getConfig and return config[1] instead of having to pass config into the method?
        String fileName = "configurations.txt";
        int dice = 0;

        try{
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            
            input.nextInt(); //numer of sides
            dice = input.nextInt(); //number of dice

        }   catch(Exception ex){
            ex.printStackTrace();
        }
        
        return dice;
    }
}
