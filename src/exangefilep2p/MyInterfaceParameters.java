/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 * interface for getting parameters programm
 * @author dav
 */
public interface MyInterfaceParameters {
    // 
    public boolean isServer();
    public int getDelayForPakageQueue();
    public int getCountItemsForPakageQueu();
    public int getBytePerPakage();
    public String getTempPath();
    public String getIPServer();
    public int getPortServer();
    public int getCountThreadPool(); // omly for data + services 
}
