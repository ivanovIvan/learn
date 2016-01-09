/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author dav
 */
public class FormatIn {
    static public int[] getIntFromString(String stringin){
        String[] tempString = stringin.split("\\s+");
        int[] rez = new int[tempString.length];
        int i = 0;
        for (String item:tempString) {
            try {
                rez[i++] = Integer.parseInt(item);
            }catch (NumberFormatException ex) {
                System.out.println("Error. Not possible convert to integer: "+item);
            }
            
        }
        return rez;
    }
}
