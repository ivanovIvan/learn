/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public interface MyGUI {
    public String saveFile(String nameFile, Node myNode);
    public void recieveFile(String nameFile);
    public void reciveDebugging(String message);
    public void errorAccesToFile(String fileName,Exception ex);
    public String qeustionRecieveFIle(String fileName,Node from);
}
