/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.io.Serializable;
import java.net.InetAddress;

/**
 *
 * @author dav
 */
public class Node implements Serializable {

    @Override
    public String toString() {
        return  Name;
    }
    protected String Name;
    protected boolean activ;
    protected String guid;
    private InetAddress myInetAddres;
    private int portNumber;

    /**
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * @return the activ
     */
    public boolean isActiv() {
        return activ;
    }

    /**
     * @param activ the activ to set
     */
    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    /**
     * @return the guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * @return the myAddres
     */
    public InetAddress getMyInetAddres() {
        return myInetAddres;
    }

    /**
     * @param myAddres the myAddres to set
     */
    public void setMyInetAddres(InetAddress myAddres) {
        this.myInetAddres = myAddres;
    }

    /**
     * @return the portNumber
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * @param portNumber the portNumber to set
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
    
}
