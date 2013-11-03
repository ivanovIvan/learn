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
    protected int borderWidth,borderHeigth;
    protected byte pixelLine;
    public byte dash[][];
    public Graphics g;
    public Rectangle rectangl;
    protected int mDelay;// задержка в милисекундах
    class paramYach 
    {
        int x,y,width,heigth;
    }
    
    class coordYach {
        int col,row;//col - строка; row - колонка
        
    }
    paramYach getParamYach(int col, int row)
    {
        // метод возвращает координаты ячейки для переданного номера строки и колонки
        paramYach rez = new paramYach();
        int n = dash.length;
        rez.heigth = (int)(rectangl.height-2*borderHeigth)/n;
        rez.width = (int)(rectangl.width-2*borderHeigth)/n;
        // вычислим координаты
        rez.x = rez.width*row+borderWidth;
        rez.y = rez.heigth*col+borderHeigth;
        return rez;
    }
    
    coordYach getCoordYach(int x, int y)
    {
        // метод возвращает номер строки и колонки для щелчка мыши
        int n = dash.length;
        int heigth = (int)(rectangl.height-2*borderHeigth)/n;
        int width = (int)(rectangl.width-2*borderHeigth)/n;
        coordYach rez = null;
        if (x>=borderWidth&& x<=(width*n+borderWidth)&&y>=borderHeigth&&y<=(heigth*n+borderHeigth))  
                {
                    int row = (int)((x-borderWidth)/width);
                    int col = (int)((y-borderHeigth)/heigth);
                    if (col<=n && row <=n) 
                    {
                        rez = new coordYach();
                        rez.col = col;
                        rez.row = row;
                    }
                    
                }
        return rez;
    }
        
    void drawWinner(IdentRes winner)
    {
        // отображаем победителя
        
    }
    void setAttribut(int pborderLeft,int pborderRight, byte ppixelLine)
    {
        // устанавливает параметры и перерисовывает объект
        borderWidth  = pborderLeft;
        borderHeigth = pborderRight;
        pixelLine   = ppixelLine;
        paint();
    }
    void drawGrid()
    {
        // рисуем сетку по количеству симоволо
        
    }
    void drawX(int col, int row)
    {
        //метод реализует рисование Х
    }
    void drawX(int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        
    }
    void drawO(int col, int row)
    {
        //метод реализует рисование Х
        
    }
    void drawO(int col, int row, boolean first)
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
                if (dash[i][j]==0) drawO(i, j);
                else if (dash[i][j]==1) drawX(i,j);
            }
        }
    }
    void drawCell(int col, int row, boolean first)
    {
        byte i = dash[col][row];
        if (first)
            {
                if (i==1) drawX(col,row);
                else drawO(col,row,first);
            }
        else {
                if (i==1) drawX(col,row);
                else drawO(col,row);
            
        }
    }

    public XODraw() {
        mDelay = 3;
    }
    
}

