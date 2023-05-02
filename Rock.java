import java.awt.*;

/**
 * Creates the Rock that will fall from the sky during the Volcano scene
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
abstract class Rock extends Thread
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
    
    //The current speeds for the rock objects
    protected int ySpeed;
    protected int xSpeed;
    
    /**
    Run method to define the life of each Rock object. The object is no longer needed once it reaches the bottom of the screen
     */
    abstract public void run();
}
