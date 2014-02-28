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
public class NetworkToolsImpl {
    protected TreeModel treeModel;
    protected Options options;
    protected CopyOnWriteArrayList<MyGUI> ListenersGUI;
    protected CopyOnWriteArrayList<ProgressDialog> ListenersProgress;
    protected MyInterfaceParameters myParamenters;
    protected MyInterfaceMessage myMessaging;
    
    // +++ ГЕТТЕРЫ И СЕТТЕРЫ + КОНСТРУКТОР
    public NetworkToolsImpl() {
        ListenersGUI = new CopyOnWriteArrayList<>();
        ListenersProgress = new CopyOnWriteArrayList<>();
        myParamenters   = MyParameters.getCurrentInstance();
        myMessaging     = MyMessaging.getCurrentInstance();
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
    public void treeModel_refreshNode(String nodGUI, boolean isActive) {
    }

    // ошибка при передаче/получении файла
    public void fileTransferError(String fileName, Node node, String textError) {
    }

    // окончание передачи/получения файла
    public void fileTransferOk(String fileName, Node node) {
    }

    // --- ОБРАБОТКА СОБЫТИЙ
    // +++ ОБЩИЕ МЕТОДЫ
    public void sendFile(String fileName, Node node) {
        // посылает файл конкретному узлу
    }
    // --- ОБЩИЕ МЕТОДЫ

    /**
     * @return the myParamenters
     */
    public MyInterfaceParameters getMyParamenters() {
        return myParamenters;
    }

    /**
     * @param myParamenters the myParamenters to set
     */
    public void setMyParamenters(MyInterfaceParameters myParamenters) {
        this.myParamenters = myParamenters;
    }

    /**
     * @return the myMessaging
     */
    public MyInterfaceMessage getMyMessaging() {
        return myMessaging;
    }

    /**
     * @param myMessaging the myMessaging to set
     */
    public void setMyMessaging(MyInterfaceMessage myMessaging) {
        this.myMessaging = myMessaging;
    }
    
}
