/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dav;

import MyCollections.BagOnArray;
import MyCollections.BagOnLinkedList;
import MyCollections.Queue;
import MyCollections.Stack;
import UF.UF_QuickFind;
import myTest.BagTest;
import myTest.QueueTest;
import myTest.StackTest;
import myTest.UF_Test;

/**
 *
 * @author dav
 */
public class algo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int among = 10000000;
//        BagTest.Run(new BagOnArray(),among, false);
        //BagTest.Run(new BagOnArray<Integer>(),among, false);
        //BagTest.Run(new BagOnLinkedList<Integer>(),among,false);
        //QueueTest.Run(new Queue<Integer>(), among, true);
        //StackTest.Run(new Stack<Integer>(), among, false);
        UF_Test.Run(new UF_QuickFind(1000000),"f:\\_Учеба\\Algo\\Aglo\\TestFile\\UF\\largeUF.txt");
        
    }
    
}
