/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTest;

import MyCollections.BagOnArray;
import MyCollections.Bag_API;
import java.lang.instrument.Instrumentation;
/**
 *
 * @author dav
 */
public class BagTest {
    
    public static void Run(Bag_API myBag, int count, boolean print){
        long start_time = System.currentTimeMillis();
        for (int i = 0; i<count;i++){
            myBag.add(i);
        }
        long end_time = System.currentTimeMillis();

        long timeToAdd = end_time-start_time;
        
        start_time = System.currentTimeMillis();
        for (Object currString:myBag){
            if (print) System.out.println(currString);
        }
        end_time = System.currentTimeMillis();
        long timeToGet = end_time-start_time;
        long size = 0;//getObjectSize(myBag);
        System.out.println("Time to add is:"+timeToAdd+" milisecond");
        System.out.println("Time to get is:"+timeToGet+" milisecond");
//        System.out.println("Size collection is:"+size);
    }
}
