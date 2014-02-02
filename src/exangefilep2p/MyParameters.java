/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public class MyParameters implements MyInterfaceParameters{
    private static MyParameters currentInstace;
    
    {
        currentInstace = new MyParameters();
    }
    public static MyParameters getCurrentInstance(){
        return currentInstace;
    }
    
    @Override
    public boolean isServer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDelayForPakageQueue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCountItemsForPakageQueu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getBytePerPakage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
