/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author dav
 */
public class MyPakageQueue implements MyInterfacePakageQueue {
    private static MyInterfacePakageQueue currentInstance;
    private LinkedBlockingDeque<MyPakage> listOfmessage;
    
    {
        currentInstance = new MyPakageQueue();
        listOfmessage = new LinkedBlockingDeque<>();
    }

    @Override
    public boolean putPakage(MyPakage pakage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MyPakage getPakage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @return instace of class
     */
    public static MyInterfacePakageQueue getCurrentInstance(){
        return currentInstance;
    }
}
