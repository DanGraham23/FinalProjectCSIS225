import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;

/**
 * creates slow falling rocks
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
public class SlowFallingRock extends Rock
{
    //ySpeed for the falling Rock
    private int ySpeed = 4;
    
    
    
    /**
    Construct a new SlowFallingRock object.
    @param container the Swing component in which this Rock is being
    drawn to allow it to call that component's repaint method
    @param startX the starting x value for the falling Rock
     */
    public SlowFallingRock(Component panel, Point topL){
        this.panel = panel;
        this.bottom = panel.getHeight();
        this.done = false;
        this.topL = topL;
    }

    /**
    This object's run method, which manages the life of the SlowFallingRock as it
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
            topL.translate(0, ySpeed);
            panel.repaint();
        }

        done = true;
        panel.repaint();
    }

    /**
    This object's paint method. Paints the Rock to the screen.
    @param g the graphics object to be painted to.
     */
    @Override
    public void paint(Graphics g){

        g.setColor(Color.GRAY);

        if(!done){
            g.drawImage(rockPic, topL.x, topL.y, null);
        }

    }
    
}
