/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import exangefilep2p.MyPakage.TypeMessagePakage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;

/**
 *
 * @author dav
 */
public class FactoryFile {
    private String fileName;
    private boolean isActive;
    private Node nodeDestination;
    private Node nodeSource;
    private SeekableByteChannel myByteChannel;
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
    
    
    //++++++ CONSTRUCITON AND INJECTION
    public void FactoryFile(){
        setCountPakage(0);
        setRepeatPakage((ArrayDeque<Integer>) new ArrayDeque());
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
            myByteChannel = fis.getChannel();
        } catch (FileNotFoundException ex) {
            myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("FileNotFound"));
            isActive = false;
            rez = false;
            return rez;
        }
        if (fis!=null) {
            try {
                currentFileSize = myByteChannel.size();
            } catch (IOException ex) {
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("getSizeOfFIle"),ex);
                isActive = false;
                rez = false;
                return rez;
//                Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        //Charset charset = Charset.forName("UTF-8");
        //Path file;
        //file = Paths.get(path);
        isActive = true;
        return rez;
    }

  private String getTempFileName() {
      return "";
  }
    //------ CONSTRUCITON AND INJECTION
/*    public void FactoryFile(SeekableByteChannel reader)  {
        FactoryFile();
        this.reader = reader;
    }*/
    
    // по скольку байт в пакете
    //++++++ SETTERS AND GETTERS
    
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
            myByteChannel.position(getCurrentOffsetFile());
        } catch (IOException ex) {
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorSetPositionInCHanel"), ex);
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

    /**
     * @return the repeatPakage
     */
    public ArrayDeque<Integer> getRepeatPakage() {
        return repeatPakage;
    }

    /**
     * @param repeatPakage the repeatPakage to set
     */
    public void setRepeatPakage(ArrayDeque<Integer> repeatPakage) {
        this.repeatPakage = repeatPakage;
        isActive = true;
    }
    
    
    //------ SETTERS AND GETTERS

    //++++++ FUNCTION FOR SEND DATA
    
    private byte[] getDataFromFile() {
        int bytes = -1;
        byte[] rez = null;
        try {
            bytes = myByteChannel.read(myBuffer);
            
        } catch (IOException ex) {
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorReadingFile"),ex);
        }
        if (bytes!=-1) {
            myBuffer.flip();
            if (myBuffer.hasRemaining()) {
                rez = myBuffer.array();
            }
        } 
        return rez;
    }
    
    
    // методы для формирования пакетов
    private MyPakage getPakage(TypeMessagePakage typeMessage) {
        MyPakage myPakage = new MyPakage();
        boolean isRepeating = !repeatPakage.isEmpty();
        myPakage.setTypeMessage(typeMessage);
        //myPakage.setNumberPakage(0);
        myPakage.setNodeSourde(nodeSource);
        myPakage.setNodeDestination(nodeDestination);
        if (typeMessage == TypeMessagePakage.sendFile || typeMessage == TypeMessagePakage.endOfFile)
        {
            myPakage.setNumberPakage(0);
            if (typeMessage==TypeMessagePakage.endOfFile) {
                myPakage.setData(hashKodeFile);
            }
        } else {
             if (isRepeating) {
                 // this is repeting pakage
                 
                 setCountPakage(getRepeatPakage().element());
                 byte[] mData = getDataFromFile();
                 if (mData!=null) {
                    myPakage.setData(mData);
                    myPakage.setNumberPakage(countPakage);
                    myPakage.setTypeMessage(TypeMessagePakage.data);
                 }
             }else {
                // save data to pakage
                byte[] mData = getDataFromFile();
                if (mData!=null) {
                    myPakage.setData(mData);
                    myPakage.setNumberPakage(countPakage);
                    countPakage++;
                    myPakage.setTypeMessage(TypeMessagePakage.data);
                } else {
                    // is end of file
                    myPakage.setTypeMessage(TypeMessagePakage.endOfFile);
                    myPakage.setNumberPakage(0);
                    // поместим хэш в пакет
                    if (hashKodeFile==null) hashKodeFile = md5.digest();
                    myPakage.setData(hashKodeFile);

                }
                /*
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
                        myPakage.setTypeMessage(TypeMessage.data);
                    }
                } else
                {
                    // достигут конец файла сформируем соответсвующее сообщение
                    myPakage.setTypeMessage(TypeMessage.endOfFile);
                    myPakage.setNumberPakage(0);
                    // поместим хэш в пакет
                    if (hashKodeFile==null) hashKodeFile = md5.digest();
                    myPakage.setData(hashKodeFile);
                }   */
                
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
        boolean isRepeating = !repeatPakage.isEmpty();
        if (myPakageQueue == null) {
            myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorNotInitialized"));
            return;
        }
        MyPakage myPakage = null;
        if (!isStarted) {
            // вначале отправим пакет с информацией о том что начинаем отправку файла
            myPakage = getPakage(TypeMessagePakage.sendFile);
        } else {
            //посылаем данные или окончание файла
            myPakage = getPakage(TypeMessagePakage.data);
        }
          
        if (myPakage!=null) {
                // добавим его в очередь пакетов
                if (myPakageQueue.putPakage(myPakage)) {
                //!listOfmessage.put(myPakage);
                // необходимо учесть когда отправляем повторные пакеты и не включать сюда хэш
                    if (myPakage.getTypeMessage()==TypeMessagePakage.endOfFile) isActive=false;
                    if (myPakage.getTypeMessage()== TypeMessagePakage.data&&!isRepeating) {
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
                    getRepeatPakage().pop(); // remove repeated element
                    if (isRepeating&&getRepeatPakage().isEmpty()) {
                        myPakage = getPakage(TypeMessagePakage.endOfFile);
                        if (myPakageQueue.putPakage(myPakage)) isActive = false;
                    }
                } else countPakage--; // удаляем пакеты из очереди повтора именно здесь
                
       }
    }
    //------ FUNCTION FOR SEND DATA

    //++++++ FUNCTION FOR RECIVE DATA
public boolean processingPakage(MyPakage myPakage) {
    // 
    boolean rez = false;
    if (myPakage.getNodeDestination().equals(nodeSource)&&myPakage.getFileName().equals(fileName)) {
        // our pakage, lets go
        
        rez = true;
    }
    return rez;
}
    
    //------ FUNCTION FOR RECIVE DATA


    
}
