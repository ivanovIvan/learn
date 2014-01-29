/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 * Класс служит для различных сообщений, ошибки, предупреждение, лог и т.д.
 * @author dav
 */
public class MyMessaging {
    enum TypeOfMessage {error,warning,log,information};
    static MyMessaging currentInstance;
    {
        currentInstance = new MyMessaging();
    }
    
    public void setMessage(TypeOfMessage type, String message){
        System.out.println(message);
    }

    public void setMessage(String message, Exception ex){
        //System.out.println(message);
    }
           
    public static MyMessaging getCurrentInstance(){
        return currentInstance;
    }
}
