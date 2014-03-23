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
    private boolean isServer;
    static{
        currentInstace = new MyParameters();
    }
    public static MyParameters getCurrentInstance(){
        return currentInstace;
    }
    
    public void setServer(boolean isServer){
        this.isServer = isServer;
    }
    
    @Override
    public boolean isServer() {
        return isServer;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDelayForPakageQueue() {
        return 500; // delay 0.5 seconds
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCountItemsForPakageQueu() {
        return 100;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getBytePerPakage() {
        return 1024;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTempPath() {
        return System.getProperty("java.io.tmpdir");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public java.lang.String getIPServer() {
        return "127.0.0.1";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getPortServer () {
        return 4545;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCountThreadPool() {
        return 10;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
