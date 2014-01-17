/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 * пакет для передачи данных
 * @author dav
 */
public class MyPakage {

    private Node nodeDestination;
    private Node nodeSourde;
    private static int countByteInPakage;
    private Byte[] data;
    
    public void MyPakage(){
        if (countByteInPakage<=0) countByteInPakage = 1024;
        data = new Byte[countByteInPakage];
    }
    
    public Node getNodeDestination() {
        return nodeDestination;
    }

    public Node getNodeSourde() {
        return nodeSourde;
    }

    public void setNodeDestination(Node nodeDestination) {
        this.nodeDestination = nodeDestination;
    }

    public void setNodeSourde(Node nodeSourde) {
        this.nodeSourde = nodeSourde;
    }
   
    /**
     * @return the countByteInPakage
     */
    public static int getCountByteInPakage() {
        return countByteInPakage;
    }

    /**
     * @param aCountByteInPakage the countByteInPakage to set
     */
    public static void setCountByteInPakage(int aCountByteInPakage) {
        countByteInPakage = aCountByteInPakage;
    }
    
    public String getHashSum(){
        // return hash summ of data
        return "yet not realise";
    }

    /**
     * @return the data
     */
    public Byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Byte[] data) {
        this.data = data;
    }
    
    
}
