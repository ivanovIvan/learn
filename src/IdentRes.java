/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dav
 */
public class IdentRes {
    // класс реализуе хранение результат обработки поля (количество ходов до заврешения, тип строки, и начало)
    enum eTip {col, row, diag};
    public eTip type;   // тип элемента, строка, колонка или диагональ
    public int num;     // начало элемента, для диагноали 1- слева направо, 2 справо на лево
    public int stepOfend;  // количество шагов до окончания
}
