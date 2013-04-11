/*
 * Copyright 2012 - 2013 Herb Bowie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.powersurgepub.linktweaker;

  import com.powersurgepub.psdatalib.ui.*;
  import com.powersurgepub.psutils.*;
  import com.powersurgepub.xos2.*;
  import javax.swing.*;

/**
 *
 * @author  hbowie
 */
public class PrefsWindow 
    extends javax.swing.JFrame
      implements WindowToManage {

  private XOS               xos = XOS.getShared();
  
  private ProgramVersion    programVersion = ProgramVersion.getShared();
  
  private boolean           setupComplete = false;
  
  private TweakerPrefs      tweakerPrefs ;
  
  /** Creates new form PrefsWindow */
  public PrefsWindow(TweakerPrefs tweakerPrefs) {

    super();
    this.tweakerPrefs = tweakerPrefs;
    
    initComponents();
    
    this.setTitle (Home.getShared().getProgramName() + " Preferences");
    this.setBounds (100, 100, 600, 400);
    
    prefsTabs.addTab(TweakerPrefs.PREFS_TAB_NAME, tweakerPrefs);
    
    try {
      javax.swing.UIManager.setLookAndFeel
        (javax.swing.UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      // Let's hope this doesn't happen!!
    }
    SwingUtilities.updateComponentTreeUI (this);
    
    setupComplete = true;
  }

  public void savePrefs() {
    tweakerPrefs.savePrefs();
  }
  
  public String getRedirectURL () {
    return tweakerPrefs.getRedirectURL();
  }

  public TweakerPrefs getTweakerPrefs() {
    return tweakerPrefs;
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    prefsTabs = new javax.swing.JTabbedPane();

    setMinimumSize(new java.awt.Dimension(400, 300));
    addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentHidden(java.awt.event.ComponentEvent evt) {
        formComponentHidden(evt);
      }
    });
    getContentPane().add(prefsTabs, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
  WindowMenuManager.getShared().hide(this);
}//GEN-LAST:event_formComponentHidden
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTabbedPane prefsTabs;
  // End of variables declaration//GEN-END:variables
  
}
