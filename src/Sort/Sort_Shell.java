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
    public static int[] getIncrementPerion(int n){
        // Однако гораздо лучший вариант предложил Р.Седжвик. Его последовательность имеет вид
        ArrayList<Integer> myList = new ArrayList<>();
        Integer curr = 1;
        int i = 0;
        boolean chet = n%2==0;
        myList.add(curr);
        while (curr<n/3){
            if (chet) 
        }
    }
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
