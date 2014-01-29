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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * пакет для передачи данных
 * @author dav
 */
public class MyPakage {

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
    enum TypeMessage {requestSendFile,acceptRequestSendFile,test,requesNewSocket,responsRequestNewSoket,sendFile,data,endOfFile} 

    private Node nodeDestination;
    private Node nodeSourde;
    private static int countByteInPakage;
    private byte[] data;
    private String hash;
    private String fileName;    //имя файла связанное с пакетом
    private TypeMessage typeMessage;
    private int numberPakage; // номер пакета для идентификации
    /**
     * @return the typeMessage
     */
    public TypeMessage getTypeMessage() {
        return typeMessage;
    }

    /**
     * @param typeMessage the typeMessage to set
     */
    public void setTypeMessage(TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }
    
    public void MyPakage(){
        if (countByteInPakage<=0) countByteInPakage = 1024;
        data = new byte[countByteInPakage];
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
    
    public static MyPakage loadFromByte(byte[] mData){
        ByteArrayInputStream bis = new ByteArrayInputStream(mData);
        ObjectInput in = null;
        MyPakage o = new MyPakage();
        try {
                in = new ObjectInputStream(bis);
          o = (MyPakage)in.readObject(); 
        }  catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MyPakage.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
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
        }  
        return o;
    }

    
}
