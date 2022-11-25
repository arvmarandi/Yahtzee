import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.util.Arrays;

/*
 * To do:
 * Writeup
 * Merge
 * Push
 */

public class GUI {
    int turns = 3; //the number of rolls per hand are three by default
    int[] config = new int[2]; //array to hold the configurations
    config myConfig = new config(); //config constructor
    dice myDice = new dice(); //dice constructor
    scorecard myScorecard = new scorecard(); //scorecard constructor
    player myPlayer = new player(); //player constructor
    ArrayList<JButton> buttonList = new ArrayList<JButton>(); //arraylist that holds the dice
    ArrayList<JButton> scoreOptions = new ArrayList<JButton>(); //arraylist that holds the scoring option buttons
    ArrayList<JLabel> scorecardLabels = new ArrayList<JLabel>(); //arraylist that holds the scorecard labels
    ArrayList<Integer> hand = new ArrayList<Integer>(); //arraylist that holds the current hand
    ArrayList<Integer> total = new ArrayList<Integer>(); //arraylist that holds all the scores
    ArrayList<Integer> subTotal = new ArrayList<Integer>(); //arraylist that holds only the scores for the upper scorecard
    ArrayList<Integer> lowTotal = new ArrayList<Integer>(); //arraylist that holds only the scores for the lower scorecard
    JFrame frame; //main frame the game is being played on
    JPanel imagePanel, buttonPanel, displayPanel, mainButtonPanel, scorePanel; //all the panels that are used in my program
    JButton rollDice, scoreButton, start; //some of, but not all, the buttons that are used in this program
    JLabel diceLabel, sidesLabel, diceNumber, label, nTurns; //some of, but not all, the labels that are used in this program
    JComboBox diceSides, diceNum; //drop down menus in the start frame

