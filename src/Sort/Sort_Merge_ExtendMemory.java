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
public class Sort_Merge_ExtendMemory extends Example{
    protected static Comparable[]  newArray;
    protected static void merge(Comparable[] a, int lo, int hi){
        if (lo!=hi) {
            int delimiter = lo+(hi-lo)/2;
            merge(a,lo,delimiter);
            merge(a,delimiter+1,hi);
            // then merge both array on one sort array
            int del = delimiter+1,j = lo,currPos=lo, i = del;
            
            //lo,j=, newPos=0,currPos=lo;
            while (currPos<=hi){
                if (j>=del||(i<=hi&&less(a[i],a[j]))){
                    newArray[currPos] = a[i++];
                    if (j>=del) {
                        while(j<del) {
                            newArray[++currPos] = a[j++];
                        }
                    }
                } else {
                    newArray[currPos] = a[j++];
                }
                currPos++;/*
                if (currPos<i){
                    if (less(a[i],a[currPos])) {
                        exch(a, currPos, i);
                        j = i;
                        while (j<hi&&less(a[j+1], a[j])) {
                            exch(a, j, j+1);
                            j++;
                        }
                    }
                }else {
                    currPos = hi;
                    /*if (less(a[currPos],a[currPos+1])) exch(a, currPos, currPos+1);
                    else currPos = hi;
                }
                currPos++;*/
            }
            System.arraycopy(newArray, lo, a, lo, hi-lo+1);
        }
    }
    public static void sort(Comparable[] a){
        newArray = new Comparable[a.length];
        merge(a,0,a.length-1);
        //a = newArray;
    }    
}
