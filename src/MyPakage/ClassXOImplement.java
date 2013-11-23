package MyPakage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JOptionPane;


public class ClassXOImplement {
    private byte[][] dash;    // игровое поле 2 - свободная ячейка
    private byte mySym;     // символ компьютера 0 - нолик, 1 - крестик
    private byte compSym;   // символ противника 0 - нолик, 1 - крестик
    private int n;          // количество ячеек
    private IdentRes winner;// победитель
    private XODraw drawer;  // класс рисования, инициализируется при начале игры
    private String messageWinner;
    private boolean hard;// применяется усложенный алгоритм расчета хода
    
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
        hard = true;
//        mySym = 0;
//        compSym = 1;
        drawer.drawGrid();
        messageWinner = "";
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
    private IdentRes getBestOfTheBest(ArrayList<IdentRes> incoming){
        // функция при усложненном алгоритме из ходов одинаково удаленных от победы выбирает тот, который
        // перекрывает противнику больше ходов
        IdentRes rez=null;
        Iterator<IdentRes> iter = incoming.iterator();
        IdentRes[] compareEl = new IdentRes[incoming.size()];
        byte[][] arrayStep = new byte[3][n];
        int i = 0;
        int prevousStep = 0;
        if (iter.hasNext()) {
            compareEl[i]= iter.next();
            prevousStep = compareEl[i].stepOfend;
            rez = compareEl[i];
            i++;
        }
        while (iter.hasNext()){
            compareEl[i] = iter.next();
            if (compareEl[i].stepOfend==prevousStep) {
                i++;
            }
            else {
                i--;
                break;
            } 
        }
        if (i>1){
            // с одинаковым шагом больше нуля, неоходимо проводить доп отбор
            // вначале рассмотрим какие ходы во всех клетках мы можем перекрыть (т.е. строчки, столбцы
            // и диагонали, где нет наших ходов
            for (int k=0;k<n;k++){
                boolean breakCol = false;
                boolean breakRow = false;
                for (int l = 0;l<n;l++)
                {
                    if (!breakCol&&dash[k][l]==mySym) breakCol = true;
                    if (!breakRow&&dash[l][k]==mySym) breakRow = true;
                }
                arrayStep[0][k] = (byte)(breakCol?0:1);
                arrayStep[1][k] = (byte)(breakRow?0:1);
            }
            // далее диагонали
            boolean breakLeft = false;
            boolean breakRight = false;
            for (int k=0;k<n;k++){
                if (!breakLeft&&dash[k][k]==mySym) breakLeft = true;
                if (!breakRight&&dash[k][n-(k+1)]==mySym) breakRight = true;
            }
            arrayStep[2][1] = (byte)(breakLeft?0:1);
            arrayStep[2][2] = (byte)(breakRight?0:1);
            // далее обходим массив и вычислям самый лучший наш ход
            int bestChoose = 0;
            for (int k =0;k<=i;k++){
                IdentRes temp = compareEl[k];
                for (int l =0;l<n;l++){
                    int tekChoose = 0;
                    if (temp.type==eTip.col){
                        // обходим строки
                        if (dash[l][temp.num]==2) {
                            tekChoose = arrayStep[0][l]+arrayStep[1][temp.num];
                            // далее рассмотрим диагонили.. принадлежит ли он 
                            if(l==temp.num) tekChoose = tekChoose+arrayStep[2][1];
                            if (l==(n-(l+1))) tekChoose = tekChoose+arrayStep[2][2];
                        }
                    }
                    else {
                        if (temp.type==eTip.row){
                            // обходим строки
                            if (dash[temp.num][l]==2) {
                                tekChoose = arrayStep[0][temp.num]+arrayStep[1][l];
                                // далее рассмотрим диагонили.. принадлежит ли он 
                                if(l==temp.num) tekChoose = tekChoose+arrayStep[2][1];
                                if (l==(n-(l+1))) tekChoose = tekChoose+arrayStep[2][2];
                            }
                        }
                        else {
                            // диагональ
                            if (temp.num==0){
                                // слева направо
                                if (dash[l][l]==2) {
                                    tekChoose = arrayStep[0][l]+arrayStep[1][l]+arrayStep[2][1];
                                    // далее рассмотрим диагонили.. принадлежит ли он 
                                    if (l==(n-(l+1))) tekChoose = tekChoose+arrayStep[2][2];
                                }
                            }
                            else{
                                // справа на лево
                                if (dash[l][n-(l+1)]==2) {
                                    tekChoose = arrayStep[0][n-(l+1)]+arrayStep[1][l]+arrayStep[2][2];
                                    // далее рассмотрим диагонили.. принадлежит ли он 
                                    if (l==l) tekChoose = tekChoose+arrayStep[2][1];
                                }
                            }
                        }
                    }
                    if (tekChoose>bestChoose) {
                        bestChoose = tekChoose;
                        rez = compareEl[k];
                        rez.preferredCell = l;
                    }
                }
            }
        }
        return rez;
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
        if (compBestRes!=null&&compBestRes.stepOfend==0) 
        {
            winner = compBestRes;
            messageWinner = java.util.ResourceBundle.getBundle("MyPakage/mypropertys").getString("messageWinner");
        }
        
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
                else
                    // если усложненный алгоритм, проверим, возможно следует выбрать наилучший ход из равных к окончанию
                    if (hard) {
                        currentRes = getBestOfTheBest(ourList);
                    }
            }
            else
            {
                // он ближе к победе. надо помешать
                currentRes = compBestRes;
                    // если усложненный алгоритм, проверим, возможно следует выбрать наилучший ход из равных к окончанию
                    if (hard) {
                        currentRes = getBestOfTheBest(compList);
                    }
            }
            // сделать следующий шаг и отрисовать
            if (currentRes.preferredCell>=0){
                // если есть предпочтительный ход
                    if (currentRes.type == eTip.col)
                    {
                        dash[currentRes.num][currentRes.preferredCell]= mySym;
                        tecCol = currentRes.num;
                        tecRow = currentRes.preferredCell;
                    }
                    if (currentRes.type == eTip.row)
                    {
                        dash[currentRes.preferredCell][currentRes.num]= mySym;
                        tecCol = currentRes.preferredCell;
                        tecRow = currentRes.num;
                    }
                    if (currentRes.type == eTip.diag)
                    {
                        dash[currentRes.preferredCell][Math.abs(currentRes.num-currentRes.preferredCell)] = mySym;
                        tecCol = currentRes.preferredCell;
                        tecRow = Math.abs(currentRes.num-currentRes.preferredCell);
                    }
                }
                
