/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public interface MyInterfacePakageQueue {

    public boolean putPakage(MyPakage pakage);
    public MyPakage getPakage();
    public boolean deletePakageFromQueue(MyPakage pakage);        
}
