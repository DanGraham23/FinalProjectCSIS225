import java.awt.*;
import java.util.*;

/**
 * creates falling rocks with strong wind
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
public class WindyRock extends Rock
{
    
    public WindyRock(Component panel, Point topL){
        this.panel = panel;
        this.bottom = panel.getHeight();
        this.done = false;
        this.topL = topL;
        this.ySpeed = 4;
        this.xSpeed = 1;
    }
    
    /**
    This object's run method, which manages the life of the WindyRock as it
    moves down the screen.
     */
    @Override
    public void run(){
        // the run method is what runs in this object's thread for the
        // time it is "alive"

        try {
            sleep(DELAY_TIME);
        }
        catch (InterruptedException e) {
        }

        while (topL.y < bottom){
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }
            topL.translate(xSpeed, ySpeed);
            Random rand = new Random();
            xSpeed += (rand.nextInt(3)-1) * 3;
            panel.repaint();
        }

        done = true;
        panel.repaint();
    }
}
