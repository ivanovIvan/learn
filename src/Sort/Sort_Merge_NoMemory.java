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
            int i = lo,j=delimiter+1, newPos=0,currPos=lo;
            while (i<=delimiter||j<=hi){
                if (i>delimiter) newPos = j++;
                else if (j>hi) newPos = i++;
                else {
                    if (less(a[i],a[j])) newPos = i++;
                    else newPos = j++;
                }
                if (currPos!=newPos) exch(a, currPos, newPos);
                currPos++;
            }
        }
    }
    public static void sort(Comparable[] a){
        merge(a,0,a.length-1);
    }
}
