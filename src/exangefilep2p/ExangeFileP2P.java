/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exangefilep2p;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author dav
 */
public class ExangeFileP2P extends javax.swing.JFrame  implements MyGUI {
    private NetworkToolsImpl currentNetworkTools;

   /**
     *
     * @param nameFile
     * @param узел 
     * @return возвращает имя фай
     */
    @Override
    public String saveFile(String nameFile, Node myNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recieveFile(String nameFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void errorAccesToFile(String fileName, Exception ex) {
        System.out.println("Error acces to file");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String qeustionRecieveFIle(String fileName, Node from) {
        String testPath= "D:\\";
        return testPath;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // ACTION
    public class ActionRefresh extends javax.swing.AbstractAction  {
        /**
         *
         * @throws IOException
         */
        public ActionRefresh() 
        {
            //super(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("textOption"),null);
            javax.swing.ImageIcon myIcon = null;
            /*try {
                myIcon = getIconFromImage(ImageIO.read((getClass().getResource("image/image_options.png"))));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.putValue(SMALL_ICON, myIcon);*/
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // this we do refresh
            
        }
    }
    
    public class SendUnit extends javax.swing.AbstractAction  {
        /**
         *
         * @throws IOException
         */
        public SendUnit() 
        {
            //super(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("textOption"),null);
            javax.swing.ImageIcon myIcon = null;
            /*try {
                myIcon = getIconFromImage(ImageIO.read((getClass().getResource("image/image_options.png"))));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.putValue(SMALL_ICON, myIcon);*/
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // this we send file or folder
            
        }
    }
    
    // END OF ACTION
    
    private void myInit(){
//        currentNetworkTools = OwnFactory.getCurrentInstace().getNerworkTools();
    }
    /**
     * Creates new form NewJFrame
     */
    public ExangeFileP2P() {
        initComponents();
        myInit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jBServer = new javax.swing.JButton();
        JBClient = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jTree1);

        jBServer.setText("Server");
        jBServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBServerActionPerformed(evt);
            }
        });

        JBClient.setText("Client");
        JBClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JBClient)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBServer)
                    .addComponent(JBClient))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBServerActionPerformed
        // TODO add your handling code here:
        this.JBClient.setEnabled(false);
        MyParameters.getCurrentInstance().setServer(true);
        currentNetworkTools = OwnFactory.getCurrentInstace().getNerworkTools();
        currentNetworkTools.setMyGUI(this);
    }//GEN-LAST:event_jBServerActionPerformed
    
    @Override
    public void reciveDebugging(String message){
        JOptionPane.showMessageDialog(null, message);
    }
    private void JBClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBClientActionPerformed
        // TODO add your handling code here:
        this.jBServer.setEnabled(false);
        MyParameters.getCurrentInstance().setServer(false);
        currentNetworkTools = OwnFactory.getCurrentInstace().getNerworkTools();
        currentNetworkTools.setMyGUI(this);
        currentNetworkTools.sendDebugPakage();
        currentNetworkTools = null; // destroy object
    }//GEN-LAST:event_JBClientActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExangeFileP2P.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ExangeFileP2P().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBClient;
    private javax.swing.JButton jBServer;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
