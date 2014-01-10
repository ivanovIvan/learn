/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.util.HashSet;

/**
 *
 * @author dav
 */
public class NetworkToolsFactory {
    
    public abstract class  NetworkToolsImpl {
        //private 
    } 
    
    
    
    public class NetworkToolsSocket extends NetworkToolsImpl {
        private HashSet<MyGUI> Listeners;

        public NetworkToolsSocket() {
            Listeners = new HashSet<>();
        }
        
        public void addListener(MyGUI listener) {
            Listeners.add(listener);
        }
        public boolean removeListener(MyGUI listener) {
            return Listeners.remove(listener);
        }
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
