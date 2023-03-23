import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Creates the Rock that will fall from the sky during the Volcano scene
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
abstract public class Rock extends Thread
{
    //Delay between screen updates
    public static final int DELAY_TIME = 33;

    //Panel to paint Rock objects to
    protected Component panel;

    //The bottom of the screen
    protected int bottom;
    
    //Determine if the current object should no longer be painted
    protected boolean done;

    //The current top left point
    protected Point topL;
    
    // the filename that will be loaded into snowPic
    protected static final String rockPicFileName = "images/rock1.png";
    
    protected static Image rockPic;
    /**
    Run method to define the life of each Rock object. The object is no longer needed once it reaches the bottom of the screen
     */
    @Override
    abstract public void run();

    /**
    Paint method to draw the current Rock object to the screen
     */
    abstract public void paint(Graphics g);
    
    /**
    Done method to determine when the current Rock should no longer be drawn to the screen
     */
    public boolean done(){
        return done;
    }
    
    /**
       Set the Image to be used by all Rock objects, to be 
       called by the main method before the GUI gets set up
    */
    public static void loadRockPic() {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Rock.rockPic = toolkit.getImage(rockPicFileName);
    }
}
