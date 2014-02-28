/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * пакет для передачи данных
 * @author dav
 */
public class MyPakage implements Serializable {
    /**
     * @return the numberPakage
     */
    public int getNumberPakage() {
        return numberPakage;
    }

    /**
     * @param numberPakage the numberPakage to set
     */
    public void setNumberPakage(int numberPakage) {
        this.numberPakage = numberPakage;
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

    enum TypeMessagePakage {requestSendFile,acceptRequestSendFile,test,requesNewSocket,responsRequestNewSoket,sendFile,data,endOfFile} 

    private Node nodeDestination;
    private Node nodeSourde;
    private static int countByteInPakage;
    private byte[] data;
    private String hash;
    private String fileName;    //имя файла связанное с пакетом
    private TypeMessagePakage typeMessage;
    private int numberPakage; // номер пакета для идентификации
    /**
     * @return the typeMessage
     */
    public TypeMessagePakage getTypeMessage() {
        return typeMessage;
    }

    /**
     * @param typeMessage the typeMessage to set
     */
    public void setTypeMessage(TypeMessagePakage typeMessage) {
        this.typeMessage = typeMessage;
    }
    
    private void commonConstrucotr(){
        if (countByteInPakage<=0) countByteInPakage = 1024;
        data = new byte[countByteInPakage];
    }
    
    public MyPakage(){
        commonConstrucotr();
    }
    public MyPakage(MyPakage pakageSource){
        commonConstrucotr();
        nodeDestination = pakageSource.getNodeSourde();
        nodeSourde      = pakageSource.getNodeDestination();
        fileName        = pakageSource.getFileName();
        numberPakage    = 0;
    }
    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash() {
        this.hash = calculateHash(data);
    }    
    
    public Node getNodeDestination() {
        return nodeDestination;
    }

    public Node getNodeSourde() {
        return nodeSourde;
    }

    public void setNodeDestination(Node nodeDestination) {
        this.nodeDestination = nodeDestination;
    }

    public void setNodeSourde(Node nodeSourde) {
        this.nodeSourde = nodeSourde;
    }
   
    /**
     * @return the countByteInPakage
     */
    public static int getCountByteInPakage() {
        return countByteInPakage;
    }

    /**
     * @param aCountByteInPakage the countByteInPakage to set
     */
    public static void setCountByteInPakage(int aCountByteInPakage) {
        countByteInPakage = aCountByteInPakage;
    }
    
    private String calculateHash(byte[] data) {
        return "not realize yet";
    }
    
    public boolean IsHashRight(){
        // return hash summ of data
        return hash.equals(calculateHash(data))?true:false;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }
    
    public boolean putObject(Object o) {
        data = toByte(o);
        return true;
    }
    
    public static byte[] toByte(Object o) {
        //http://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] arrayOfByte = new byte[0];
        try {
            out = new ObjectOutputStream(bos);   
            out.writeObject(o);
            arrayOfByte = bos.toByteArray();
          } catch (IOException ex) {
            }
            finally {
            try {
              if (out != null) {
                out.close();
              }
            } catch (IOException ex) {
              // ignore close exception
            }
            try {
              bos.close();
            } catch (IOException ex) {
              // ignore close exception
            }
          }  
        return arrayOfByte;
    }
    
    public byte[] toByte(){
        return toByte(this);
    }
    
    public static MyPakage loadFromByte(byte[] mData) throws ClassNotFoundException {
        
        //ObjectInput in = null;
        MyPakage o = new MyPakage();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(mData); ObjectInput in = new ObjectInputStream(bis);) {
          o = (MyPakage)in.readObject(); 
        }  catch (IOException ex) {
                //Logger.getLogger(MyPakage.class.getName()).log(Level.SEVERE, null, ex);
           MyMessaging.getCurrentInstance().setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorDeserializePakage"), ex);
        }

        /*finally {
          try {
            bis.close();
          } catch (IOException ex) {
            // ignore close exception
          }
          try {
            if (in != null) {
              in.close();
            }
          } catch (IOException ex) {
            // ignore close exception
          }
        }  */
        return o;
    }
    public String getDataAsString() throws UnsupportedEncodingException{
        return new String(data, "UTF-8");
    }
    
    public void setStringAsData(String myData) throws UnsupportedEncodingException {
        data = myData.getBytes("UTF-8");
    }
    
    public boolean getDataAsBoolean(){
        boolean rez = false;
        try {
            String temp = getDataAsString();
            rez = temp.equals("true");
        } catch (UnsupportedEncodingException ex) {
            MyMessaging.getCurrentInstance().setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorGetStringFromDataPakage"), ex);
            //Logger.getLogger(MyPakage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rez;
    }
    public void setDataAsBoolean(boolean source){
        try {
            setStringAsData(source?"true":"false");
        } catch (UnsupportedEncodingException ex) {
            MyMessaging.getCurrentInstance().setMessage(java.util.ResourceBundle.getBundle("exangefilep2p/Bundle").getString("errorGetStringFromDataPakage"), ex);
            //Logger.getLogger(MyPakage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
