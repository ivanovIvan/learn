/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public class Node {

    @Override
    public String toString() {
        return  Name;
    }
    private String Name;
    private boolean activ;

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
    
}
