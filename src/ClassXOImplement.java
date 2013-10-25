/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */

import java.util.ArrayList;

public class ClassXOImplement {
    private byte[][] dash;    // игровое поле 2 - свободная ячейка
    private byte mySym;     // символ компьютера 0 - нолик, 1 - крестик
    private byte compSym;   // символ противника 0 - нолик, 1 - крестик
    
    public void initialNewGame(int n)
    {
        // инициализиует поле первоначальными занчениями из полей n
        dash = new byte[n][n];
        for (int i=0; i<n;i++)
        {
            for (int j=0;j<n;j++) dash[i][j]=2;
            
        }
    }
    
    public void runNextStep()
    {
        // алгоритм обрабатывает следующий ход
    }
    public void show()
    {
        // метод выводит массив на экран
    }
    
    public void setSym(byte mySym, byte compSym)
    {
        // метод устанавливает собственный символ и символ соперника
        this.mySym      = mySym;
        this.compSym    = mySym;
    }
    private void getTabLines(byte sym)
    {
        // процедура пробегает все возможные варианты и возвращает их отсортированными
        // по степени завершенности, самые близкие к завершению вверху
        ArrayList<IdentRes> rez = new ArrayList<>();
        
    }
}
