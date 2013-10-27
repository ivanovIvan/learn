package MyPakage;


import MyPakage.XODraw.*;
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */
class XODrawPrimitive extends XODraw {
    
    @Override
    void drawGrid()
    {
        // рисуем сетку по количеству симоволо
        //Rectangle bound = g.getClipBounds();
        int n = dash.length;
        int mHeigth = (int)rectangl.height/n;
        int mWidth = (int)rectangl.width/n;
        g.setColor(Color.BLACK);
        for (int i=0;i<=n;i++)
        {
            g.drawLine(i*mWidth,0+borderTop , i*mWidth, rectangl.height-borderBottom);
            g.drawLine(0+borderLeft,i*mHeigth, rectangl.width-borderRight, i*mHeigth);
        }
        //System.out.println("!!Отрисован "+g.getColor().toString());
    }
    @Override
    void drawX(Graphics g,int col, int row)
    {
        //метод реализует рисование Х
    }
    @Override
    void drawX(Graphics g,int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    }
    @Override
    void drawO(Graphics g,int col, int row)
    {
        //метод реализует рисование Х
        
    }
    @Override
    void drawO(Graphics g,int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    } 
}
