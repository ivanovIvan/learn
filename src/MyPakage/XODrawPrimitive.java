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
    void drawWinner(IdentRes winner) {
        //super.drawWinner(winner); //To change body of generated methods, choose Tools | Templates.
        int n = dash.length;
        if (winner.type == eTip.diag)
        {
            paramYach i1 ;
            paramYach i2 ;
            if (winner.num ==(n-1))
            {
                i1 = getParamYach(0, n);
                i2 = getParamYach(n, 0);
            }
            else
            {
                i1 = getParamYach(0, 0);
                i2 = getParamYach(n, n);
            }
            for (byte l = 0; l<pixelLine;l++)
            {
                g.drawLine(i1.x+l, i1.y , i2.x+l, i2.y);
            }
        }
        else if (winner.type == eTip.col)
        {
            paramYach i = getParamYach(winner.num, 0);
            // проведем линию посредине
            for (byte l=0;l<pixelLine;l++)
            {
                g.drawLine(i.x,i.y+(int)(i.heigth/2)+l, i.x+(int)i.width*n, i.y+(int)(i.heigth/2)+l);
            }
        }
        else
        {
            paramYach i = getParamYach(0,winner.num);
            // проведем линию посредине
            for (byte l=0;l<pixelLine;l++)
            {
                g.drawLine(i.x+(int)i.width/2+l,i.y, i.x+(int)i.width/2+l, i.y+(int)(i.heigth*n));
            }
        }

    }
    static class DrawPrimitiveImplement 
    {
        // класс рисующий примитивы
        static public void drawLine(Graphics g,int x1, int y1, int x2, int y2, int delay,byte pixelLine)
        {
            //метода рисующий линию с определенной задержкой
            double deltaX = x2-x1;
            double deltaY = y2-y1;
            double c = (double)deltaY/deltaX;
            for (int i = 1; i<=deltaX; i++)
            {
                double y = c*i;
                /*int x11 = x1+i-1;
                int y11 = y1+(int)(c*(i-1));
                int x21 = x1+i;
                int y21 = y1+(int)y;*/
                //g.
                //pixelLine =1;
                for (byte l = 0;l<pixelLine; l++)
                {
                    g.drawLine(x1+i-1+l, y1+(int)(c*(i-1)),x1+i+l,y1+(int)y);
                }
                try {
                    Thread.sleep(delay);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                
            }
        }
        static public void drawCircle(Graphics g, int leftX, int leftY, int width, int heigth, int delay, byte pixelLine)
        {
            // вначале вычислим центр и радиус окружности
            int x = leftX+(int)(width/2);
            int y = leftY+(int)(heigth/2);
            double radiusWidth = (int)(width/2);
            double radiusHeigth = (int)(heigth/2);
            double deltaR      = (radiusHeigth-radiusWidth)/90;
            // пока упрощаем и рисуем только круг. не овал
            //pixelLine = 1;
            double tekRadius;
            double tekKoef;
            for (int i = 0;i<360; i++)
            {
                for (int l=0;l<pixelLine;l++)
                {
                    if ((i>0&&i<90)||(i>180&&i<270)) {
                        tekKoef = 1;
                        tekRadius = (radiusWidth+(deltaR*(i%90))*tekKoef);
                    }
                    else {
                        tekKoef =-1;
                        tekRadius = (radiusHeigth+(deltaR*(i%90))*tekKoef);
                    }
                    int deltaX = (int)((tekRadius-l)*Math.cos(Math.toRadians(i)));
                    int deltaY = (int)((tekRadius-l)*Math.sin(Math.toRadians(i)));
                    g.drawLine(x+deltaX, y+deltaY,x+deltaX, y+deltaY);
                    try {
                        Thread.sleep(delay);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    
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
        DrawPrimitiveImplement.drawLine(g,m.x+(int)m.width/10, m.y+(int)m.heigth/10,m.x-(int)m.width/10+m.width , m.y-(int)m.heigth/10+m.heigth,0,pixelLine);
        DrawPrimitiveImplement.drawLine(g,m.x+(int)m.width/10, m.y-(int)m.heigth/10+m.heigth,m.x-(int)m.width/10+m.width , m.y+(int)m.heigth/10,0,pixelLine);
        
//        for (byte l = 0; l <pixelLine+1;l++)
//        {
//            // слева направо
//            g.drawLine(m.x+(int)m.width/10+l, m.y+(int)m.heigth/10,m.x-(int)m.width/10+l+m.width , m.y-(int)m.heigth/10+m.heigth);
//            // справа налево
//            g.drawLine(m.x+(int)m.width/10+l, m.y-(int)m.heigth/10+m.heigth,m.x-(int)m.width/10+l+m.width , m.y+(int)m.heigth/10);
//            
//        }
        g.setColor(tempColor);
        
    }
    @Override
    void drawX(int col, int row, boolean first)
    {
        // метод реализует рисование Х первый раз
        if (first)
        {
            Color tempColor = g.getColor();
            g.setColor(Color.BLACK);
            paramYach m = getParamYach(col, row);

//            for (byte l = 0; l <pixelLine+1;l++)
//            {
                // слева направо
                DrawPrimitiveImplement.drawLine(g,m.x+(int)m.width/10, m.y+(int)m.heigth/10,m.x-(int)m.width/10+m.width , m.y-(int)m.heigth/10+m.heigth,mDelay,pixelLine);
//                g.drawLine(m.x+(int)m.width/10+l, m.y+(int)m.heigth/10,m.x-(int)m.width/10+l+m.width , m.y-(int)m.heigth/10+m.heigth);
                // справа налево
                DrawPrimitiveImplement.drawLine(g,m.x+(int)m.width/10, m.y-(int)m.heigth/10+m.heigth,m.x-(int)m.width/10+m.width , m.y+(int)m.heigth/10,mDelay,pixelLine);
//                g.drawLine(m.x+(int)m.width/10+l, m.y-(int)m.heigth/10+m.heigth,m.x-(int)m.width/10+l+m.width , m.y+(int)m.heigth/10);

  //          }
            g.setColor(tempColor);
            
        }
        else drawX(col, row);
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
        if (first)
        {
            Color tempColor = g.getColor();
            g.setColor(Color.BLACK);
            paramYach m = getParamYach(col, row);
            DrawPrimitiveImplement.drawCircle(g, m.x+(int)m.width/10, m.y+(int)m.heigth/10, (int)(m.width*0.8), (int)(m.heigth*0.8), mDelay, pixelLine);
            g.setColor(tempColor);
            
        }
        else drawO(col, row);
    } 
    
}
