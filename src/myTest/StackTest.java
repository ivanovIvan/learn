/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTest;

import MyCollections.Queue_API;
import MyCollections.Stack_API;

/**
 *
 * @author dav
 */
public class StackTest {
    public static void Run(Stack_API myStack, int count, boolean print){
        long start_time = System.currentTimeMillis();
        for (int i = 0; i<count;i++){
            myStack.push(i);
        }
        long end_time = System.currentTimeMillis();

        long timeToAdd = end_time-start_time;
        for (Object item:myStack){
            if (print) System.out.println(item);
        }
        
        start_time = System.currentTimeMillis();
        for (int i = 0; i<count;i++){
            myStack.pop();
        }
        end_time = System.currentTimeMillis();
        long timeToGet = end_time-start_time;
        long size = 0;//getObjectSize(myBag);
        System.out.println("Time to add is:"+timeToAdd+" milisecond");
        System.out.println("Time to get is:"+timeToGet+" milisecond");
//        System.out.println("Size collection is:"+size);
    }        
}
