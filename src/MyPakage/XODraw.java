package MyPakage;


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
abstract class XODraw {
    // бордюры
    static protected int borderLeft,borderRight,borderTop, borderBottom;
    static protected byte pixelLine;
    static public byte dash[][];
    static public Graphics g;
    static public Rectangle rectangl;
    
    void setAttribut(int pborderLeft,int pborderRight, int pborderTop, int pborderBottom,byte ppixelLine)
    {
        // устанавливает параметры и перерисовывает объект
        borderLeft  = pborderLeft;
        borderRight = pborderRight;
        borderTop   = pborderTop;
        borderBottom= pborderBottom;
        pixelLine   = ppixelLine;
        paint();
    }
    void drawGrid()
    {
        // рисуем сетку по количеству симоволо
        
    }
    void drawX(Graphics g,int col, int row)
    {
        //метод реализует рисование Х
    }
    void drawX(Graphics g,int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    }
    void drawO(Graphics g,int col, int row)
    {
        //метод реализует рисование Х
        
    }
    void drawO(Graphics g,int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    }
    void paint()
    {
        // метод выводит таблицу
        drawGrid();
        for (int i=0;i<dash.length;i++)
        {
            for (int j=0;j<dash.length;j++)
            {
                if (dash[i][j]==0) drawO(g,i, j);
                else if (dash[i][j]==1) drawX(g,i,j);
            }
        }
    }
}

