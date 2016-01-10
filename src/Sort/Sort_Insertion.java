/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sort;

import static Sort.Example.less;

/**
 *
 * @author dav
 */
public class Sort_Insertion {
    public static void sort(Comparable[] a){
        int j=0;
        for (int i=0;i<a.length;i++) {
            j=i;
            while(j>0&&less(a[j],a[j-1])){
                Example.exch(a, j, j-1);
                j--;
            }
        }
    }    
}
