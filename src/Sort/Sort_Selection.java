/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sort;

/**
 *
 * @author dav
 */
public class Sort_Selection extends Example{

    public static void sort(Comparable[] a){
        int minIndex=0;
        for (int i=0;i<a.length;i++) {
            minIndex = i;
            for (int j=i;j<a.length;j++){
                if (less(a[j],a[minIndex])) minIndex = j;
            }
            if (minIndex!=i) exch(a, minIndex, i);
        }
    }
}
