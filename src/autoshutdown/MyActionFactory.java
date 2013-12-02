/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoshutdown;

import javax.swing.JOptionPane;

/**
 *
 * @author dav
 */
public class MyActionFactory {
    MyAction currentAction;
    public abstract  class MyAction {
        public abstract void Action_SetAutostart(boolean autostart);
        public abstract void Action_Shutdown();
    }

    public  class  MyAction_Windows extends MyAction {

        @Override
        public void Action_SetAutostart(boolean autostart) {
        }

        @Override
        public void Action_Shutdown() {
            System.out.println("Выполнено действие");
        }
        
    }
    public  class MyAction_Linux extends MyAction{

        @Override
        public void Action_SetAutostart(boolean autostart) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void Action_Shutdown() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
    public MyAction getInstant(){
        if (currentAction==null ) currentAction= new MyAction_Windows();
        return currentAction;
    }
}

