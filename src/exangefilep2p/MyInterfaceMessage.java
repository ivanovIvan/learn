/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 *
 * @author dav
 */
public interface MyInterfaceMessage {
    public void setMessage(TypeOfMessage type, String message);
    
    public void setMessage(String message, Exception ex);
           
            
}
