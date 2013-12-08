/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoshutdown;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author dav
 */
public class MyActionFactory {
    MyAction currentAction;
    public static final class OsUtils
{
   private static String OS = null;
   public static String getOsName()
   {
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
   }
   public static boolean isWindows()
   {
      return getOsName().startsWith("Windows");
   }

   public static boolean isUnix() {
       return getOsName().startsWith("Linux");
   }
}
    public abstract  class MyAction {
        public abstract void Action_SetAutostart(boolean autostart);
        public abstract void Action_Shutdown() throws RuntimeException, IOException;
    }

    public  class  MyAction_Windows extends MyAction {

        @Override
        public void Action_SetAutostart(boolean autostart) {
            
        }

        @Override
        public void Action_Shutdown() throws RuntimeException, IOException {
            String shutdownCommand = "shutdown.exe -s -t 0";
            Runtime.getRuntime().exec(shutdownCommand);
            System.exit(0);            
        }
        
    }
    public  class MyAction_Linux extends MyAction{

        @Override
        public void Action_SetAutostart(boolean autostart) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void Action_Shutdown() throws RuntimeException, IOException{
            String shutdownCommand = "shutdown -h now";
            Runtime.getRuntime().exec(shutdownCommand);
            System.exit(0);            
        }

    }
    public MyAction getInstant(){
        if (currentAction==null ){
            if (OsUtils.isWindows()) currentAction= new MyAction_Windows();
            else currentAction= new MyAction_Linux();
        }
        return currentAction;
    }
}

