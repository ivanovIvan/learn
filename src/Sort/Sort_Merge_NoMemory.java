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
public class Sort_Merge_NoMemory extends Example{
    protected static void merge(Comparable[] a, int lo, int hi){
        if (lo!=hi) {
            int delimiter = lo+(hi-lo)/2;
            merge(a,lo,delimiter);
            merge(a,delimiter+1,hi);
            // then merge both array on one sort array
            int i = delimiter+1,currPos=lo,j=0;
            while (currPos<hi){
                if (currPos<i){
                    if (less(a[i],a[currPos])) {
                        exch(a, currPos, i);
                        j = i+1;
                        while (j<=hi&&less(a[j], a[i])) {
                            //exch(a, j, j+1);
                            j++;
                        }
                        if (i<(j-1)) exch(a,i,j-1);
                    }
                }else {
                    currPos = hi;
                }
                currPos++;
            }
        }
    }
    public static void sort(Comparable[] a){
        merge(a,0,a.length-1);
    }
}
