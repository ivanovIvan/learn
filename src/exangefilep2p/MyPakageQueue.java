/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this is collection must bloking read and non bloking write
 * @author dav
 */
public class MyPakageQueue implements MyInterfacePakageQueue {
    //private static MyInterfacePakageQueue currentInstance;
    private LinkedBlockingDeque<MyPakage> listOfmessage;
    private int delay;
    public MyPakageQueue() {
        listOfmessage = new LinkedBlockingDeque<>();
        delay = MyParameters.getCurrentInstance().getDelayForPakageQueue();
        
    }
    @Override
    public boolean putPakage(MyPakage pakage) {
        boolean rez = false;
        try {
            rez = listOfmessage.offer(pakage, delay, TimeUnit.MILLISECONDS);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (InterruptedException ex) {
            Logger.getLogger(MyPakageQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rez;
    }

    @Override
    public MyPakage getPakage() {
        MyPakage rez = null;
        try {
            rez = listOfmessage.poll(delay, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Logger.getLogger(MyPakageQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rez;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @return instace of class
     */
//    public static MyInterfacePakageQueue getCurrentInstance(){
//        return currentInstance;
//    }

    /**
     *
     * @param pakage
     * @return
     */
    @Override
    public boolean deletePakageFromQueue(MyPakage pakage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
