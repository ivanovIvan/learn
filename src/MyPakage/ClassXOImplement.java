package MyPakage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;


public class ClassXOImplement {
    private byte[][] dash;    // игровое поле 2 - свободная ячейка
    private byte mySym;     // символ компьютера 0 - нолик, 1 - крестик
    private byte compSym;   // символ противника 0 - нолик, 1 - крестик
    private int n;          // количество ячеек
    private IdentRes winner;// победитель
    private XODraw drawer;  // класс рисования, инициализируется при начале игры
    
    
    public void initialNewGame(int n, Graphics g, Rectangle r)
    {
        // инициализиует поле первоначальными занчениями из полей n
        dash = new byte[n][n];
        for (int i=0; i<n;i++)
        {
            for (int j=0;j<n;j++) dash[i][j]=2;
            
        }
        this.n = n;
        winner = null;
        XODrawPrimitive tempDrawer = new XODrawPrimitive();
        drawer = tempDrawer;
        tempDrawer = null;
        drawer = new XODrawPrimitive();
        drawer.g    = g;
        dash[0][0] = 1;
        dash[0][1] = 0;
        dash[0][2] = 1;
        drawer.dash = dash;
        drawer.rectangl = r;
        drawer.setAttribut(30, 30, (byte)2);
        drawer.drawGrid();
    }
    
    public void clicMouse(int x, int y)
    {
        XODraw.coordYach m = drawer.getCoordYach(x, y);
        if (m==null) System.out.println( "За границей "); 
        else System.out.println("Номер колонки "+m.row+" номер строки "+m.col);   
    }
    
    public void runNextStep()
    {
        // алгоритм обрабатывает следующий ход
        // вначале получаем наши возможные ходы
        ArrayList<IdentRes> ourList = getTabLines(mySym);
        // затем получаем возможные ходы противника
        ArrayList<IdentRes> compList = getTabLines(compSym);
        // проверка на победу одного из игроков
        // вначале проверяем соперника, до нашего хода.. может мы уже впухли
        IdentRes compBestRes = null, ourBestRes = null;
        
        if (!compList.isEmpty()) compBestRes = compList.get(0);
        if (!ourList.isEmpty()) ourBestRes  = ourList.get(0);
        if (compBestRes!=null&&compBestRes.stepOfend==0) winner = compBestRes;
        if (winner ==null&& ourBestRes!=null&&ourBestRes.stepOfend==0) winner = ourBestRes;
        if (winner ==null)
        {
            // явного победителя нет.. делаем следующий ход
            int minOurStep = (ourBestRes!=null)?ourBestRes.stepOfend:n+1;
            int compOurStep = (compBestRes!=null)?compBestRes.stepOfend:n+1;
            IdentRes currentRes;
            if (minOurStep<compOurStep)
            {
                // мы ближе к победи.. двигаем наш крестик
                currentRes = ourBestRes;
                if (ourBestRes.stepOfend==1) winner = ourBestRes;
            }
            else
            {
                // он ближе к победе. надо помешать
                currentRes = compBestRes;
            }
            // сделать следующий шаг и отрисовать
            for (int i=0;i<n;i++)
            {
                if (currentRes.type == eTip.col)
                {
                    if (dash[currentRes.num][i]==2)  
                    {
                        dash[currentRes.num][i]= mySym;
                        break;
                    }
                }
                if (currentRes.type == eTip.row)
                {
                    if (dash[i][currentRes.num]==2)  
                    {
                        dash[i][currentRes.num]= mySym;
                        break;
                    }
                }
                if (currentRes.type == eTip.diag)
                {
                    if (dash[Math.abs(currentRes.num-i)][Math.abs(currentRes.num-i)]==2)
                    {
                        dash[Math.abs(currentRes.num-i)][Math.abs(currentRes.num-i)] = mySym;
                        break;
                    }
                }
            }
        }
        show();
    }
    public void show()
    {
        // метод выводит массив на экран
        //drawer.g = g;
        drawer.paint();
    }
    /**
     *
     * @param g
     */
    public void setGraphics(Graphics g, Rectangle r)
    {
        drawer.g = g;
        drawer.rectangl = r;
    }
    
    public void setSym(byte mySym, byte compSym)
    {
        // метод устанавливает собственный символ и символ соперника
        this.mySym      = mySym;
        this.compSym    = mySym;
    }
    private ArrayList<IdentRes> getTabLines(byte sym)
    {
        // процедура пробегает все возможные варианты и возвращает их отсортированными
        // по степени завершенности, самые близкие к завершению вверху
        ArrayList<IdentRes> rez = new ArrayList<>();
        // строки
        for (int i=0;i<n;i++)
        {
            int colCol = n;    // количество ходов по строкам
            for (int j=0;j<n;j++)
            {
                // вначале проверяем строку
                if ((colCol!=-1)&&(dash[i][j] == 2 || dash[i][j] == sym)) 
                    {colCol= colCol-1;}
                    else {break;}
            }
            // далее добавим в список результаты вычисления
            if (colCol!=-1)
            {
                IdentRes mObj = new IdentRes();
                mObj.type   = eTip.col;
                mObj.num    = i;
                mObj.stepOfend= colCol;
                rez.add(mObj);
            }
            
        }
        // колонки
        for (int i=0;i<n;i++)
        {
            
            int colRow = n;
            for (int j=0;j<n;j++)
            {
                // вначале проверяем строку
                // далее проверяем колонки
                if ((colRow!=-1)&&(dash[j][i] == 2 || dash[j][i] == sym)) 
                    {colRow= colRow-1;}
                    else {break;}
            }
            // далее добавим в список результаты вычисления
            if (colRow!=-1)
            {
                IdentRes mObj = new IdentRes();
                mObj.type   = eTip.row;
                mObj.num    = i;
                mObj.stepOfend= colRow;
                rez.add(mObj);
            }
        }
        // теперь обойдем диагонали
        int diag1 = n, diag2 =n;
        for (int i=0; i<n;i++)
        { 
            if ((diag1!=-1)&&(dash[i][i] == 2 || dash[i][i] == sym)) diag1--;
            else diag1 = -1;
            if ((diag2!=-1)&&(dash[n-i][n-i] == 2 || dash[n-i][n-i] == sym)) diag2--;
            else diag2 = -1;
            if (diag1==-1&& diag2==-1) break;
        }
        if (diag1!=-1)
                {
                 IdentRes mObj = new IdentRes();
                 mObj.type   = eTip.diag;
                 mObj.num    = 1;
                 mObj.stepOfend= diag1;
                 rez.add(mObj);
                }
        if (diag2!=-1)
                {
                 IdentRes mObj = new IdentRes();
                 mObj.type   = eTip.diag;
                 mObj.num    = n;
                 mObj.stepOfend= diag2;
                 rez.add(mObj);
                }
        Collections.sort(rez);
        return rez;
   }
    
}