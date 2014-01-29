/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dav
 */
public class FactoryFile {
    private String fileName;
    private boolean isActive;
    private LinkedBlockingDeque<MyPakage> listOfmessage;
    private Node nodeDestination;
    private Node nodeSource;
    private SeekableByteChannel reader;
    private int countPakage;            // номер пакета из файла оригинала
    private ArrayDeque<Integer> repeatPakage;    // пакеты требующие повторной отправки
    private boolean isStarted;  // признак того что фабрика уже отправляет пакеты
    private long currentFileSize;
    private ByteBuffer myBuffer;
    private FileInputStream fis;
    private boolean isSend; // признак того что фабрика для передачи файла
    //private 
    // добавим очередь исходящую
    
    public void FactoryFile(){
        listOfmessage = new LinkedBlockingDeque<>();
        setCountPakage(0);
        repeatPakage = new ArrayDeque();
        isStarted = false;
        //myBuffer = new ByteBuffer();
        myBuffer = ByteBuffer.allocate(getCountByte());
    }
    
    // получим текущее смещение в файла
    private long getCurrentOffsetFile() {
        return countPakage*MyPakage.getCountByteInPakage()+1;
    }
          
    
    public boolean FactoryFile(String path, boolean isSend)   {
        boolean rez = true;
        this.isSend = isSend;
        FactoryFile();
        fis = null;
        try {
            fis = new FileInputStream(path);
            reader = fis.getChannel();
        } catch (FileNotFoundException ex) {
            MyMessaging.getCurrentInstance().setMessage(MyMessaging.TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("FileNotFound"));
            isActive = false;
            rez = false;
        }
        if (fis!=null) {
            try {
                currentFileSize = reader.size();
            } catch (IOException ex) {
                MyMessaging.getCurrentInstance().setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("getSizeOfFIle"),ex);
                isActive = false;
                rez = false;
//                Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        //Charset charset = Charset.forName("UTF-8");
        //Path file;
        //file = Paths.get(path);
        return rez;
    }

/*    public void FactoryFile(SeekableByteChannel reader)  {
        FactoryFile();
        this.reader = reader;
    }*/
    
    // по скольку байт в пакете
    private int getCountByte(){
        return MyPakage.getCountByteInPakage();
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the isFinished
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @return the nodeDestination
     */
    public Node getNodeDestination() {
        return nodeDestination;
    }

    /**
     * @param nodeDestination the nodeDestination to set
     */
    public void setNodeDestination(Node nodeDestination) {
        this.nodeDestination = nodeDestination;
    }

    /**
     * @return the nodeSource
     */
    public Node getNodeSource() {
        return nodeSource;
    }

    /**
     * @param nodeSource the nodeSource to set
     */
    public void setNodeSource(Node nodeSource) {
        this.nodeSource = nodeSource;
    }

    /**
     * @return the countPakage
     */
    public int getCountPakage() {
        return countPakage;
    }

    /**
     * @param countPakage the countPakage to set
     */
    public void setCountPakage(int countPakage) {
        this.countPakage = countPakage;
        try {
            // cпозиционируем канал на нужном размере
            reader.position(getCurrentOffsetFile());
        } catch (IOException ex) {
            Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // методы для формирования пакетов
    private MyPakage getPakage(MyPakage.TypeMessage typeMessage) throws IOException{
        MyPakage myPakage = new MyPakage();
        myPakage.setTypeMessage(typeMessage);
        //myPakage.setNumberPakage(0);
        myPakage.setNodeSourde(nodeSource);
        myPakage.setNodeDestination(nodeDestination);
        if (typeMessage == MyPakage.TypeMessage.sendFile || typeMessage == MyPakage.TypeMessage.endOfFile)
        {
            myPakage.setNumberPakage(0);
        } else {
            // данные
            int rez = reader.read(myBuffer);
            myPakage.setData(reader.read(cbuf, countPakage, countPakage));
        }
        return myPakage
    }
    
    // метод добавляет в очередь следующий пакет
    public void sendNextPackage() 
    {
        // три случая: еще не начали отправку, в середине файла, в конце файла
        //long currentOffset = getCurrentOffsetFile();
        if (!isStarted) {
            // вначале отправим пакет с информацией о том что начинаем отправку файла
            
        } else {
            //посылаем данные или окончание файла
            
        }
          
        
    }
    
}
