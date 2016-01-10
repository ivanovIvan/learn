/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTest;

import Etalon.StdRandom;
import Service.MyStdOut;
import Sort.Example;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dav
 */
public class Sort_Test {
    public static Integer[] getTestArray(int capacity) {
        Integer[] rez = new  Integer[capacity];
        for (int i=0;i<capacity;i++){
            rez[i] = StdRandom.uniform(1000000000);
        }
        return rez;
    }
    public static void Run(Class<?> SortClass, Comparable[] b){
        //myStaticMethod takes a Double and String as an argument
        Comparable[] a = new Comparable[b.length];
        System.arraycopy(b, 0, a, 0, b.length);
        Method Sort;
        try {
            Sort = SortClass.getMethod("sort", Comparable[].class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Sort_Test.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (SecurityException ex) {
            Logger.getLogger(Sort_Test.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        MyStdOut.printLn("Test for "+SortClass+" capacity "+MyStdOut.getFormatNumeric(a.length));
        long start_time = System.currentTimeMillis();
        try {
            Sort.invoke(null,new Object[]{a});
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Sort_Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Sort_Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Sort_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
//        SortClass.sort(a);
        long stop_time = System.currentTimeMillis();
        MyStdOut.printLn("Time to sort "+MyStdOut.getFormatNumeric(stop_time-start_time)+" milisecond ");
        MyStdOut.printLn((Example.isSorted(a))?"Sorted correctly":"ERROR. Wrong sort resultat");
        MyStdOut.printLn("");
        
    }
}
