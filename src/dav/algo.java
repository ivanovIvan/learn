/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dav;

import Service.MyStdOut;
import Sort.Sort_Insertion;
import Sort.Sort_Merge_ExtendMemory;
import Sort.Sort_Merge_NoMemory;
import Sort.Sort_Selection;
import Sort.Sort_Shell;
import myTest.Sort_Test;

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
        //UF_Test.CreateFileTest("d:\\Java_Project\\Lern\\Algo\\Aglo\\TestFile\\UF\\largeUF.txt");
        
        //  UNION FIND
       /* String filePath = "d:\\Java_Project\\Lern\\Algo\\Aglo\\TestFile\\UF\\largeUF.txt";
        UF_Test.Run(new UF_QuickUnion(2000000),filePath);
        UF_Test.Run(new UF_WeightedQuickUnion(2000000),filePath);
        UF_Test.Run(new UF_QuickFind(2000000),filePath);*/
       
       // SORT ARRAY
       //Integer[] testArray = Sort_Test.getTestArray(10);
       Integer[] testArray = {314839861,730428587,154200775,634627186,341002217,510434320,257334558,133556827,465631549,708375567} ;
       if (testArray.length<15) MyStdOut.printlnArray(testArray);
       /*Sort_Test.Run(Sort_Selection.class,testArray);
       Sort_Test.Run(Sort_Insertion.class,testArray);
       Sort_Test.Run(Sort_Shell.class,testArray);
       Sort_Test.Run(Etalon.Shell.class,testArray);*/
       //Sort_Merge_NoMemory.sort(testArray);
       
       Sort_Test.Run(Sort_Merge_NoMemory.class,testArray);
       //Sort_Test.Run(Sort_Merge_ExtendMemory.class,testArray);
       //Sort_Merge_ExtendMemory.sort(testArray);
       //Sort_Test.Run(Etalon.Merge.class,testArray);
       
    }
    
}
