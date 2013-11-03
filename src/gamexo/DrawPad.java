/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamexo;
import MyPakage.ClassXOImplement;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 *
 * @author dav
 */
public class DrawPad extends JComponent implements MouseListener{

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        if (mObj!=null)
        {
            mObj.setGraphics(g, this.getBounds());
            mObj.show();
        }
    }
    ClassXOImplement mObj;
    
    public void DrawPad(){
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (mObj!=null&&e.getButton() == MouseEvent.BUTTON1)
        {
            mObj.clicMouse(e.getX(), e.getY());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
