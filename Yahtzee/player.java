import javax.swing.*;

public class player {
    
    config myConfig = new config();
    dice myDice = new dice();
    JLabel label;

    public void makeKeep(Character[] keep){ //makes the default keep array
        for(int i = 0; i < myConfig.getDice(); i++){
            keep[i] = 'n';
        }
    }

    //makes an array that is filled with ys. this is used as a comparison tool
    public void makeFull(Character[] fullKeep){
        for(int i = 0; i < myConfig.getDice(); i++){
            fullKeep[i] = 'y';
        }
    }
    
}
