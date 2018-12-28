import java.awt.event.*;
import javax.swing.event.*;
    public class MyMouseListener extends MouseAdapter
    {
        DrawingPanel _myPanel;        
        //ButtonPanel _myButtonPanel;
        // constructor
        public MyMouseListener(DrawingPanel dp)
        {
            _myPanel = dp;
            //_myButtonPanel = dp;
        }
        public void mouseClicked(MouseEvent e)
        {
            _myPanel.mouseClicked(e);
        }
    }