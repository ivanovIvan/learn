
import java.awt.Graphics;

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
    
    static void setAttribut(int pborderLeft,int pborderRight, int pborderTop, int pborderBottom,byte ppixelLine)
    {
        // устанавливает параметры и перерисовывает объект
        borderLeft  = pborderLeft;
        borderRight = pborderRight;
        borderTop   = pborderTop;
        borderBottom= pborderBottom;
        pixelLine   = ppixelLine;
        paint(g,dash);
    }
    static void drawGrid(int n)
    {
        // рисуем сетку по количеству симоволо
        
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
    static void paint()
    {
        // метод выводит таблицу
        drawGrid(dash.length);
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

