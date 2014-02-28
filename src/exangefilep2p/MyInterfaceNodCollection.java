/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public interface MyInterfaceNodCollection {
    String getCurrentUniquiID(); // return uniqui id computer
    void updateStatusClient(String uniquiID, boolean live);// mark nod as live or not
    
}
