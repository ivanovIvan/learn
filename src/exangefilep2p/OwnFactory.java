/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public class OwnFactory {
    private NetworkToolsImpl currentNetworkTools;
    private static OwnFactory currentFactory;
    {
        currentFactory = new OwnFactory();
    }
    public static OwnFactory getCurrentInstace(){
        return currentFactory;
    }
    
    public MyInterfaceMessage getMyMessage(){
        return MyMessaging.getCurrentInstance();
    }
    public MyInterfaceParameters getMyParameters() {
        return MyParameters.getCurrentInstance();
    }
    
    public NetworkToolsImpl getNerworkTools() {
        if (currentNetworkTools==null) currentNetworkTools = new NetworkToolsSocketUDP();
        return currentNetworkTools;
    }
}
