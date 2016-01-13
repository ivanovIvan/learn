/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sort;

import static Sort.Example.less;
import java.util.ArrayList;

/**
 *
 * @author dav
 */
public class Sort_Shell {
    private static int[] getIncrementPerion(int n){
        // Однако гораздо лучший вариант предложил Р.Седжвик. Его последовательность имеет вид
        ArrayList<Integer> myList = new ArrayList<>();
        Integer curr = 1;
        double i = 1;
        boolean chet = n%2==0;
        myList.add(1);
        while (curr<n/3){
            if (chet) curr = ((Double)(9*Math.pow(2, i)-9*Math.pow(2, i/2)+1)).intValue();
            else curr = ((Double)(8*Math.pow(2, i)-6*Math.pow(2, (i+1)/2)+1)).intValue();
            myList.add(curr);
            i++;
        }
        int[] rez = new int[myList.size()];
        curr = 0;
        for (Integer item:myList) {
            rez[curr++] = item;
        }
        return rez;
    }
    
    private static void innerSort(Comparable[] a, int step){
        int k = a.length-1; 
        int j = 0;
        while ((k-step)>=0) {
            //for (int i=k+step;i<a.length;i=i+step) {
                j=k;
                while(j>(step-1)){
                    if (less(a[j],a[j-step])) Example.exch(a, j, j-step);
                    j = j - step;
                }
            //}
            k--;
        }
    }
    public static void sort(Comparable[] a){
        int[] arrayStep = getIncrementPerion(a.length);
        for (int i = arrayStep.length-1; i>=0;i--) {
            innerSort(a, arrayStep[i]);
        }
    }        
}
