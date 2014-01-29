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
        private CopyOnWriteArrayList<ProgressDialog> ListenersProgress;
        
        // +++ ГЕТТЕРЫ И СЕТТЕРЫ + КОНСТРУКТОР
        
        public NetworkToolsImpl() {
            ListenersGUI = new CopyOnWriteArrayList<>();
            ListenersProgress = new CopyOnWriteArrayList<>();
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
      
        public void addListenerGUI(MyGUI listener) {
            ListenersGUI.addIfAbsent(listener);
        }

        public boolean removeListenerGUI(MyGUI listener) {
            return ListenersGUI.remove(listener);
        }

      
        public void addListenerProgress(ProgressDialog listener) {
            ListenersProgress.addIfAbsent(listener);
        }

        public boolean removeListenerProgress(ProgressDialog listener) {
            return ListenersProgress.remove(listener);
        }
        // --- ГЕТТЕРЫ И СЕТТЕРЫ + КОНСТРУКТОР
        
        // +++ ОБРАБОТКА СОБЫТИЙ 
        
        // обновляет состояние узла в модели, добавляет, активирует/даективирует
        public void treeModel_refreshNode(String nodGUI, boolean isActive){
        }
        
        // ошибка при передаче/получении файла
        public void fileTransferError(String fileName, Node node, String textError){
            
        }
        
        // окончание передачи/получения файла
        public void fileTransferOk(String fileName, Node node){
            
        }

        
        // --- ОБРАБОТКА СОБЫТИЙ 
        
        // +++ ОБЩИЕ МЕТОДЫ
        public void sendFile(String fileName, Node node){
            // посылает файл конкретному узлу
            
        }
        // --- ОБЩИЕ МЕТОДЫ

    } 
    
       
    public class NetworkToolsSocketUDP extends NetworkToolsImpl {
        public NetworkToolsSocketUDP(){
            super();
            //int a = 4;
        }
        
    }

    public class NetworkToolsSocketTCP extends NetworkToolsImpl {
        
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
