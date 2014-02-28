/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import exangefilep2p.MyPakage.TypeMessagePakage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private BlockingQueue<Integer> repeatPakage;    // пакеты требующие повторной отправки
    private boolean isStarted;  // признак того что фабрика уже отправляет пакеты
    private long currentFileSize;
    private ByteBuffer myBuffer;
    private ByteBuffer myWriteBuffer;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private boolean isSend; // признак того что фабрика для передачи файла
    private MyInterfaceMessage myMessage;
    private MyInterfacePakageQueue myPakageQueue;
    private ArrayList<Integer> correctPakage;// array get correct pakage
    private final String tempPath = MyParameters.getCurrentInstance().getTempPath();// temp path for saving temporarly fail
    private final String SUFFICS = "_w";// суффикс для файла содержащего пакеты
    private String destinationsPath;
    // крипто
    private byte[] hashKodeFile;// полный хэш код файла
    MessageDigest md5 ;// 
    //private 
    // добавим очередь исходящую
    
    
    //++++++ CONSTRUCITON AND INJECTION
    // metod check inner data for korrection 
    private boolean isDataCorrect(){
      boolean error = false;
      if (isSend){
          // send files
        if (fis==null) {
            error = true;
            myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("notInitializedFileOutputStream"));
         }
      } else {
          // recive files
        Path pathTemp = Paths.get(tempPath);
        if (!Files.exists(pathTemp, LinkOption.NOFOLLOW_LINKS)) {
              error = true;
              myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorTempDirectoryDoNotExist"));
        } else if (!Files.isDirectory(Paths.get(tempPath), LinkOption.NOFOLLOW_LINKS)) {
              myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorTempPathIsNotDirectory"));
              error = true;
        }
        if (fos==null) {
            error = true;
            myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorNotInitializedFileOutputStream"));
        }
        if (oos==null) {
            error = true;
            myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorInitializedParameters")+"oos");
        }
        
      }
      // general 
      if (getFileName().isEmpty()) {error = true;
        myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorNotInitializeFileNameForFactoryFIle"));
      }
      if (myBuffer==null) {
          error = true;
          myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("notInitializedBufferForReadFile"));
      }
      if (myWriteBuffer==null) {
          error = true;
          myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("notInitializedBufferForReadFile"));
      }
      if (myByteChannel==null) {
          error = true;
          myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("notInitializingByteChannel"));
      }
      if (md5==null) {
          error = true;
          myMessage.setMessage(TypeOfMessage.logError,java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("notInitializedMD5"));
      }
      
      
      return !error;
    }
    
    public boolean FactoryFile(){
        setCountPakage(0);
        setRepeatPakage((BlockingQueue<Integer>) new PriorityBlockingQueue());
        //myBuffer = new ByteBuffer();
        try {
            myBuffer = ByteBuffer.allocate(getCountByte());
            myWriteBuffer = ByteBuffer.allocate(getCountByte()+2); // initializing buffer for data and int - number pakage
        }
        catch(IllegalArgumentException ex) {
            // check initialized in spesial procedur
        }
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
        } catch (NoSuchAlgorithmException ex) {
            md5 = null;
            myMessage.setMessage(TypeOfMessage.log, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorWichMD5"));
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
        }
        myByteChannel = isSend?fos.getChannel():fis.getChannel();
        isStarted = isDataCorrect();
        return isStarted;
    }

    public FactoryFile(String path)   {
        // this is conctructor only for send data
        boolean rez = true;
        isSend = true;
        fis = null;
        
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            myMessage.setMessage(TypeOfMessage.error, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("FileNotFound"));
            //isActive = false;
            //rez = false;
  //          return false;
        }
        if (fis!=null) {
            try {
                currentFileSize = myByteChannel.size();
            } catch (IOException ex) {
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("getSizeOfFIle"),ex);
                //isActive = false;
                //rez = false;
//                return false;
//                Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        hashKodeFile = null;
        FactoryFile();
    }
    

    public FactoryFile(MyPakage myPakage) {
        // конструктор на основании пакет начала передачи файлов
        setFileName(myPakage.getFileName());
        //tempPath = MyParameters.getCurrentInstance().getTempPath();
        // initializing prevous downloadin if they possible
        correctPakage = new ArrayList<>();
        Path checkPath = Paths.get(getTempFileName());
        if (!Files.exists(checkPath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                    Files.createFile(checkPath, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxr-x---")));
            } catch (FileAlreadyExistsException ex) {
                myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorFileAlreadyExist"));
            }
             catch (IOException ex) {
                myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorIOForCreateTempFile"));
            }
            catch (SecurityException ex) {
                myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreateTempFileNotPermission"));
            }
        }
        try {
            fos = new FileOutputStream(getTempFileName());
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorOpenOutputStream"), ex);
        }
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException ex) {
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreateOutputObjectStream"), ex);
            //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        FactoryFile();
    }    
    /**
     * Метод служит для освобождения занятых ресурсов
     */
    public void DestroyFactory(){
        if (isSend) {
            if (myBuffer!=null) myBuffer= null;
        }
        if (fis!=null) {
            try {
                fis.close();
            } catch (IOException ex) {
                //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCloseFile"), ex);
            }
        }
        if (fos!=null) {
            try {
                fos.close();
            } catch (IOException ex) {
                //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCloseFile"), ex);
            }
        }
        if (myByteChannel!=null) {
            try {
                fis.close();
            } catch (IOException ex) {
                //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCloseFile"), ex);
            }
        }
        /*!if (oos!=null) {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        
    }
          

    


    //------ CONSTRUCITON AND INJECTION
/*    public void FactoryFile(SeekableByteChannel reader)  {
        FactoryFile();
        this.reader = reader;
    }*/
    
    // по скольку байт в пакете
    //++++++ SETTERS AND GETTERS
    // получим текущее смещение в файла
  private long getCurrentOffsetFile() {
      return countPakage*MyPakage.getCountByteInPakage()+1;
  }

  private String getTempFileName() {
      return Paths.get(tempPath, getFileName()).toString()+SUFFICS;
  }

  private Path getDestinationsFileName(){
      return Paths.get(getDestinationsPath(), getFileName());
  }
  
  private void addGetNumberPakage(int numPakage) {
        if (correctPakage.indexOf(numPakage)==-1&&numPakage>0) correctPakage.add(numPakage);
    }
    
  private void fillRepeatNumber(int countPakage) {
        Collections.sort(correctPakage);
        //Iterator myIterator = correctPakage.iterator();
        int prevous = 0;
        for (Integer myIndex:correctPakage) {
            while ((prevous++)<myIndex.intValue()) {
                try {
                    repeatPakage.put(prevous);
                } catch (InterruptedException ex) {
                    //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                    myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorForRepeatedPakage"),ex);
                }
            }
      }
        
        // далее обходим коллекцию
  }
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
        if (isSend) {
            try {
                // cпозиционируем канал на нужном размере
                myByteChannel.position(getCurrentOffsetFile());
            } catch (IOException ex) {
                //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
                myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorSetPositionInCHanel"), ex);
            }
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
    public BlockingQueue<Integer> getRepeatPakage() {
        return repeatPakage;
    }

    /**
     * @param repeatPakage the repeatPakage to set
     */
    public void setRepeatPakage(BlockingQueue<Integer> repeatPakage) {
        this.repeatPakage = repeatPakage;
        isActive = true;
    }
    
    /**
     * @return the destinationsPath
     */
    public String getDestinationsPath() {
        return destinationsPath;
    }

    /**
     * @param destinationsPath the destinationsPath to set
     */
    public void setDestinationsPath(String destinationsPath) {
        this.destinationsPath = destinationsPath;
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
                    myPakage.setNumberPakage(countPakage+1);
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
                    getRepeatPakage().poll(); // remove repeated element
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
    TypeMessagePakage myType = myPakage.getTypeMessage();
    if (myPakage.getNodeDestination().equals(nodeSource)&&myPakage.getFileName().equals(fileName)) {
        // our pakage, lets go
        switch(myType){
            case data: {
                rez = reciveDataPakage(myPakage);
            }
            case endOfFile: {
                // recive end of file, check recived number of pakage and compile file
                rez = true;
                if (repeatPakage.isEmpty()){
                    fillRepeatNumber(myPakage.getNumberPakage());
                    if (repeatPakage.isEmpty()) {
                        // its ok, we recieve and save all pakage, next step compile destinations file
                        compileFile();
                    }
                }
            }
        }
    }
    return rez;
}

// possible optimizations
private boolean compileFile(){
    boolean rez= false;
    try {
        // close the byte channel
        myByteChannel.close();
    } catch (IOException ex) {
        //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCloseTheByteChanel"),ex);
        return rez;
    }
    // after all, read all pakage and compile destinations file
    Path fileDst = getDestinationsFileName();
    if (Files.notExists(fileDst, LinkOption.NOFOLLOW_LINKS)) {
        try {
            Files.createFile(fileDst, PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxr-x---")));
        } catch (FileAlreadyExistsException ex) {
                myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorFileAlreadyExist"));
                return false;
        }
             catch (IOException ex) {
                myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreatingDestinationsFile"));
                return false;
        }
            catch (SecurityException ex) {
                myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreatingDestinationsFileNotPermissions"));
                return false;
        }
        
    }
    try (FileInputStream myFis = new FileInputStream(getDestinationsFileName().toString()); SeekableByteChannel myReadByteChannel = myFis.getChannel();){
        //FileInputStream myFis = new FileInputStream(getDestinationsFileName().toString());
        //long currentPositions = 0;
        //MyPakage currentPakage=null;
        myByteChannel.close();
        ArrayList<Long> listOfPakageInFile = new ArrayList<>();
        
        int tempCurrentPakage = 0;
        int countReadByte = 0;
        // read the file and set offset for all pakage
        do {
            countReadByte = myReadByteChannel.read(myWriteBuffer);
            myWriteBuffer.flip();
            tempCurrentPakage = myWriteBuffer.getInt();
            while (tempCurrentPakage>listOfPakageInFile.size()) {
                listOfPakageInFile.add(-1L);
            }
            listOfPakageInFile.set(tempCurrentPakage, myReadByteChannel.position());
        } while (countReadByte>0);
        // read number pakage, offset and data from file
        myReadByteChannel.position(0L);
        fos.close();
        // create file
        
        fos = new FileOutputStream(getDestinationsFileName().toString());
        myByteChannel = fos.getChannel();
        //byte[] myTempByteBuffer = new byte[getCountByte()];
        md5.reset();
        for (long offset :listOfPakageInFile) {
            // read data
            myReadByteChannel.position(offset+2); //+2 - this is offset integer - number pakage
            myReadByteChannel.read(myBuffer);
            myBuffer.flip();
            //myWriteBuffer.get(myTempByteBuffer);
            // save data
            myByteChannel.write(myBuffer);
            md5.update(myBuffer);
        }
        myByteChannel.close();
            
        
    
    } catch (FileNotFoundException ex) {
           //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreatingDestinationsFile"));
        return false;
    } catch (StreamCorruptedException ex) {
           //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreatingInputObjectStream_InvalidHeader"));
        return false;
    } catch (SecurityException ex) {
           //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreatingInputObjectStream_NotPermission"));
        return false;
    } catch (NullPointerException ex) {
           //Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
        myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorCreatingInputObjectStream_NullPointer"));
        return false;
    } 
    catch (IOException ex) {
        myMessage.setMessage(TypeOfMessage.logError, java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorIO"));
        return false;
    }
      //catch (IO)
    
    
    return rez;
}

private boolean reciveDataPakage(MyPakage myPakage) {
    boolean rez=false;
    if (myPakage.IsHashRight()) {
        try {
            
            // если пакет правильный - запишем его в файл
            //myBuffer.put(myPakage.toByte());
            //myBuffer.flip();
            //myByteChannel.write(myBuffer); //.write(myPakage.toByte());
            //oos.writeObject(myPakage);
            // write pakagt to file
            myWriteBuffer.flip();
            myWriteBuffer.putInt(countPakage);
            myWriteBuffer.put(myPakage.getData());
            myByteChannel.write(myWriteBuffer);
            //Integer.
            addGetNumberPakage(myPakage.getNumberPakage());
            //oos.flush();
        } catch (InvalidClassException ex) {
         //   Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorWriteObjectToFile_errorInClass"), ex);
        }catch (NotSerializableException ex) {
         //   Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorWriteObjectToFile_ClassIsNotSeriazable"), ex);
        }catch (IOException ex) {
         //   Logger.getLogger(FactoryFile.class.getName()).log(Level.SEVERE, null, ex);
            myMessage.setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorWriteObjectToFile_IO"), ex);
        }
    }
    return rez;
}
    //------ FUNCTION FOR RECIVE DATA

    

    
}
