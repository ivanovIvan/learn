/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import exangefilep2p.NetworkToolsFactory.NetworkToolsImpl;

/**
 *
 * @author dav
 */
public class ExangeFileP2P {
    private NetworkToolsImpl currentNetworkTools;
    /**
     * @param args the command line arguments
     */
    public void ExangeFileP2P() {
        currentNetworkTools = new NetworkToolsFactory().getInstant();

    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