            else {
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
            }
        if (tecCol>=0&&tecRow>=0) drawer.drawCell(tecCol,tecRow,true);
        }
        else
        {
            if (winner ==null) messageWinner = java.util.ResourceBundle.getBundle("MyPakage/mypropertys").getString("messageDraw");
            
        }
        if (winner!=null&&winner.equals(ourBestRes)) {
            winner = ourBestRes;
            messageWinner = java.util.ResourceBundle.getBundle("MyPakage/mypropertys").getString("messageLose");
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
    
    public void setSym(byte compSym)
    {
        // метод устанавливает собственный символ и символ соперника
        this.compSym    = compSym;
        this.mySym      = (byte)(compSym==1?0:1);
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
                mObj.preferredCell = -1;
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
                mObj.preferredCell = -1;
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
                 mObj.preferredCell = -1;
                 rez.add(mObj);
                }
        if (diag2!=-1)
                {
                 IdentRes mObj = new IdentRes();
                 mObj.type   = eTip.diag;
                 mObj.num    = n-1;
                 mObj.stepOfend= diag2;
                 mObj.preferredCell = -1;
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
   public void showMessageWinner(Component parent) 
   {
       if (!messageWinner.isEmpty()){
           JOptionPane.showMessageDialog(parent, messageWinner);
           messageWinner = "";
       }
   }
           
}