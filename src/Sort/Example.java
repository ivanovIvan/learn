/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sort;

import Service.MyStdOut;

/**
 *
 * @author dav
 */
public class Example {

    protected static boolean less(Comparable v, Comparable w)
    { 
        return v.compareTo (w) <0; 
    }
    
    protected static void exch (Comparable [ ] a, int i, int j)
    { 
        Comparable t = a[i]; 
        a[i] = a[j]; 
        a[j] = t;
    }
    protected static void show(Comparable[] a)
    { // Вывод массива в одной строке,
        for (int i = 0; i < a. length; i++) MyStdOut.print(a[i] + " ");
        MyStdOut.println("");
    }

    public static boolean isSorted(Comparable[] a)
    { // Проверка упорядоченности элементов массива,
        for (int i = 1; i < a. length; i++) if (less(a[i], a[i-1])) return false;
        return true;
    }
    
}
