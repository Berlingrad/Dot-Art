/** Drawing Panel displays the circles the user has created via mouse clicks
 * 
 * @author (Greg Johnson) 
 * @version (November 1, 2017)
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.awt.Color;
import java.io.*;

public class DrawingPanel extends JPanel 
{
    SmartEllipse _circle = null;
    private ArrayList<SmartEllipse>  _container;
    private int _red, _green, _blue;
    JButton save, open;
    
    /**
     * Constructor for objects of class ObjectPanel
     */
    public DrawingPanel()
    {

        super();

        this.setLayout(new BorderLayout());
        JPanel ButtonPanel = new JPanel();
        
        open = new JButton("open");
        save = new JButton("save");
        open.addActionListener(new MyActionListener());
        save.addActionListener(new MyActionListener());
        
        this.add(ButtonPanel, BorderLayout.PAGE_END);
        ButtonPanel.add(save);
        ButtonPanel.add(open);
        
        
        
        _container = new ArrayList<SmartEllipse>();
        
        MyMouseListener myMouseListener = new MyMouseListener(this);
        SaveKey mySaveKey = new SaveKey();
        OpenKey myOpenKey = new OpenKey();
        
        
        // set up the display on the panel
        this.setBackground(Color.WHITE);

        // add the panel as a MouseListener
        this.addMouseListener(myMouseListener);
        this.addKeyListener(mySaveKey);
        this.addKeyListener(myOpenKey);
    }
   
    /**
     * The paintComponent method of class DrawingPanel is responsible for painting
     * the panel any time there is a change in the panel.
     */
    public void paintComponent(Graphics aBrush)
    {
        super.paintComponent(aBrush);
        Graphics2D aBetterBrush = (Graphics2D)aBrush;
        
	//_circle.fill(aBetterBrush);
	for (SmartEllipse c : _container) {
	    if (c != null) {
                c.fill(aBetterBrush);
            }
        }
    }
     
    /**
     * The mouseClicked method will create a new SmartEllipse
     * and (eventually) add it to the ArrayList of dots in the drawing area
     */
     public void mouseClicked(java.awt.event.MouseEvent e)
     {
        Point currentPoint;
        // get the current mouse location
        currentPoint = e.getPoint();
        _red = (int) (Math.floor(Math.random()*255));
        _green = (int) (Math.floor(Math.random()*255));
        _blue = (int) (Math.floor(Math.random()*255));
        
        		//create a new circle and put draw it on the panel
	        _circle = new SmartEllipse(new java.awt.Color(_red, _green, _blue));
		_circle.setSize(20,20);							//dot on screen is 20 by 20 pixels
		_circle.setLocation(currentPoint.x-10, currentPoint.y-10);  //subtracting 10 from x and y centers the circle on the mouse click
		repaint();
		
		_container.add(_circle);
		
		
    }   
    
    
    private class MyActionListener implements ActionListener{
        public MyActionListener() {}
        public void actionPerformed(ActionEvent e) {
                //System.out.println(e.getSource());
                JFileChooser fc = new JFileChooser();
                if (e.getSource() == open) {
                    
                    int returnVal = fc.showOpenDialog(DrawingPanel.this);
                    
                    
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        try {
                            //Scanner scanner = new Scanner(file);
                            ObjectInputStream istream = new ObjectInputStream (new FileInputStream(file));
                            _container.removeAll(_container);
                            try {
                                for (;;) {
                                    SmartEllipse c = (SmartEllipse) istream.readObject();
                                    _container.add(c);
                                }
                            } catch (EOFException exc) {
                             
                            } catch (IOException exc) {
                                exc.printStackTrace();
                            }
                            
                            istream.close();
                            repaint();
                        }
                        catch (Exception error) {
                            error.printStackTrace();
                        }
                        //log.append("Opening: " + file.getName() + "." + newline);
                    }
                    
                } else if (e.getSource() == save) {
                    try {
                        int returnVal = fc.showSaveDialog(DrawingPanel.this);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file=fc.getSelectedFile();
                            ObjectOutputStream ostream = new ObjectOutputStream (new FileOutputStream(file));
                            for (SmartEllipse c : _container) {
                                 ostream.writeObject(c);
                             
                            }
                            ostream.close();
                        }
                    } catch (Exception error) {
                        System.out.println("Write failed"); 
                    }
                        
                }
        }
    }
    
    public class SaveKey implements KeyListener {
 
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        
         if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
               JFileChooser fc = new JFileChooser();
               try {
                    int returnVal = fc.showSaveDialog(DrawingPanel.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                         File file=fc.getSelectedFile();
                         ObjectOutputStream ostream = new ObjectOutputStream (new FileOutputStream(file));
                        for (SmartEllipse c : _container) {
                             ostream.writeObject(c);
                             
                        }
                        ostream.close();
                    }
               } catch (Exception error) {
                        System.out.println("Write failed"); 
               }       
         }
    }

    @Override
    public void keyReleased(KeyEvent e) {}     
    }
    
    
    public class OpenKey implements KeyListener {
 
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(DrawingPanel.this);
        if ((e.getKeyCode() == KeyEvent.VK_O) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
              File file = fc.getSelectedFile();
              try {
                            //Scanner scanner = new Scanner(file);
                   ObjectInputStream istream = new ObjectInputStream (new FileInputStream(file));
                   _container.removeAll(_container);
                   try {
                       for (;;) {
                                SmartEllipse c = (SmartEllipse) istream.readObject();
                                _container.add(c);
                       }
                   } catch (EOFException exc) {
                             
                   } catch (IOException exc) {
                            exc.printStackTrace();
                   }
                            
                   istream.close();
                   repaint();
              }
                   catch (Exception error) {
                        error.printStackTrace();
              }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}     
    }
}
