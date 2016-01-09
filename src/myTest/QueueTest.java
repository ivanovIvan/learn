/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTest;

import MyCollections.Bag_API;
import MyCollections.Queue_API;

/**
 *
 * @author dav
 */
public class QueueTest {
    public static void Run(Queue_API myQueue, int count, boolean print){
        long start_time = System.currentTimeMillis();
        for (int i = 0; i<count;i++){
            myQueue.enqueue(i);
        }
        long end_time = System.currentTimeMillis();

        long timeToAdd = end_time-start_time;
        for (Object item:myQueue){
            if (print) System.out.println(item);
        }
        
        start_time = System.currentTimeMillis();
        for (int i = 0; i<count;i++){
            myQueue.dequeue();
        }
        end_time = System.currentTimeMillis();
        long timeToGet = end_time-start_time;
        long size = 0;//getObjectSize(myBag);
        System.out.println("Time to add is:"+timeToAdd+" milisecond");
        System.out.println("Time to get is:"+timeToGet+" milisecond");
//        System.out.println("Size collection is:"+size);
    }    
}
