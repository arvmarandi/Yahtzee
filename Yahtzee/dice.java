import java.util.*;

public class dice {
    
    config myConfig = new config();

    //creates a new hand randomly, based on how long the user wants the hand to be
    public ArrayList<Integer> makeHand(ArrayList<Integer> hand){
        for(int i = 0; i < myConfig.getDice(); i++){
            int random = new Random().nextInt(myConfig.getSides()) + 1;
            hand.add(random);
        }
        return hand;
    }

    //rolls a dice and gives a random number between 1 and however many sides the user has requested
    public int rollDice(){
        int dice = new Random().nextInt(myConfig.getSides()) + 1;
        return dice;
    }

}
