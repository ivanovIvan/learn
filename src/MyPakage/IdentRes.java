package MyPakage;

import java.util.Objects;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */
enum eTip {col, row, diag}; // col - строки, row - колонки
    
public class IdentRes implements Comparable<IdentRes> {


    // класс реализуе хранение результат обработки поля (количество ходов до заврешения, тип строки, и начало)
    public eTip type;   // тип элемента, строка, колонка или диагональ
    public int num;     // начало элемента, для диагноали 1- слева направо, 2 справо на лево
    public int stepOfend;  // количество шагов до окончания
    //public bool win;        // признак победы
       
    @Override
    public int compareTo(IdentRes compareObject)
    {
        // метод отвечает за сортировку, сравниваем по stepOfend
        /*if (win && compareObject.win !=true)     
        {
            // однозначно это элемент старше
            return 1;
        }
        else { if (win!=true && compareObject.win)
                {
                    // однозначно этот элемент младше
                    return -1;
                }
                else {
                    // тогда сортируем по количеству шагов*/
                    if (stepOfend < compareObject.stepOfend)
                        return -1;
                    else if (stepOfend == compareObject.stepOfend)
                        return 0;
                    else
                        return 1;        
         /*            }                   
        }*/
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + this.num;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IdentRes mObj = (IdentRes)obj;
        if (this.type==mObj.type&&this.num==mObj.num) return true;
        else return false;
    }
}
