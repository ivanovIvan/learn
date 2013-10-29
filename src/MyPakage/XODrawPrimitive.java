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
    class DrawPrimitiveImplement 
    {
        // класс рисующий примитивы
        public void drawLine(Graphics g,int x1, int y1, int x2, int y2, int delay)
        {
            //метода рисующий линию с определенной задержкой
            double deltaX = x2-x1;
            double deltaY = y2-y1;
            double c = Math.tan(deltaY/deltaX);
            for (int i = x1+1; i<=x2; i++)
            {
                double y = c*i;
                g.drawLine(i-1, (int)c*(i-1),i,(int)y);
                try {
                    Thread.sleep(delay);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                
            }
        }
    }
    @Override
    void drawGrid()
    {
        // рисуем сетку по количеству симоволо
        //Rectangle bound = g.getClipBounds();
        int n = dash.length;
        int mHeigth = (int)(rectangl.height-2*borderHeigth)/n;
        int mWidth = (int)(rectangl.width-2*borderHeigth)/n;
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        for (int i=0;i<=n;i++)
        {
            //int k = (i==0)?1:(i==n)?-1:0;
            for (byte l = 0;l<pixelLine;l++)
            {
                g.drawLine(i*mWidth+borderWidth+l,0+borderHeigth , i*mWidth+borderWidth+l, rectangl.height-borderHeigth);
                g.drawLine(0+borderWidth,i*mHeigth+borderHeigth+l, rectangl.width-borderWidth, i*mHeigth+borderHeigth+l);
            }
        }
        g.setColor(tempColor);
        //System.out.println("!!Отрисован "+g.getColor().toString());
    }
    @Override
    void drawX(int col, int row)
    {
        //метод реализует рисование Х
/*        int n = dash.length;
        int mHeigth = (int)(rectangl.height-2*borderHeigth)/n;
        int mWidth = (int)(rectangl.width-2*borderHeigth)/n;*/
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        // вычислим координаты
        /*int colX = mWidth*col+borderWidth;
        int rowY = mHeigth*row+borderHeigth;*/
        paramYach m = getParamYach(col, row);
        
        for (byte l = 0; l <pixelLine+1;l++)
        {
            // слева направо
            g.drawLine(m.x+(int)m.width/10+l, m.y+(int)m.heigth/10,m.x-(int)m.width/10+l+m.width , m.y-(int)m.heigth/10+m.heigth);
            // справа налево
            g.drawLine(m.x+(int)m.width/10+l, m.y-(int)m.heigth/10+m.heigth,m.x-(int)m.width/10+l+m.width , m.y+(int)m.heigth/10);
            
        }
        g.setColor(tempColor);
        
    }
    @Override
    void drawX(int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        drawX(col, row);
    }
    @Override
    void drawO(int col, int row)
    {
        //метод реализует рисование Х
        /*int n = dash.length;
        int mHeigth = (int)(rectangl.height-2*borderHeigth)/n;
        int mWidth = (int)(rectangl.width-2*borderHeigth)/n;*/
        Color tempColor = g.getColor();
        g.setColor(Color.BLACK);
        // вычислим координаты
        /*int colX = mWidth*col+borderWidth;
        int rowY = mHeigth*row+borderHeigth;*/
        paramYach m = getParamYach(col, row);
        
        for (byte l = 0; l <pixelLine+1;l++)
        {
            // слева направо
            g.drawOval(m.x+(int)m.width/10+l, m.y+(int)m.heigth/10+l, (int)(m.width*0.8-l*2), (int)(m.heigth*0.8-l*2));
        }
        g.setColor(tempColor);
        
    }
    @Override
    void drawO(int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        drawO(col, row);
    } 
}
