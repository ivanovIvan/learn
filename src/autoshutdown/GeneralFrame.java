/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autoshutdown;

import autoshutdown.MyActionFactory.MyAction;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SpinnerNumberModel;
/**
 *
 * @author dav
 */
public class GeneralFrame extends javax.swing.JFrame {
    public int minCount = 0;    // минимальная задержка
    public int maxCount = 600;  // максимальная задержка
    public int step     = 10;    // шаг изменения
    public int firstCount = 7;  // количество строк в "быстром" запуске
    public MyTimer myTimer;
    public boolean pausa;
    //public int currentTime;
    BufferedImage myOwnImage;
    BufferedImage myPauseImage;
    TrayIcon myTrayIcon;
    MyAction currentAction;
    final SystemTray tray;
    JPopupMenu popup;
    SimpleAction myListener;
    ActionExit actionExit;
    ActionStart actionStart;
    ActionPause actionPause;
    SimpleAction simpleAction;
    
    public void setCurrentTime(int mCurrentTime){
        mTimeShutDown.setValue(mCurrentTime);
        //currentTime = mCurrentTime;
        
    }
    public int getCurrentTime(){
        return (int)mTimeShutDown.getValue();
        //return currentTime;
    }
    // блок работы с действиями
    public ImageIcon getIconFromImage(Image img) {
//        ImageIcon icon = (ImageIcon)jButton.getIcon();
//        Image img = icon.getImage() ;  
        Image newimg = img.getScaledInstance( 16, 16,  java.awt.Image.SCALE_SMOOTH ) ;  
        return new ImageIcon( newimg );        
    }
    
