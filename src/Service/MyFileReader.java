/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author dav
 */
public class MyFileReader implements Iterable<String>{
    private class MyIterator implements Iterator<String>{

        @Override
        public boolean hasNext() {
            return nextLine!=null;
        }

        @Override
        public String next() {
            tempLine = nextLine;
            readNextLine();
            return tempLine;
        }
    }
    BufferedReader br;
    String nextLine;
    String tempLine;
    
    public MyFileReader(String fileName) {
        try {
            br = new BufferedReader(new FileReader(fileName));
            readNextLine();        
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR. Not possible read file: "+fileName);
        }

    }

    private void readNextLine(){
        try {
            nextLine = br.readLine();
        } catch (IOException ex) {
            System.out.println("ERROR wich read line in file");
        }
    }
    
    public void myCloseFile(){
        if (br!=null){
            try {
                br.close();
            } catch (IOException ex) {
                System.out.println("ERROR. Not possible close file");
            }
        }
    }
    
    @Override
    public Iterator<String> iterator() {
        return new MyIterator();
    }
    
    
}
