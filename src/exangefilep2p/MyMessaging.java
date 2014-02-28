/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

/**
 * Класс служит для различных сообщений, ошибки, предупреждение, лог и т.д.
 * @author dav
 */
public class MyMessaging implements MyInterfaceMessage {
    //enum TypeOfMessage {error,warning,log,information};
    private static MyMessaging currentInstance;
    {
        currentInstance = new MyMessaging();
    }
    
    /**
     *
     * @param type
     * @param message
     */
    @Override
    public void setMessage(TypeOfMessage type, String message){
        System.err.println(type.toString()+message);
    }

    /**
     *
     * @param message
     * @param ex
     */
    @Override
    public void setMessage(String message, Exception ex){
        System.err.println(message+ex.toString());
        //System.out.println(message);
    }
           
    public static MyMessaging getCurrentInstance(){
        return currentInstance;
    }
}
