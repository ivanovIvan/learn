/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sort;

import Etalon.StdRandom;

/**
 *
 * @author dav
 */
public class Sort_Quicksort extends Example{
    
    private static void  quick(Comparable[] a, int lo, int hi) {
        if (lo < hi) {
            Comparable tempKey = a[lo];
            int indexLeft, indexRight;
            indexLeft = lo+1;
            indexRight = hi;
            while (indexLeft < indexRight) {
                while (less(a[indexLeft], tempKey) && indexLeft < indexRight) indexLeft++;
                while (less(tempKey, a[indexRight]) && indexLeft < indexRight) indexRight--;
                if (indexLeft != indexRight) exch(a, indexLeft, indexRight);
            }
            if (less(tempKey, a[indexLeft]) || tempKey.equals(a[indexLeft])) indexLeft--;
            if (lo != (indexLeft)) exch(a, lo, indexLeft);
            if ((hi-lo)  > 1) {
                /*System.out.println("1. lo "+lo+"  indexleft "+indexLeft+" hi "+hi);
                if ((hi-lo)<15) {
                   String printSubArray = "";
                   for (int i = lo; i <= hi; i++) {
                       printSubArray = printSubArray+"; "+a[i];
                   }
                   System.out.println(printSubArray);
                }*/
                quick(a, lo, indexLeft);
                //System.out.println("2. lo "+(indexLeft+1)+" hi "+hi);
                quick(a, indexLeft+1, hi);
            }
            
        }
    }
    public static void sort(Comparable[] a){
        // first shuffle the array
        StdRandom.shuffle(a);
        quick(a, 0, a.length-1);
    }    
}
