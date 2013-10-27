package MyPakage;

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
}
