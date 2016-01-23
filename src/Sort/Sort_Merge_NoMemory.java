/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sort;

/**
 *
 * @author dav
 *  work wery slowly, not solutions, yet
 */
public class Sort_Merge_NoMemory extends Example{
    protected static void merge(Comparable[] a, int lo, int hi){
        if (lo!=hi) {
            int delimiter = lo+(hi-lo)/2;
            merge(a,lo,delimiter);
            merge(a,delimiter+1,hi);
            // then merge both array on one sort array
            int indexCur=lo,indexleft = lo,indexRight=delimiter+1, temp;
            while (indexCur<indexRight){
                if (indexleft == indexRight) {
                    // in this is place we have if right element is ended
                    while (indexleft>indexCur) {
                        exch(a,indexleft,indexleft-1);
                        indexleft--;
                    }
                    indexCur = indexRight;
                }else {
                    if (less(a[indexleft],a[indexRight])) {
                        if ( indexleft != indexCur) {
                            exch(a, indexCur, indexleft);
                            temp = indexleft+1;
                            while (temp<indexRight&&less(a[temp], a[temp-1])) {
                                exch(a, temp, temp-1);
                                temp++;
                            }
                        }
                        else indexleft++;
                        //if (i<(j-1)) exch(a,i,j-1);
                    } else {
                        exch(a, indexCur, indexRight);
                        if (indexCur == indexleft) indexleft = indexRight;
                        if (indexRight < hi) indexRight++;
                    }
                    indexCur++;
                    
                }
            }
        }
    }
    public static void sort(Comparable[] a){
        merge(a,0,a.length-1);
    }
}
