import java.awt.*;

/**
 * creates the fast falling rocks
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
public class FastFallingRock extends Rock
{
    
    public FastFallingRock(Component panel, Point topL){
        this.panel = panel;
        this.bottom = panel.getHeight();
        this.done = false;
        this.topL = topL;
        this.ySpeed = 1;
        this.xSpeed = 0;
    }
    
    /**
    This object's run method, which manages the life of the FastFallingRock as it
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
            this.ySpeed += 2;
            panel.repaint();
        }

        done = true;
        panel.repaint();
    }
}
