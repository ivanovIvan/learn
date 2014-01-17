/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author dav
 */
public class NetworkToolsFactory {
    private static NetworkToolsFactory currentFactory;
    static {
        currentFactory = new NetworkToolsFactory();
    }
    public class  NetworkToolsImpl {
        private TreeModel treeModel;
        private Options options;
        private CopyOnWriteArrayList<MyGUI> ListenersGUI;

        public NetworkToolsImpl() {
            ListenersGUI = new CopyOnWriteArrayList<>();
        }
        
        /**
         * @return the treeModel
         */
        public TreeModel getTreeModel() {
            return treeModel;
        }

        /**
         * @param treeModel the treeModel to set
         */
        public void setTreeModel(TreeModel treeModel) {
            this.treeModel = treeModel;
        }
        
        public void treeModel_refreshNode(String nodGUI, boolean isActive){
            // обновляет состояние узла в модели, добавляет, активирует/даективирует
        }
        public void sendFile(String fileName, Node node){
            // посылает файл конкретному узлу
        }

        /**
         * @return the options
         */
        public Options getOptions() {
            return options;
        }

        /**
         * @param options the options to set
         */
        public void setOptions(Options options) {
            this.options = options;
        }
      
        public void addListener(MyGUI listener) {
            ListenersGUI.addIfAbsent(listener);
        }

        public boolean removeListener(MyGUI listener) {
            return ListenersGUI.remove(listener);
        }

    } 
    
    
    
    public class NetworkToolsSocket extends NetworkToolsImpl {
        
    }
    public class NetworkToolsNETTY extends NetworkToolsImpl {
    
    }
    /**
     *
     * @return
     */
    public NetworkToolsImpl getNetworkTools(){
        return new NetworkToolsNETTY();
    }
    public static NetworkToolsFactory getInstant () {
        return currentFactory;
    }
}
