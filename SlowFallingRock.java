import java.awt.*;

/**
 * creates slow falling rocks
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
public class SlowFallingRock extends Rock
{
    
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
        this.ySpeed = 4;
        this.xSpeed = 0;
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
            topL.translate(0, this.ySpeed);
            panel.repaint();
        }

        done = true;
        panel.repaint();
    }
    
}
