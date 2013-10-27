
import static XODraw.g;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */
class XODrawPrimitive extends XODraw {
    static void drawGrid(int n)
    {
        // рисуем сетку по количеству симоволо
        Rectangle bound = g.getClipBounds();
        int mHeigth = (int)bound.height/n;
        int mWidth = (int)bound.width/n;
        for (int i=0;i<=n;i++)
        {
            g.drawLine(i*mWidth,0+borderTop , i*mWidth, bound.height-borderBottom);
            g.drawLine(0+borderLeft,i*mHeigth, bound.width-borderRight, i*mHeigth);
        }
    }
    static void drawX(Graphics g,int col, int row)
    {
        //метод реализует рисование Х
    }
    static void drawX(Graphics g,int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    }
    static void drawO(Graphics g,int col, int row)
    {
        //метод реализует рисование Х
        
    }
    static void drawO(Graphics g,int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    } 
}
