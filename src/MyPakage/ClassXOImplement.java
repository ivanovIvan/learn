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
        //tempDrawer = null;
        drawer = tempDrawer;
        drawer.g    = g;
        /*dash[0][0] = 1;
        dash[0][1] = 0;
        dash[0][2] = 1;*/
        drawer.dash = dash;
        drawer.rectangl = r;
        drawer.setAttribut(30, 30, (byte)2);
        mySym = 1;
        compSym = 0;
        drawer.drawGrid();
    }
    
    public void clicMouse(int x, int y)
    {
        if (winner ==null)
        {
            XODraw.coordYach m = drawer.getCoordYach(x, y);
            if (m!=null) 
            {
                byte i = dash[m.col][m.row];
                if (i==2) {
                    dash[m.col][m.row] = compSym;
                    drawer.drawCell(m.col, m.row, true);
                    runNextStep();
                    //drawer.dash = dash;
                }
            } 
        }
        
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
        if (winner ==null&&(ourBestRes!=null||compBestRes!=null))
        {
            int tecCol = -1, tecRow=-1;
            // явного победителя нет.. делаем следующий ход
            int minOurStep = (ourBestRes!=null)?ourBestRes.stepOfend:n+1;
            int compOurStep = (compBestRes!=null)?compBestRes.stepOfend:n+1;
            IdentRes currentRes;
            if (minOurStep<=compOurStep)
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
                        tecCol = currentRes.num;
                        tecRow = i;
                        break;
                    }
                }
                if (currentRes.type == eTip.row)
                {
                    if (dash[i][currentRes.num]==2)  
                    {
                        dash[i][currentRes.num]= mySym;
                        tecCol = i;
                        tecRow = currentRes.num;
                        break;
                    }
                }
                if (currentRes.type == eTip.diag)
                {
                    if (dash[i][Math.abs(currentRes.num-i)]==2)
                    {
                        dash[i][Math.abs(currentRes.num-i)] = mySym;
                        tecCol = i;
                        tecRow = Math.abs(currentRes.num-i);
                        break;
                    }
                }
            }
        if (tecCol>=0&&tecRow>=0) drawer.drawCell(tecCol,tecRow,true);
        }
        else
        {
            // ничья
            System.out.println("Ничья");
        }
    }
    public void show()
    {
        // метод выводит массив на экран
        //drawer.g = g;
        if (winner!=null) drawer.drawWinner(winner);
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
        this.compSym    = compSym;
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
                if ((colCol!=-1)&&(dash[i][j] == sym ||dash[i][j]==2)) 
                    { if (dash[i][j]==sym) colCol= colCol-1;}
                    else {
                    colCol = -1;
                    break;}
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
                if ((colRow!=-1)&&(dash[j][i] ==sym||dash[j][i]==2)) 
                    {if (dash[j][i]==sym) colRow--; }
                    else {
                    colRow = -1;
                    break;}
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
            if ((diag1!=-1)&&(dash[i][i] == 2 || dash[i][i] == sym)) 
                { if(dash[i][i] == sym) diag1--; }
            else diag1 = -1;
            if ((diag2!=-1)&&(dash[i][n-i-1] == 2 || dash[i][n-i-1] == sym)) 
                { if (dash[i][n-i-1] == sym) diag2--; }
            else diag2 = -1;
            if (diag1==-1&& diag2==-1) break;
        }
        if (diag1!=-1)
                {
                 IdentRes mObj = new IdentRes();
                 mObj.type   = eTip.diag;
                 mObj.num    = 0;
                 mObj.stepOfend= diag1;
                 rez.add(mObj);
                }
        if (diag2!=-1)
                {
                 IdentRes mObj = new IdentRes();
                 mObj.type   = eTip.diag;
                 mObj.num    = n-1;
                 mObj.stepOfend= diag2;
                 rez.add(mObj);
                }
        Collections.sort(rez);
        return rez;
   }
   public void test_draw()
   {
       drawer.drawX(0, 0, true);
       //drawer.dash[0][0] = 1;
   }
}