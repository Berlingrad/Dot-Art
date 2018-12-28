/**
 * 2102: DotArt class is the main program.
 * 
 * @author (Greg Johnson) 
 * @version (November 1,2017)
 */
import java.awt.*;
import javax.swing.*;

public class DotArt extends JFrame
{   
    DrawingPanel _theDrawingPanel;
    /**
     * Constructor for objects of class MyApp
     */
    public DotArt(String title)
    {
        super(title);
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        // create and add Panels to the Frame
        _theDrawingPanel = new DrawingPanel();
        this.add(_theDrawingPanel); 
        this.setVisible(true);      
    }

    /**
     * The mainline program.
     */
    public static void main(String[] args)
    {
        DotArt da = new DotArt("2102 Dot Art");
    }
}
