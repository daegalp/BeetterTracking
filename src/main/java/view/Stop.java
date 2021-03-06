package view;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONObject;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Astin
 */
public class Stop extends javax.swing.JFrame {
    private String token = "";
    int hour,minute,second;
    int timeInSecond;
    int xx,yy,x,y;
    boolean exit = false;

    boolean status = false;
    
    Calendar cal = Calendar.getInstance();
    
    public Stop() {
        initComponents();
        this.setLocationRelativeTo(null);
        countWorkingTime();
    }
    
    public void checkToken(){
        if(status == false){
           getProcess();
        }
    }
    public void setToken(String token){
        this.token = token;
        checkToken();
    }
      
    public int convertsecond(){
        timeInSecond=second+60*minute+3600*hour;
        return timeInSecond;
    }
          
    public void countWorkingTime(){
        Thread t = new Thread(){
           public void run(){
               for(;;){
                    String time = hour + ":" + minute + ":" + second +"";
                    second++;

                    if(second == 60){
                        minute++;
                        second = 0;
                    }
                    
                    if(minute == 60){
                        hour++;
                        minute = 0;
                    }
                    
                    if(hour == 24){
                        hour = 0;
                    }
                    
                    durasiLabel.setText(time);
                    try{
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ex){
                        Logger.getLogger(Start.class.getName())
                                         .log(Level.SEVERE, null,ex);
                    }
               }
           }
       };
       t.start();
    }
    
    public void getProcess(){
        Thread t = new Thread(){
            public void run(){
                while(!exit){
                    Calendar timeBeforeProcess = Calendar.getInstance();
                    List<ProcessInfo> processesList = JProcesses
                                                      .getProcessList();
                    List<String> app = new ArrayList<String>();
                 
                    for(int i=0;i<processesList.size();i++){
                        app.add(processesList.get(i).getName());
                    }
                    
                    Set<String> setProcess = new HashSet<>(app); 
                    List<String> processListNoDuplicate  = new ArrayList<>
                                                            (setProcess);
                    
                    String[] name = new String[processListNoDuplicate.size()];
                    
                    for(int i=0;i<processListNoDuplicate.size();i++){
                          name[i]= processListNoDuplicate.get(i);
                      }
                    
                    Calendar timeAfterProcess = Calendar.getInstance();
                    long duration = 120000 -( timeAfterProcess.getTimeInMillis() 
                                    - timeBeforeProcess.getTimeInMillis());
                    
                        sendData(name,name.length);
                        
                        try{
                            Thread.sleep(duration);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Stop.class.getName())
                                            .log(Level.SEVERE, null, ex);
                        }
                
                }
            }
        };
        t.start();
        
    }
    
    public void sendData(String[] name,int length){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //default timeout for not annotated requests
            .readTimeout(300000, TimeUnit.MILLISECONDS)
            .connectTimeout(300000, TimeUnit.MILLISECONDS)
            .writeTimeout(300000, TimeUnit.MILLISECONDS)
            .build();
        
        FormBody.Builder formBuilder = new FormBody.Builder();
        
        for(int i=0; i< length;i++){
            formBuilder.add("data[" + i + "]" , name[i]);
        
        }
        RequestBody formBody = formBuilder.build();
        
        Request request = new Request.Builder()
            .url("https://better123.herokuapp.com/api/application-tracking-history/data")
            .addHeader("Content-Type","application/x-www-form-urlencoded")
            .addHeader("Accept","application/json")
            .addHeader("Authorization", token)
            .post(formBody)
            .build();
        
        
        
        try (Response response = okHttpClient.newCall(request).execute()) {
               String t = response.body().string();
               System.out.println(t);
            if(response.message().equalsIgnoreCase("OK")){
                System.out.println("200");
            }
            else if(response.message().equalsIgnoreCase("Unauthorized")){
                System.out.println("401");
            }
            
            System.out.println(response.message());
            
        } catch (IOException ex) {
            Logger.getLogger(SignIn.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel2 = new javax.swing.JPanel();
        stopLabel = new javax.swing.JLabel();
        closeLabel = new javax.swing.JLabel();
        minimizeLabel = new javax.swing.JLabel();
        exitLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        durasiLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1109, 590));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });
        jPanel2.setLayout(null);

        stopLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stop.png"))); // NOI18N
        stopLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stopLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                stopLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stopLabelMouseExited(evt);
            }
        });
        jPanel2.add(stopLabel);
        stopLabel.setBounds(580, 260, 400, 51);

        closeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        closeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeLabelMouseExited(evt);
            }
        });
        jPanel2.add(closeLabel);
        closeLabel.setBounds(580, 330, 400, 51);

        minimizeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize.png"))); // NOI18N
        minimizeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseClicked(evt);
            }
        });
        jPanel2.add(minimizeLabel);
        minimizeLabel.setBounds(940, 10, 40, 30);

        exitLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        exitLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitLabelMouseClicked(evt);
            }
        });
        jPanel2.add(exitLabel);
        exitLabel.setBounds(990, 20, 20, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setText("WORKING TIME");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(150, 250, 280, 40);

        durasiLabel.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        durasiLabel.setText("00:00:00");
        jPanel2.add(durasiLabel);
        durasiLabel.setBounds(190, 320, 200, 60);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/custom – 2.png"))); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 1030, 590);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1030, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopLabelMouseClicked
        Start start = new Start();
        start.setToken(token);
        start.setVisible(true);
        status = true;
        exit = true;
        this.dispose();
    }//GEN-LAST:event_stopLabelMouseClicked

    private void stopLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopLabelMouseEntered
        ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource("images/stopClick.png"));
        stopLabel.setIcon(i);
    }//GEN-LAST:event_stopLabelMouseEntered

    private void stopLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopLabelMouseExited
        ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource("images/stop.png"));
        stopLabel.setIcon(i);
    }//GEN-LAST:event_stopLabelMouseExited

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void closeLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseEntered
        ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource("images/closeClick.png"));
        closeLabel.setIcon(i);
    }//GEN-LAST:event_closeLabelMouseEntered

    private void closeLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseExited
        ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource("images/close.png"));
        closeLabel.setIcon(i);
    }//GEN-LAST:event_closeLabelMouseExited

    private void minimizeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseClicked
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_minimizeLabelMouseClicked

    private void exitLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exitLabelMouseClicked

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        xx = evt.getX();
        yy = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        this.setLocation(x-xx, y-yy);
    }//GEN-LAST:event_jPanel2MouseDragged

    
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Stop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Stop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Stop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Stop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Stop().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel durasiLabel;
    private javax.swing.JLabel exitLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel minimizeLabel;
    private javax.swing.JLabel stopLabel;
    // End of variables declaration//GEN-END:variables
}
