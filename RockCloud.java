import java.awt.*;
import java.util.*;

/**
 * creat clouds that drop rocks
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
public class RockCloud extends Thread
{

    // total number of rocks to fall
    private int rocks;

    // time between falling rocks
    private static final int ROCK_INTERVAL = 300;

    // the Component where we'll be creating rocks
    private Component panel;

    // list of Rock objects that are the responsibility
    // of this Cloud
    private java.util.List<Rock> rockCloud;

    // are all of this Cloud's rocks started?
    private boolean allStarted;

    // are all of this Cloud's rocks done?
    private boolean done;

    //The type of rock to fall
    private String type;

    // the filename that will be loaded into snowPic
    protected static final String rockPicFileName = "images/rock1.png";
    
    protected static Image rockPic;

    // an object to serve as the lock for thread safety of our list access
    private Object lock = new Object();
    /**
    Construct a new RockCloud, using the given component to pass along
    to its Rock objects that it will create.
    @param panel the Component in which this RockCloud will generate Rock
    objects
     */
    public RockCloud(Component panel, String type, int rocks) {

        this.panel = panel;
        rockCloud = new Vector<Rock>(rocks);
        done = false;
        allStarted = false;
        this.type = type;
        this.rocks = rocks;
    }

    /**
    Run method to define the life of this RockCloud, which consists of
    generating Rock objects for a while.
     */
    @Override
    public void run() {

        Random r = new Random();

        for (int rockCount = 0; rockCount < rocks; rockCount++) {

            // wait a bit before creating the next snowflake
            try {
                sleep(ROCK_INTERVAL);
            }
            catch (InterruptedException e) {
            }

            // random x coordinate
            int x = r.nextInt(panel.getWidth());

            Rock newRock = new SlowFallingRock(panel, new Point(x, 0));
            if (type.equals("slow")){
                newRock = new SlowFallingRock(panel, new Point(x, 0));
            }else if (type.equals("fast")){
                newRock = new FastFallingRock(panel, new Point(x, 0));
            }else if(type.equals("windy")){
                newRock = new WindyRock(panel, new Point(x, 0));
            }

            synchronized (lock){
                rockCloud.add(newRock);
            }

            newRock.start();
        }
        allStarted = true;
    }

    /**
    Pass along a paint to all Rock objects managed by this RockCloud.
    @param g the Graphics object in which to paint
     */
    public void paint(Graphics g) {

        if (done) return;
        synchronized (lock){
            for (Rock curRock : rockCloud) {
                if(!curRock.done){
                    g.drawImage(RockCloud.rockPic, curRock.topL.x, curRock.topL.y, null);
                }
            }
        }

    }

    /**
    Check if this RockCloud's work is done (all of its Rock objects have
    fallen and completed)
    @return true if this RockCloud's work is done
     */
    public boolean done() {

        if (!allStarted) return false;

        if (done) return true;

        synchronized (lock){
            // this Cloud is done if all of its flakes have melted
            for (Rock curRock : rockCloud) {
                if (!curRock.done) return false;
            }
        }

        done = true;
        return true;
    }

    /**
       Set the Image to be used by all Rock objects, to be 
       called by the main method before the GUI gets set up
    */
    public static void loadRockPic() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        RockCloud.rockPic = toolkit.getImage(rockPicFileName);
    }
}
