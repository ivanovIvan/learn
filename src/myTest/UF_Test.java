/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myTest;

import Etalon.StdRandom;
import Etalon.WeightedQuickUnionUF;
import Service.FormatIn;
import Service.MyFileReader;
import UF.UF_API;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dav
 */
public class UF_Test {
    public static String getTestFileName(String fileName) {
        return fileName.substring(0, fileName.length()-4)+"_testData.txt";
    }
    public static void CreateFileTest(String fileName)   {
        // write file
        
        //File logFile=new File(fileName);
        String fileTest = getTestFileName(fileName);//.substring(0, fileName.length()-4)+"_testData.txt";
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileTest));
        } catch (IOException ex) {
            System.out.println("Error. Not possible write file");
            return;
        }
        
        String delemiter = " ";
        //writer.write (string);

        MyFileReader myReader = new MyFileReader(fileName);
        int[] inItem;
        int p,q;
        int count = 0;
        WeightedQuickUnionUF UF_Etalon = new WeightedQuickUnionUF(1000000);
        for(String item:myReader){
            inItem = FormatIn.getIntFromString(item);
            if (inItem.length>1){
                p = inItem[0];
                q = inItem[1];
                UF_Etalon.union(p, q);
            } else {
                count = inItem[0];
            }
            
        }
        int rez = 0;
        for(int i=0; i<count;i++){
            p = StdRandom.uniform(count);
            q = StdRandom.uniform(count);
            if (p!=q) {
                rez = (UF_Etalon.connected(p, q))?1:0;
                try {
                    //inItem = FormatIn.getIntFromString(fileName);
                    writer.write(p+delemiter+q+delemiter+rez);
                    writer.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(UF_Test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        // close resource
        myReader.myCloseFile();
        try {        
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(UF_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void Run(UF_API uf, String fileName){
        // read the file and fill UF structure
        long start_time = System.currentTimeMillis();
        MyFileReader myReader = new MyFileReader(fileName);
        int[] inItem;
        int p,q,rez;
        for(String item:myReader){
            inItem = FormatIn.getIntFromString(item);
            if (inItem.length>1){
                p = inItem[0];
                q = inItem[1];
                uf.union(p, q);
            }
        }
        long end_time = System.currentTimeMillis();
        long timeToAdd = end_time-start_time;
        myReader.myCloseFile();
        System.out.println("Time to union:"+timeToAdd+" milisecond");
        
        // read the test data and check her
        start_time = System.currentTimeMillis();
        myReader = new MyFileReader(getTestFileName(fileName));
        for(String item:myReader){
            inItem = FormatIn.getIntFromString(item);
            if (inItem.length>1){
                p = inItem[0];
                q = inItem[1];
                rez = inItem[2];
                if (rez != ((uf.connected(p,q))?1:0)) System.out.println("Error check wich data p:"+p+" q:"+q);
            }
        }
        end_time = System.currentTimeMillis();
        long timeToGet = end_time-start_time;
        myReader.myCloseFile();
        System.out.println("Time to connected:"+timeToGet+" milisecond");
    }        
}
