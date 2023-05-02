import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Creates an animated valcano scene with several different modes of destruction
 *
 * @author Ammar Malik, Amal Thomas, Daniel Graham, Bailey Cross, Aimen HARIZI
 * @version 
 */
public class Volcano extends MouseAdapter implements Runnable, ActionListener
{

    //Main JPanel for scene
    private JPanel panel;

    //JComboBox for the different modes
    private JComboBox modes;

    //JButton for the background modes
    private JButton apocalypse;
    private boolean apoc;
    private JButton sunny;

    //List of ash objects to keep track of
    private java.util.List<RockCloud> list;

    //Check if the first thread was started
    private boolean exploded = false;

    // the filename that will be loaded into smokePic
    private static final String smokePicFileName = "images/explosion.png";

    private static Image smokePic;

    // the filename that will be loaded into happyPic
    private static final String happyPicFileName = "images/happypeople.png";

    private static Image happyPic;
    
    // the filename that will be loaded into scaredPic
    private static final String scaredPicFileName = "images/scared.png";

    private static Image scaredPic;
    
    // the filename that will be loaded into boomingPic
    private static final String boomingPicFileName = "images/booming.png";

    private static Image boomingPic;

    // an object to serve as the lock for thread safety of our list access
    private Object lock = new Object();

    /**
    Method to redraw the Volcano scene in the graphics panel.
    @param g the Graphics object in which to paint
     */
    protected void redrawScene(Graphics g) {

        if (apoc == true){
            //Draw the ground
            g.setColor(Color.BLACK);
            g.fillRect(0, 375, 500, 125);

            //Draw the sky
            g.setColor(Color.RED);
            g.fillRect(0, 0, 500, 375);

            //Draw some clouds
            g.setColor(Color.DARK_GRAY);

            g.fillOval(0,0, 75, 100);
            g.fillOval(50, 0, 75, 100);
            g.fillOval(100, 0, 75, 100);

            g.fillOval(275, 0, 75, 100);
            g.fillOval(325, 0, 75, 100);
            g.fillOval(375, 0, 75, 100);

            if (exploded){
                g.drawImage(smokePic, 150, 110, null);
                g.setColor(new Color(255,69,0));
                g.fillRect(225, 200, 25, 10);
            }
            
            //Draw the Volcano
            //Set to brown color
            g.setColor(new Color(102, 51, 0));
            //Draw the Volcano with determined points
            g.fillPolygon(new int[]{100, 200, 225, 250, 275, 375 }, new int[]{375, 250, 200, 200, 250, 375}, 6);

            g.drawImage(scaredPic, 30, 300, null);
            g.drawImage(boomingPic, 300, 300, null);
            if (exploded){
                g.setColor(new Color(255,69,0));
                g.fillRect(225, 200, 25, 10);
            }
        }else{
            //Draw the ground
            g.setColor(Color.GREEN);
            g.fillRect(0, 375, 500, 125);

            //Draw the sky
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, 500, 375);
            //Draw the sun and sunlines
            g.setColor(Color.YELLOW);
            g.fillOval(450, 0, 50, 50);
            g.drawLine(410, 40, 440, 30);
            g.drawLine(410, 10, 440, 10);
            g.drawLine(425, 65, 450, 45);
            g.drawLine(445, 85, 470, 55);

            if (exploded){
                g.drawImage(smokePic, 150, 110, null);
                g.setColor(new Color(255,69,0));
                g.fillRect(225, 200, 25, 10);
            }

            //Draw the Volcano
            //Set to brown color
            g.setColor(new Color(102, 51, 0));
            //Draw the Volcano with determined points
            g.fillPolygon(new int[]{100, 200, 225, 250, 275, 375 }, new int[]{375, 250, 200, 200, 250, 375}, 6);

            g.drawImage(happyPic, 30, 300, null);
            if (exploded){
                g.setColor(new Color(255,69,0));
                g.fillRect(225, 200, 25, 10);
            }
        }

    }

    /**
    The run method to set up the graphical user interface
     */
    @Override
    public void run() {

        // set up the GUI "look and feel" which should match
        // the OS on which we are running
        JFrame.setDefaultLookAndFeelDecorated(true);

        // create a JFrame in which we will build our very
        // tiny GUI, and give the window a name
        JFrame frame = new JFrame("Volcano");
        frame.setPreferredSize(new Dimension(500,500));

        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel with a paintComponent method
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {

                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);

                // redraw our main scene
                redrawScene(g);

                // redraw each ash's contents
                // remove the ones that are done

                int i = 0;
                while (i < list.size()) {
                    synchronized (lock){
                        RockCloud curRock = list.get(i);
                        if (curRock.done()) {
                            list.remove(i);
                        }
                        else {
                            curRock.paint(g);
                            i++;
                        }
                    }
                }

            }
        };

        frame.add(panel);
        panel.addMouseListener(this);

        //Create and add the modes JComboBox
        modes = new JComboBox();
        modes.addItem("Casual");
        modes.addItem("Intermediate");
        modes.addItem("Extreme");
        panel.add(modes);

        //Create the apocalypse option
        apocalypse = new JButton("Apocalypse");
        apocalypse.addActionListener(this);
        panel.add(apocalypse);

        //Create the sunny option
        sunny = new JButton("Sunny");
        sunny.addActionListener(this);
        sunny.setVisible(false);
        panel.add(sunny);
        //Initialize the list of Ash objects
        list = new ArrayList<RockCloud>();

        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    /**
    Mouse press event handler to create a new Ash object that will
    then start generating falling ash onto the scene
    @param e mouse event info
     */
    @Override
    public void mousePressed(MouseEvent e) {
        RockCloud newRock;

        //Create ash object and add to the list of ash objects
        if (modes.getSelectedItem().equals("Casual")){
            newRock = new RockCloud(panel, "slow", 30);
        }else if (modes.getSelectedItem().equals("Intermediate")){
            newRock = new RockCloud(panel, "fast", 60);
        }else{
            newRock = new RockCloud(panel, "windy", 20);
        }
        exploded = true;
        synchronized (lock){
            list.add(newRock);
        }
        newRock.start();
        //panel.repaint();
    }

    /**
    Checks which button in the panel has been pressed
    @param e the current button press event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == apocalypse){
            apoc = true;
            apocalypse.setVisible(false);
            sunny.setVisible(true);
            panel.repaint();
        }else if (e.getSource() == sunny){
            apoc = false;
            apocalypse.setVisible(true);
            sunny.setVisible(false);
            panel.repaint();
        }
    }

    /**
    Set the Images to be used by Volcano objects, to be 
    called by the main method before the GUI gets set up
     */
    public static void loadPics() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Volcano.smokePic = toolkit.getImage(smokePicFileName);
        Volcano.happyPic = toolkit.getImage(happyPicFileName);
        Volcano.scaredPic = toolkit.getImage(scaredPicFileName);
        Volcano.boomingPic = toolkit.getImage(boomingPicFileName);
    }



    public static void main(String args[]) {

        RockCloud.loadRockPic();
        Volcano.loadPics();
        
        // launch the main thread that will manage the GUI
        javax.swing.SwingUtilities.invokeLater(new Volcano());
    }
}