    public GUI(){ //constructor for GUI, which sets up the frame, the only thing that is consistent throughout this program
        frame = new JFrame();
        frame.setSize(1600,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(null);
    }

    //the startGame method creates the panels where the user is able to choose their configurations and is shown at the beginning of each yahtzee playthrough
    public void startGame(){ 

        //creating the panel for the yahtzee image
        imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBounds(318, 20, 965, 389);

        //creating yahtzee image
        try{
        BufferedImage img = ImageIO.read(new File("yahtzee.jpg"));
        ImageIcon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
        imagePanel.add(label);
        }catch (IOException e) {
            e.printStackTrace();
         }

        //creating the panel for the drop down menus
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); //change color to white once the drop down menus are added
        buttonPanel.setBounds(318, 409, 965, 400);
        buttonPanel.setLayout(null);

        //dice sides drop down menu
        String[] sideOptions = new String[]{"6", "8", "12"};
        diceSides = new JComboBox(sideOptions);
        diceSides.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        diceSides.setBounds(32, 80, 300, 100);
        buttonPanel.add(diceSides);

        //number of sides label
        sidesLabel = new JLabel("Number of Sides");
        sidesLabel.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        sidesLabel.setBounds(50,30,300,25);
        buttonPanel.add(sidesLabel);

        //number of dice in play drop down menu
        String[] diceOptions = new String[]{"5", "6","7"};
        diceNum = new JComboBox(diceOptions);
        diceNum.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        diceNum.setBounds(632,80, 300, 100);
        buttonPanel.add(diceNum);

        //number of dice label
        diceLabel = new JLabel("Number of Dice");
        diceLabel.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        diceLabel.setBounds(657,30,300,25);
        buttonPanel.add(diceLabel);
        
        //start button
        start = new JButton();
        start.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        start.setText("Start");
        start.setBounds(332,250,300,100);
        Color sColor = new Color(0,204,0);
        start.setBackground(sColor);
        start.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(e.getSource() == start){ //if start is clicked, set the configurations to whatever the user selected in the drop down menus

                        config[0] = Integer.parseInt((String)diceSides.getSelectedItem());
                        config[1] = Integer.parseInt((String)diceNum.getSelectedItem());
                        myConfig.setConfig(config);
                        
                        makeScoringOptions();
                        mainGame(); 
                    }   
                }
            }
        );
        buttonPanel.add(start);

        //adds and visibility
        frame.add(buttonPanel);
        frame.add(imagePanel);
        frame.setVisible(true);
    }

    public void makeScoringOptions(){

        //creating scoring option buttons as well as creating scorecard labels
        for(int i = 0; i < myConfig.getSides(); i++){
            JButton tempButton = new JButton();
            JLabel tempLabel = new JLabel((i+1) + ": 0");
            tempButton.setPreferredSize(new Dimension(150,150));
            tempLabel.setPreferredSize(new Dimension(500, 39)); 
            tempLabel.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
            scoreOptions.add(tempButton);
            scorecardLabels.add(tempLabel);
        }

        //subtotal label
        JLabel subTotal = new JLabel("Subtotal: 0");
        subTotal.setPreferredSize(new Dimension(500, 39));
        subTotal.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scorecardLabels.add(subTotal);

        //bonus label
        JLabel bonus = new JLabel("Bonus: 0");
        bonus.setPreferredSize(new Dimension(500, 39));
        bonus.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scorecardLabels.add(bonus);

        //three of a kind button and label
        JButton threekindB = new JButton();
        JLabel threekindL = new JLabel("3K: 0");
        threekindB.setPreferredSize(new Dimension(150,150));
        threekindL.setPreferredSize(new Dimension(500, 39));
        threekindL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(threekindB);
        scorecardLabels.add(threekindL);

        //four of a kind button and label
        JButton fourkindB = new JButton();
        JLabel fourkindL = new JLabel("4K: 0");
        fourkindB.setPreferredSize(new Dimension(150,150));
        fourkindL.setPreferredSize(new Dimension(500, 39));
        fourkindL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(fourkindB);
        scorecardLabels.add(fourkindL);

        //fullhouse button and label
        JButton fullhouseB = new JButton();
        JLabel fullhouseL = new JLabel("FH: 0");
        fullhouseB.setPreferredSize(new Dimension(150,150));
        fullhouseL.setPreferredSize(new Dimension(500, 39));
        fullhouseL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(fullhouseB);
        scorecardLabels.add(fullhouseL);

        //small straight button and label
        JButton SSB = new JButton();
        JLabel SSL = new JLabel("SS: 0");
        SSB.setPreferredSize(new Dimension(150,150));
        SSL.setPreferredSize(new Dimension(500, 39));
        SSL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(SSB);
        scorecardLabels.add(SSL);

        //large straight button and label
        JButton LSB = new JButton();
        JLabel LSL = new JLabel("LS: 0");
        LSB.setPreferredSize(new Dimension(150,150));
        LSL.setPreferredSize(new Dimension(500, 39));
        LSL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(LSB);
        scorecardLabels.add(LSL);

        //yahtzee button and label
        JButton YB = new JButton();
        JLabel YL = new JLabel("Yahtzee: 0");
        YB.setPreferredSize(new Dimension(150,150));
        YL.setPreferredSize(new Dimension(500, 39));
        YL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(YB);
        scorecardLabels.add(YL);

        //chance button and label
        JButton CB = new JButton();
        JLabel CL = new JLabel("Chance: 0");
        CB.setPreferredSize(new Dimension(150,150));
        CL.setPreferredSize(new Dimension(500, 39));
        CL.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scoreOptions.add(CB);
        scorecardLabels.add(CL);

        //lower total label
        JLabel lowerTotal = new JLabel("Lower Total: 0");
        lowerTotal.setPreferredSize(new Dimension(500, 39));
        lowerTotal.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scorecardLabels.add(lowerTotal);

        //grand total label
        JLabel grandTotal = new JLabel("Grand Total: 0");
        grandTotal.setPreferredSize(new Dimension(500, 39));
        grandTotal.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        scorecardLabels.add(grandTotal);
    }

    //this method is where the actual Yahtzee game takes place.
    public void mainGame(){

        //clearing the frame of its previous contents
        frame.getContentPane().removeAll();
        frame.repaint();

        //creating the objects that will be used 
        myDice.makeHand(hand);
        Character[] keep = new Character[myConfig.getDice()];
        myPlayer.makeKeep(keep); //makes default keep
        Character[] fullKeep = new Character[myConfig.getDice()];
        myPlayer.makeFull(fullKeep); //makes an array that holds all ys

        //creating the display panel
        displayPanel = new JPanel();
        displayPanel.setBackground(Color.GRAY);
        displayPanel.setBounds(0, 0, 1065, 675);
        displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        //creating the button panel for main
        mainButtonPanel = new JPanel();
        mainButtonPanel.setBackground(Color.WHITE);
        mainButtonPanel.setBounds(0, 675, 1065, 225);
        mainButtonPanel.setLayout(null);

        //creating the scorecard panel
        scorePanel = new JPanel();
        scorePanel.setBackground(Color.BLUE);
        scorePanel.setBounds(1065, 0, 535, 900);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS)); //score will be presented in vertical order from top to bottom

        //creating turns label
        nTurns = new JLabel();
        nTurns.setText("Number of Turns: " + String.valueOf(turns));
        nTurns.setBounds(191, 75, 150, 75);
        mainButtonPanel.add(nTurns);

        //creating the scorecard
        for(int i = 0; i < scorecardLabels.size(); i++){
            scorePanel.add(scorecardLabels.get(i));
        }

        // divider between mainGame panel creation and actual gameplay

        //creating all the dice that will be shown
        for(int i = 0; i < myConfig.getDice(); i++){ 
            int index = i;
            JButton dice = new JButton();
            dice.setPreferredSize(new Dimension(100,100));
            diceNumber = new JLabel(); //the label of the button will be the dice number
            diceNumber.setText(String.valueOf(hand.get(i)));
            dice.add(diceNumber);
            dice.addActionListener( //adding action listener to that specific dice in the Array List
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        dice.setBackground(Color.GREEN);
                        keep[index] = 'y'; //keep at the index of i will equal y
                    }
                }
            );

        buttonList.add(dice); //adding the specific dice into the list of buttons
        displayPanel.add(dice);
        }

        //creating roll dice button
        if(turns > 0){ 
            rollDice = new JButton();
            rollDice.setText("Roll Dice");
            rollDice.setBounds(457,75,150,75);
            rollDice.addActionListener(
                    (e -> {
                        for(int j = 0; j < myConfig.getDice(); j++){ //for loop to reset the dice if they aren't being clicked aka the user does not want to keep them
                            if(keep[j] == 'n'){
                                hand.set(j, myDice.rollDice()); //if the user doesn't want to keep the dice, reroll
                                label = new JLabel();
                                label.setText(String.valueOf(hand.get(j))); //label is set to the new dice
                                buttonList.get(j).removeAll();
                                buttonList.get(j).add(label); //setting the dice to the number
                            }
                        }
                        turns--; //decrements the amount of turns every time the user asks for a reroll
                        nTurns.setText("Number of Turns: " + String.valueOf(turns));
                        mainButtonPanel.revalidate(); //revalidate is the notify call that allows for the dice to have a renewed look
                        displayPanel.revalidate();
                        scorePanel.revalidate();
                        if(turns == 0 || Arrays.equals(keep, fullKeep)){ //if the number of turns are up or the user would like to keep their entire hand
                            displayPanel.removeAll(); 
                            displayPanel.repaint();
                            displayPanel.revalidate();
                            scorePanel.revalidate();

                            for(int i = 0; i < scoreOptions.size(); i++){ //this for loop will be displaying all the options as well as providing each option with a score
                                int index = i;
                                int index2 = i + 1;
                                int index3 = i + 2;
                                if(i < scoreOptions.size() - 7){ //creating the scoring options and adding to the scorecard for all the options that aren't combinations like "three of a kind," "fullhouse," etc.
                                    scoreOptions.get(i).setText((i+1) + " is: " + String.valueOf(myScorecard.upperCalc(hand, i+1)));
                                    scoreOptions.get(i).addActionListener( 
                                            (a -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index).setText((String.valueOf(index2)) + ": " + String.valueOf(myScorecard.upperCalc(hand, index+1)));
                                            subTotal.add(myScorecard.upperCalc(hand, index+1));
                                            total.add(myScorecard.upperCalc(hand, index+1));
                                            scorecardLabels.get(myConfig.getSides()).setText("Subtotal: " + String.valueOf(myScorecard.calcSubTotal(subTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));
                                            System.out.println(index2);
                                            System.out.println(myScorecard.upperCalc(hand, index+1));
                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame(); //recursive call
                                            
                                        }));
                                }
                                else{ //creating the scoring options and scorecard labels for the special combinations
                                    if(i == (scoreOptions.size() - 7)){
                                        scoreOptions.get(i).setText("3K is: " + String.valueOf(myScorecard.threeOfAKind(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (b -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("3K: " + myScorecard.threeOfAKind(hand));
                                            lowTotal.add(myScorecard.threeOfAKind(hand));
                                            total.add(myScorecard.threeOfAKind(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));
                                            
                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame(); 
                                        }));
                                    }
                                    if(i == (scoreOptions.size() - 6)){
                                        scoreOptions.get(i).setText("4K is: " + String.valueOf(myScorecard.fourOfAKind(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (c -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("4K: " + myScorecard.fourOfAKind(hand));
                                            lowTotal.add(myScorecard.fourOfAKind(hand));
                                            total.add(myScorecard.fourOfAKind(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));

                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame(); 
                                        }));
                                    }
                                    if(i == (scoreOptions.size() - 5)){
                                        scoreOptions.get(i).setText("FH is: " + String.valueOf(myScorecard.fullHouse(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (d -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("FH: " + myScorecard.fullHouse(hand));
                                            lowTotal.add(myScorecard.fullHouse(hand));
                                            total.add(myScorecard.fullHouse(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));
                                            
                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame();
                                        }));
                                    }
                                    if(i == (scoreOptions.size() - 4)){
                                        scoreOptions.get(i).setText("SS is: " + String.valueOf(myScorecard.smallStraight(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (f -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("SS: " + myScorecard.smallStraight(hand));
                                            lowTotal.add(myScorecard.smallStraight(hand));
                                            total.add(myScorecard.smallStraight(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));

                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame(); 
                                        }));
                                    }
                                    if(i == (scoreOptions.size() - 3)){
                                        scoreOptions.get(i).setText("LS is: " + String.valueOf(myScorecard.largeStraight(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (g -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("LS: " + myScorecard.largeStraight(hand));
                                            lowTotal.add(myScorecard.largeStraight(hand));
                                            total.add(myScorecard.largeStraight(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));
                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame();
                                        }));
                                    }
                                    if(i == (scoreOptions.size() - 2)){
                                        scoreOptions.get(i).setText("Y is: " + String.valueOf(myScorecard.yahtzee(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (h -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("Y: " + myScorecard.yahtzee(hand));
                                            lowTotal.add(myScorecard.yahtzee(hand));
                                            total.add(myScorecard.yahtzee(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));
                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame(); 
                                        }));
                                    }
                                    if(i == (scoreOptions.size() - 1)){
                                        scoreOptions.get(i).setText("C is: " + String.valueOf(myScorecard.chance(hand)));
                                        scoreOptions.get(i).addActionListener(
                                            (k -> {
                                            scoreOptions.get(index).setEnabled(false); //disables the button once it is pressed
                                            scorecardLabels.get(index3).setText("Chance: " + myScorecard.chance(hand));
                                            lowTotal.add(myScorecard.chance(hand));
                                            total.add(myScorecard.chance(hand));
                                            scorecardLabels.get(myConfig.getSides() + 9).setText("Lower Total: " + String.valueOf(myScorecard.calcLowerTotal(lowTotal)));
                                            scorecardLabels.get(myConfig.getSides() + 10).setText("Grand Total: " + String.valueOf(myScorecard.calcGrandTotal(total)));
                                            turns = 3;
                                            buttonList.clear(); //have to clear the array
                                            hand.clear(); //must clear the current hand so the new hand can be made
                                            mainGame(); 
                                        }));
                                    }
                                }
                                displayPanel.add(scoreOptions.get(i)); //add the scoring options to the display panel, so the user can see what their options are
                            }
                        }
                    }));
            mainButtonPanel.add(rollDice);
        }
        //adding panels to the frame
        frame.add(scorePanel);
        frame.add(mainButtonPanel);
        frame.add(displayPanel);
        frame.setVisible(true);
    }
}

