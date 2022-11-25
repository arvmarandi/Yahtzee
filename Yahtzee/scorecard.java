import java.util.*;

public class scorecard {
    
    config myConfig = new config();

    //calculations for anything in the upper part of the scorecard
    public int upperCalc(ArrayList<Integer> hand, int target){
        int counter = 0;
        for(int i = 0; i < hand.size(); i++){ //for loop going through the user's hand to see if the target value is in the hand
            if(hand.get(i) == target){
                counter++;
            }
        }
        return (target * counter);
    }

    //calculates the sum of all the dice
    public int totalAllDice(ArrayList<Integer> hand){
        int total = 0;
        for (int diePosition = 0; diePosition < myConfig.getDice(); diePosition++){
            total += hand.get(diePosition);
        }
        return total;
    }

    //if the max of a kind is found
    public int maxOfAKindFound(ArrayList<Integer> hand){
        int maxCount = 0;
        int currentCount;
        for(int dieValue = 1; dieValue <= myConfig.getSides(); dieValue++){
            currentCount = 0;
            for(int diePosition = 0; diePosition < myConfig.getDice(); diePosition++){
                if(hand.get(diePosition) == dieValue)
                    currentCount++;
            }
            if (currentCount > maxCount)
                maxCount = currentCount;
        }
        return maxCount;
    }

    //if three of a kind, aka, three of the same side are found in the hand
    public int threeOfAKind(ArrayList<Integer> hand){
        if(maxOfAKindFound(hand) >= 3){
            return totalAllDice(hand);
        }
        else{
            return 0;
        }
    }

    //if four of a kind are found, meaning four of the same side are found in the hand
    public int fourOfAKind(ArrayList<Integer> hand){
        if(maxOfAKindFound(hand) >= 4){
            return totalAllDice(hand);
        }
        else{
            return 0;
        }
    }

    //if the entire hand are the same
    public int yahtzee(ArrayList<Integer> hand){
        if(maxOfAKindFound(hand) >= 5){
            return 50;
        }
        else{
            return 0;
        }
    }

    //returns true if there is a fullhouse
    public Boolean fullHouseFound(ArrayList<Integer> hand){
        Boolean foundFH = false;
        Boolean found3K = false;
        Boolean found2K = false;
        int currentCount;

        for (int dieValue = 1; dieValue <= myConfig.getSides(); dieValue++){
            currentCount = 0;
            for (int diePosition = 0; diePosition < myConfig.getDice(); diePosition++)
            {
                if (hand.get(diePosition) == dieValue)
                    currentCount++;
            }
            if (currentCount == 2)
                found2K = true;
            if (currentCount == 3)
                found3K = true;
        }
        if (found2K && found3K)
            foundFH = true;
        return foundFH;
    }

    //if there is a fullhouse, it will return a score of 25, else, it won't
    public int fullHouse(ArrayList<Integer> hand){
        if(fullHouseFound(hand)){
            return 25;
        }
        else{
            return 0;
        }
    }

    //looks to see if there is a straight and returns the length of the straight
    public int maxStraightFound(ArrayList<Integer> hand){
        int maxLength = 1;
        int curLength = 1;

        for(int counter = 0; counter < (myConfig.getDice() - 1); counter++){
            if (hand.get(counter) + 1 == hand.get(counter + 1) ) //jump of 1
                curLength++;
            else if (hand.get(counter) + 1 < hand.get(counter + 1)) //jump of >= 2
                curLength = 1;
            if (curLength > maxLength)
                maxLength = curLength;
        }
        return maxLength;
    }

    //if the length of the straight is 4 or longer
    public int smallStraight(ArrayList<Integer> hand){
        if(maxStraightFound(hand) >= 4){
            return 30;
        }
        else{
            return 0;
        }
    }

    //if the length of the straight is 5 or longer
    public int largeStraight(ArrayList<Integer> hand){
        if(maxStraightFound(hand) >= 5){
            return 40;
        }
        else{
            return 0;
        }
    }

    //returns chance
    public int chance(ArrayList<Integer> hand){
        return totalAllDice(hand);
    }

    //calculates the upper scorecard total
    public int calcSubTotal(ArrayList<Integer> subArray){
        int subtotal = 0;
        for(int i = 0; i < subArray.size(); i++){ //loop through the entire subtotal arraylist, summing up the scores of the upper part of the scorecard
            subtotal += subArray.get(i);
        }
        return subtotal;
    }

    //calculates the lower scorecard total
    public int calcLowerTotal(ArrayList<Integer> lower){
        int lowertotal = 0;
        for(int i = 0; i < lower.size(); i++){
            lowertotal += lower.get(i);
        }
        return lowertotal;
    }

    //calculates the sum of the upper and lower totals
    public int calcGrandTotal(ArrayList<Integer> total){
        int grand = 0;
        for(int i = 0; i < total.size(); i++){
            grand += total.get(i);
        }
        return grand;
    }
}
