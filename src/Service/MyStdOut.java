/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.util.Locale;

/**
 *
 * @author dav
 */
public class MyStdOut {
    public static  void printLn(String text) {
        System.out.println(text);
    }
    public static void print(String text) {
        System.out.print(text);
    }
    
    public static String getFormatNumeric(Integer i) {
        //return String.format(Locale.ROOT, format, args)
        return i.toString();
    }
    public static String getFormatNumeric(long i) {
        //return String.format(Locale.ROOT, format, args)
        return Long.toString(i);
    }
}
