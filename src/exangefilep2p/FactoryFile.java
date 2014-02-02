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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author dav
 */
public class FactoryFile {
    private String fileName;
    private boolean isActive;
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
    private MyInterfaceMessage myMessage;
    private MyInterfacePakageQueue myPakageQueue;
    // крипто
    private byte[] hashKodeFile;// полный хэш код файла
    MessageDigest md5 ;// 
    //private 
    // добавим очередь исходящую
    
    public void FactoryFile(){
        setCountPakage(0);
        repeatPakage = new ArrayDeque();
        isStarted = false;
        //myBuffer = new ByteBuffer();
        myBuffer = ByteBuffer.allocate(getCountByte());
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
        } catch (NoSuchAlgorithmException ex) {
            md5 = null;
            myMessage.setMessage(TypeOfMessage.log, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorWichMD5"));
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        hashKodeFile = null;
    }
    
    /**
     * Метод служит для освобождения занятых ресурсов
     */
    public void DestroyFactory(){
        if (isSend) {
            if (myBuffer!=null) myBuffer= null;
            if (fis!=null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                    myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCloseFile"), ex);
                }
            }
        }
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
            myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("FileNotFound"));
            isActive = false;
            rez = false;
        }
        if (fis!=null) {
            try {
                currentFileSize = reader.size();
            } catch (IOException ex) {
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("getSizeOfFIle"),ex);
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
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorSetPositionInCHanel"), ex);
        }
    }
    
    // методы для формирования пакетов
    private MyPakage getPakage(MyPakage.TypeMessage typeMessage) {
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
            int bytes = -1;
            try {
                bytes = reader.read(myBuffer);
            } catch (IOException ex) {
                //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorReadingFile"),ex);
            }
            if (bytes!=-1) {
                myBuffer.flip();
                if (myBuffer.hasRemaining()) {
                    myPakage.setData(myBuffer.array());
                    myPakage.setNumberPakage(countPakage);
                    countPakage++;
                    myPakage.setTypeMessage(MyPakage.TypeMessage.data);
                }
            } else
            {
                // достигут конец файла сформируем соответсвующее сообщение
                myPakage.setTypeMessage(MyPakage.TypeMessage.endOfFile);
                myPakage.setNumberPakage(0);
                // поместим хэш в пакет
                if (hashKodeFile==null) hashKodeFile = md5.digest();
                myPakage.setData(hashKodeFile);
            }   
            //myPakage.setData(reader.read(cbuf, countPakage, countPakage));
        }
        return myPakage;
    }
    
    // метод добавляет в очередь следующий пакет
    public void sendNextPackage() 
    {
        // три случая: еще не начали отправку, в середине файла, в конце файла
        //long currentOffset = getCurrentOffsetFile();
        if (myPakageQueue == null) {
            myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorNotInitialized"));
            return;
        }
        MyPakage myPakage = null;
        if (!isStarted) {
            // вначале отправим пакет с информацией о том что начинаем отправку файла
            myPakage = getPakage(MyPakage.TypeMessage.sendFile);
        } else {
            //посылаем данные или окончание файла
            myPakage = getPakage(MyPakage.TypeMessage.data);
        }
          
        if (myPakage!=null) {
                // добавим его в очередь пакетов
                if (myPakageQueue.putPakage(myPakage)) {
                //!listOfmessage.put(myPakage);
                // необходимо учесть когда отправляем повторные пакеты и не включать сюда хэш
                    if (myPakage.getTypeMessage()== MyPakage.TypeMessage.data&&repeatPakage.isEmpty()) {
                        // проверим не равняется ли нулю менеджер хэша
                        if (md5 != null) {
                            // прибавим наши данные
                            md5.update(myPakage.getData());
                        }
                        else
                        {
                            myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorInitializingMD5"));
                        }
                    } 
                } else countPakage--; // удаляем пакеты из очереди повтора именно здесь
                
       }
    }

    /**
     * @param myMessage the myMessage to set
     */
    public void setMyMessage(MyInterfaceMessage myMessage) {
        this.myMessage = myMessage;
    }

    /**
     * @param myPakageQueue the myPakageQueue to set
     */
    public void setMyPakageQueue(MyInterfacePakageQueue myPakageQueue) {
        this.myPakageQueue = myPakageQueue;
    }
    
}
