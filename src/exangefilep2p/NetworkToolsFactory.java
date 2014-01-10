/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public class NetworkToolsFactory {
    
    public abstract class  NetworkToolsImpl {
        //private 
    } 
    public class NetworkToolsSocket extends NetworkToolsImpl {
    
    }
    public class NetworkToolsJXTA extends NetworkToolsImpl {
    
    }
    /**
     *
     * @return
     */
    public NetworkToolsImpl getInstant () {
        return new NetworkToolsJXTA();
    }
}
