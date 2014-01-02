/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoshutdown;

import java.io.File;
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
            // jar file
            File file = new File(System.getProperty("java.class.path"));
            String s;
            try
            {
                if(autostart)
                {
//                    s = "cmd /C " + "reg add HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                    s = "reg add HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                    "AutoShutDown" + " /t REG_SZ /d " + "\"" + file + "\""+" /f";
                }
                else
                {
                    s = "reg delete HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run " +
                    "/v " + "AutoShutDown" + " /f";
                }
                Process myProc = Runtime.getRuntime().exec(s);
                int rez = myProc.waitFor();// дождемся окончания выполнения
                //System.out.println(new Integer(rez).toString());
                //myProc.destroy();
             }
            catch (SecurityException ex) {
                // нет доступа
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ErrorAccess"),java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("nameError") , JOptionPane.ERROR_MESSAGE);
            }
            catch (IOException ex) {
                // нет доступа
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ErrorIO"),java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("nameError") , JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("UnknownError"), java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("nameError"), JOptionPane.ERROR_MESSAGE);
            }
             
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

