/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTest;

import MyCollections.Stack_API;
import Service.MyFileReader;
import UF.UF_API;

/**
 *
 * @author dav
 */
public class UF_Test {
    public static void Run(UF_API uf, String fileName){
        long start_time = System.currentTimeMillis();
        MyFileReader myReader = new MyFileReader(fileName);
        String[] inInt;
        int p,q;
        for(String item:myReader){
            inInt = item.split("\\s+");
            if (inInt.length==2) {
                p = Integer.parseInt(inInt[0]);
                q = Integer.parseInt(inInt[1]);
                uf.union(p, q);
            }
        }
        long end_time = System.currentTimeMillis();

        long timeToAdd = end_time-start_time;
        start_time = System.currentTimeMillis();
        for(String item:myReader){
            inInt = item.split("\\s+");
            p = Integer.parseInt(inInt[0]);
            q = Integer.parseInt(inInt[1]);
            uf.connected(p, q);
        }
        end_time = System.currentTimeMillis();
        long timeToGet = end_time-start_time;
        myReader.myCloseFile();
        System.out.println("Time to union:"+timeToAdd+" milisecond");
        System.out.println("Time to connected:"+timeToGet+" milisecond");
//        System.out.println("Size collection is:"+size);
    }        
}