    public class SimpleAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem mMenuItem = (JMenuItem)e.getSource();
            setCurrentTime(Integer.parseInt(mMenuItem.getName()));
            String mLabel = ((JPopupMenu)mMenuItem.getParent()).getLabel();
            GeneralFrame.this.mTimeShutDown.setValue(getCurrentTime());
            if (mLabel==null||(!mLabel.equals("WindowsPopup"))) actionStart.actionPerformed(e);
        }
       
    }
    
    public class ActionExit extends javax.swing.AbstractAction  {
        /**
         *
         * @throws IOException
         */
        public ActionExit() 
        {
            super(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionExit"),null);
            javax.swing.ImageIcon myIcon = null;
            try {
                myIcon = getIconFromImage(ImageIO.read((getClass().getResource("image/image_exit.png"))));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.putValue(SMALL_ICON, myIcon);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            GeneralFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            //GeneralFrame.this.tray.remove(myTrayIcon);
            System.exit(0);
        }
    }
    // старт заново
    public class ActionStart extends javax.swing.AbstractAction  {
        public ActionStart()
        {
            super(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionStart"),null);
            javax.swing.ImageIcon myIcon = null;
            try {
                myIcon = getIconFromImage(ImageIO.read(getClass().getResource("image/start.png")));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.putValue(SMALL_ICON, myIcon);
        }
        // процедура для обновления наименования
        public void refreshName(){
            StringBuffer myName = new StringBuffer();
            if (myTimer!=null&&myTimer.getTecTime()!=0) {
                myName.append(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionReStart"));
            }
            else myName.append(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionStart"));
            if (getCurrentTime()!=0) {
                myName.append(" (");
                myName.append(Integer.toString(getCurrentTime()));
                myName.append(")");
            }
            this.putValue(NAME, myName.toString());
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            GeneralFrame.this.myTimer.StartTimer(GeneralFrame.this.getCurrentTime());
            actionStart.setEnabled(false);
            actionPause.setEnabled(true);
            setIcon_Count(getCurrentTime());
            pausa = false;
            refreshName();
            actionPause.refreshName();
        }
    }
    // пауза
    public class ActionPause extends javax.swing.AbstractAction  {
        ImageIcon iconPause=null;
        ImageIcon iconResume = null;
        public ActionPause()
        {
            super(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionPause_pause"),null);
            try {
                iconPause   = getIconFromImage(ImageIO.read(getClass().getResource("image/image_pause.png")));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                iconResume  = getIconFromImage(ImageIO.read(getClass().getResource("image/image_resume.png")));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.putValue(SMALL_ICON, iconPause);
        }
        public void refreshName(){
            StringBuffer myName = new StringBuffer();
            if (pausa) {
                myName.append(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionPause_resume"));
                myName.append(" (");
                myName.append(Integer.toString(myTimer.getTecTime()));
                myName.append(")");
            }
            else myName.append(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionPause_pause"));
            this.putValue(NAME, myName.toString());
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (GeneralFrame.this.pausa)
            {
                //запускаем заново
                GeneralFrame.this.myTimer.StartTimer();
                //this.putValue(NAME,java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionPause_pause"));
                this.putValue(SMALL_ICON, iconPause);
                actionStart.setEnabled(false);
                setIcon_Count(myTimer.getTecTime());
            }
            else 
            {
                // ставим на паузу
                GeneralFrame.this.myTimer.StopTimer();
                //this.putValue(NAME,java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("ActionPause_resume"));
                this.putValue(SMALL_ICON, iconResume);
                //firePropertyChange("NAME","",this.getValue("NAME"));
                actionStart.setEnabled(true);
                setIcon_Pause();
            }
            GeneralFrame.this.pausa = !GeneralFrame.this.pausa;
            refreshName();
        }
    }
    
    //
    
    // методы устанавливают иконки
    private void setIcon_NoRun(){
        myTrayIcon.setImage(myOwnImage);
    }
    
    private void setIcon_Pause(){
        myTrayIcon.setImage(myPauseImage);
    }
    
    private void setIcon_Count(int count){
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);  
        Graphics2D g2d = image.createGraphics();  
        Font font = g2d.getFont();  
        font = font.deriveFont(15.0f); // or any other size  
        g2d.setFont(font);     
        //g2d.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        //Paint myPaint = g2d.getPaint();
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(count),0, 16);  
        g2d.dispose();  
        myTrayIcon.setImage(image);          
    }
            
    
    public class MyTimer {
        private int tecTime;  // текущее время в минутах
        private java.util.Timer myTimer;
        private ReminderTask myTask;
        private boolean timerIsPaused;
        public MyTimer() {
            myTimer = new java.util.Timer();
        }

        class ReminderTask extends java.util.TimerTask {
            @Override
            public void run() {
                if (!timerIsPaused) {
                    if (tecTime==0) 
                        // выполняем действия
                    {
                        StopTimer();
                        try {
                            currentAction.Action_Shutdown();
                        } catch (RuntimeException ex) {
                            Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                        else  {
                            tecTime --;
                            setIcon_Count(tecTime);
                       }
                }
           }            
        }
 
        
        public void StartTimer(){
            if (myTask==null) {
                myTask = new ReminderTask();
                myTimer.schedule(myTask,60000, 60000);
            }
            if (timerIsPaused) timerIsPaused=false;
            setIcon_Count(tecTime);
        }
        
        public void StartTimer(int myTecTime){
            setTecTime(myTecTime);
            StartTimer();
        }
        
        public void setTecTime(int myTecTime) {
            tecTime = myTecTime;
        }
        
        public void StopTimer(){
            timerIsPaused = true;
        }
        public int getTecTime(){
            return tecTime;
        }
    }
    /**
     * Creates new form GeneralFrame
     */
    public GeneralFrame() {
        this.popup = new JPopupMenu(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("AppNAme"));
        this.tray = SystemTray.getSystemTray();
        myOwnImage = new BufferedImage(16,16,java.awt.image.BufferedImage.TYPE_INT_RGB);
        myListener = new SimpleAction();
        actionExit = new ActionExit();
        actionPause = new ActionPause();
        actionStart = new ActionStart();
        simpleAction = new SimpleAction();
        initComponents();
        jSlider1.setMinimum(minCount);
        jSlider1.setMaximum(maxCount);
        SpinnerNumberModel myModel = (SpinnerNumberModel)mTimeShutDown.getModel();
        myModel.setMaximum(maxCount);
        myModel.setMinimum(minCount);
        myModel.setStepSize(step);
        //myModel.setValue(10);        
        currentAction = new MyActionFactory().getInstant();
        setCurrentTime(10);
        mTimeShutDown.setModel(myModel);
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(this,
            java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("messageNotSupportedSystemTray"),
            java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("nameError"),
            JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        else
        {
            try {
                myOwnImage = ImageIO.read(getClass().getResource("image/ownIcon.png"));
                myTrayIcon = new TrayIcon(myOwnImage);
                myTrayIcon.setImageAutoSize(true);
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            myPauseImage = new BufferedImage(16,16,java.awt.image.BufferedImage.TYPE_INT_RGB);
            try {
                myPauseImage = ImageIO.read(getClass().getResource("image/image_pause.png"));
            } catch (IOException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            //tray = SystemTray.getSystemTray();
            try {
                tray.add(myTrayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(GeneralFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            // создаем таймер
            myTimer = new MyTimer();
            // инициализируем меню
            actionPause.setEnabled(false);
            popup.add(actionStart);
            popup.add(actionPause);

            popup.addSeparator();
            //popup.setLightWeightPopupEnabled(false);
            JMenu menu = new JMenu(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("testSimpeAction"));
            StringBuffer textMenu = new StringBuffer();
            for (int i=1; i<=firstCount;i++){
                int time = i*step;
                textMenu.delete(0, textMenu.length());
                textMenu.append(time);
                textMenu.append(" ");
                textMenu.append(java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("min"));
                JMenuItem menuItem = new JMenuItem(textMenu.toString());
                JMenuItem menuItemP = new JMenuItem(textMenu.toString());
                textMenu.delete(0, textMenu.length());
                textMenu.append(time);
                menuItem.setName(textMenu.toString());
                menuItemP.setName(textMenu.toString());
                menuItem.addActionListener(simpleAction);
                menuItemP.addActionListener(simpleAction);
                menu.add(menuItem);
                WindowsPopup.add(menuItemP);// создаем дополнительное всплывающее окно на форме
            }
            //menu.addSeparator();
            popup.add(menu);            
            popup.addSeparator();
            //popup.addSeparator();

            popup.add(actionExit);
            /*popup.setVisible(false);
            popup.show(null,1,1);
            popup.setVisible(false);*/
            myTrayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent  e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                    //GeneralFrame.this.add(popup);
                        int myY;
                        Dimension mScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
                        int mHight = popup.getPreferredSize().height;
                        if ((e.getYOnScreen()+mHight-5)>mScreenDimension.height)  myY = e.getYOnScreen()-mHight-5;
                        else myY = e.getYOnScreen();
                        //Point location = getPopupLocation(e);
                        popup.setLocation(e.getXOnScreen(), myY);
                        //popup.setLocation(e.getX(), e.getY());
                        popup.setInvoker(popup);
                        //popup.show((Component)GeneralFrame.this, e.getXOnScreen(),myY);
                        popup.setVisible(true);
                        //popup.show(null,100 , 100);
                        //popup.show(null, e.getXOnScreen(), loca);
                    }
                    else if (e.getButton()==MouseEvent.BUTTON1&&e.getClickCount()==2) GeneralFrame.this.setVisible(!GeneralFrame.this.isVisible());
                        //else if (e.getButton()==MouseEvent.BUTTON1&&e.getClickCount()==1) actionPause.actionPerformed(null);
                }

            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        WindowsPopup = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        mTimeShutDown = new javax.swing.JSpinner();
        jSlider1 = new javax.swing.JSlider();
        jAction = new javax.swing.JButton();
        jAction1 = new javax.swing.JButton();
        ButtonPopup = new javax.swing.JButton();

        WindowsPopup.setLabel("WindowsPopup");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("autoshutdown/Bundle"); // NOI18N
        setTitle(bundle.getString("AppNAme")); // NOI18N

        jLabel1.setText(bundle.getString("JLabelTime")); // NOI18N

        mTimeShutDown.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1, 1));
        mTimeShutDown.setToolTipText(bundle.getString("JLabelTime")); // NOI18N
        mTimeShutDown.setName("mTimeShutDown"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jAction, org.jdesktop.beansbinding.ELProperty.create("${action.enabled}"), mTimeShutDown, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        mTimeShutDown.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mTimeShutDownStateChanged(evt);
            }
        });

        jSlider1.setMaximum(600);
        jSlider1.setMinimum(1);
        jSlider1.setOrientation(javax.swing.JSlider.VERTICAL);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, mTimeShutDown, org.jdesktop.beansbinding.ELProperty.create("${value}"), jSlider1, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jAction, org.jdesktop.beansbinding.ELProperty.create("${action.enabled}"), jSlider1, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jAction.setAction(actionStart);
        jAction.setName("jAction"); // NOI18N

        jAction1.setAction(actionPause);
        jAction1.setName("jAction"); // NOI18N

        ButtonPopup.setText("jButton1");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jAction, org.jdesktop.beansbinding.ELProperty.create("${action.enabled}"), ButtonPopup, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        ButtonPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPopupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mTimeShutDown)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonPopup, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jAction1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jAction, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mTimeShutDown)
                    .addComponent(ButtonPopup, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jAction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAction1)
                .addGap(18, 18, 18))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mTimeShutDownStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mTimeShutDownStateChanged
        // TODO add your handling code here:
        //System.out.println("Событие 6");
        
        //int f=3;
        Integer myModel = (Integer)mTimeShutDown.getModel().getValue();
        setCurrentTime(myModel.intValue());
        actionStart.refreshName();
    }//GEN-LAST:event_mTimeShutDownStateChanged

    private void ButtonPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonPopupActionPerformed
        // TODO add your handling code here:
        WindowsPopup.show(ButtonPopup,0 ,ButtonPopup.getHeight());
    }//GEN-LAST:event_ButtonPopupActionPerformed

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
                if (java.util.ResourceBundle.getBundle("autoshutdown/Bundle").getString("NIMBUS").equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GeneralFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneralFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneralFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneralFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GeneralFrame().setVisible(false);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonPopup;
    private javax.swing.JPopupMenu WindowsPopup;
    private javax.swing.JButton jAction;
    private javax.swing.JButton jAction1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSpinner mTimeShutDown;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
