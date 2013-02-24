package com.powersurgepub.linktweaker;

  import com.powersurgepub.psutils.*;
  import com.powersurgepub.xos2.*;
  import java.io.*;
  import java.net.*;
  import javax.swing.*;
  import javax.swing.event.*;

/**
 Tweaks URLs for internal Boeing use. 

 @author Herb Bowie
 */
public class LinkTweaker 
    extends javax.swing.JFrame
        implements
            HyperlinkListener {
  
  public static final String PROGRAM_NAME = "LinkTweaker";
  public static final String PROGRAM_VERSION = "1.00";
  
  public static final String SP_SITES = "/sites";
  
  private Home home = Home.getShared(PROGRAM_NAME, PROGRAM_VERSION);

  /**
   Creates new form LinkTweaker
   */
  public LinkTweaker() {
    initComponents();
    setBounds (100, 100, 620, 540);
    try {
      UIManager.setLookAndFeel
        (UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      // Let's hope this doesn't happen!!
    }
    SwingUtilities.updateComponentTreeUI (this);
    
    aboutJavaText.setText
        (System.getProperty("java.vm.name") +
        " version " + System.getProperty("java.vm.version") +
        " from " + StringUtils.removeQuotes(System.getProperty("java.vm.vendor")));
    programNameAndVersionText.setText
        (PROGRAM_NAME
        + " version " + PROGRAM_VERSION);

    URL aboutURL = LinkTweaker.class.getResource("about.html");
    try {
      aboutTextPane.setPage(aboutURL);
    } catch (IOException e) {
      StringBuilder about = new StringBuilder();
      about.append("<html>");
      about.append("<p><font face=\"Arial\" size=\"4\">Written for Boeing by Herb Bowie. </font></p>");

      about.append("<p><font face=\"Arial\" size=\"4\">This <span xmlns:dct=\"http://purl.org/dc/terms/\" href=\"http://purl.org/dc/dcmitype/InteractiveResource\" rel=\"dct:type\">work</span> is licensed under a <a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-nd/3.0/\">Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License</a>.  </font></p>");

      about.append("<p><font face=\"Arial\" size=\"4\">LinkTweaker is written in Java. It may be run on Windows, Macintosh and other Unix platforms. LinkTweaker requires a Java Virtual Machine (JVM/JRE) of 1.5 (aka J2SE 5.0) or later.  </font></p>");

      about.append("<p><font face=\"Arial\" size=\"4\"><a rel=\"license\" href=\"http://creativecommons.org/licenses/by-nc-nd/3.0/\"><img alt=\"Creative Commons License\" style=\"border-width:0\" src=\"http://i.creativecommons.org/l/by-nc-nd/3.0/88x31.png\" /></a> </font></p>");

      about.append("</html>");
      aboutTextPane.setText(about.toString());
    }

    aboutTextPane.addHyperlinkListener (this);

  }
  
  /**
   Let's straighten out the URL submitted by the user. 
  */
  private void tweakLink() {
    
    StringBuilder link = new StringBuilder(inputTextArea.getText());
    
    // If it's a file path, insert the proper URL protocol (aka scheme)
    if (link.length() > 0 && 
        (link.charAt(0) == '\\' || link.charAt(0) == '/')) {
      link.insert(0, "file:");  
    }
    
    // Remove a trailing period, in case one got attached somehow along the way
    if (link.length() > 0 &&
        link.charAt(link.length() - 1) == '.') {
      link.deleteCharAt(link.length() - 1);
    }
    
    // Let's make one pass through the link to normalize special characters
    int i = 0;  // Point to current position being examined
    int j = 0;  // After change, point to new position to be examined
    
    while (i < link.length()) {
      j = i;
      
      // If user wants to see spaces, then let's show them spaces
      if (j == i
          && spacesCheckBox.isSelected()) {
        j = ifMatchReplaceWith (link, i, "%20", " ");
      }
      if (j == i
          && spacesCheckBox.isSelected()) {
        j = ifMatchReplaceWith (link, i, "%2520", " ");
      }
      
      // Let's return other common characters proxies to their benign real selves
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%26", "&");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%28", "(");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%29", ")");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2d", "-");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2D", "-");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2e", ".");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2E", ".");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2f", "/");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2F", "/");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%252F", "/");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%3a", ":");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%3A", ":");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%3d", "=");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%3D", "=");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%3f", "?");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%3F", "?");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%2520", "%20");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%5f", "_");
      }
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "%5F", "_");
      }
      
      // If we've got backslashes, let's turn them around
      if (j == i) {
        j = ifMatchReplaceWith (link, i, "\\", "/");
      }
      
      // If user doesn't want to see spaces, then let's encode them
      if (j == i
          && (! spacesCheckBox.isSelected())) {
        j = ifMatchReplaceWith (link, i, " ", "%20");
      }
      
      // Remove carriage returns, tabs, line feeds, etc.
      if (j == i) {
        if (link.charAt(i) == '\r' 
            || link.charAt(i) == '\n'
            || link.charAt(i) == '\t'
            || link.charAt(i) == '\"') {
          link.deleteCharAt(i);
        } else {
          i++;
        }
      } else {
        i = j;
      }
    }
    
    // Remove email quote identifiers
    if (j == i) {
      j = ifMatchReplaceWith (link, i, "> ", "");
    }
    
    // Let's make another pass through the link to clean up SharePoint cruft
    if (spCruftCheckBox.isSelected()) {
      i = 0;
      int sites1Index = -1;

      while (i < link.length()) {
        j = i;

        // Delete '&Folder' and anything that follows
        if (j == i) {
          if (link.charAt(i) == '&'
              && (i + 7) < link.length()
              && link.substring(i, i + 7).equals("&Folder")) {
            link.delete(i, link.length());
            i--;
          }
        }
        
        // Delete '&Source=' and anything that follows
        if (j == i) {
          if (link.charAt(i) == '&'
              && (i + 8) < link.length()
              && link.substring(i, i + 8).equals("&Source=")) {
            link.delete(i, link.length());
            i--;
          }
        }

        if (j == i) {
          j = ifMatchReplaceWith (link, i, 
              "/Forms/AllItems.aspx?RootFolder=/", 
              "/");
          if (j > i) {
            int path2start = i;
            int path2end = link.indexOf("/", j);
            if (path2end < 0) {
              path2end = link.length();
            }
            String path2 = link.substring(path2start, path2end);
            System.out.println ("path2 = " + path2);
            int path1start = link.indexOf(path2);
            if (path1start >= 0 && path1start < path2start) {
              int path1end = path1start + path2.length();
              while (path2end < link.length()
                  && path1end < path2start
                  && link.charAt(path1end) == link.charAt(path2end)) {
                path1end++;
                path2end++;
              } // end while matching path characters
              link.delete(path2start, path2end);
            } // end if we found duplicate paths
          } // end if we found the rootfolder string
        } 
        if (j == i) {
          j = ifMatchReplaceWith (link, i, 
              "/Forms/AllItems.aspx", 
              "/");
        }
        if (j == i) {
          j = ifMatchReplaceWith (link, i, "/SitePages/Home.aspx", "/");
        }
        
        if (j == i) {
          j = ifMatchReplaceWith (link, i, "/Pages/Default.aspx", "/");
        }
        
        // If we find '/sites' repeated, let's delete the duplication
        if ((i + SP_SITES.length()) < link.length()
            && link.substring(i, i + SP_SITES.length()).equals(SP_SITES)) {
          if (sites1Index < 0) {
            sites1Index = i;
          } else {
            link.delete(sites1Index, i);
            j = sites1Index + SP_SITES.length();
          }
        }
        
        if (j == i) {
          i++;
        } else {
          i = j;
        }
      }
    }
    
    // Insert a redirect, if user has so specified
    if (redirectCheckBox.isSelected()) {
      link.insert(0, "http://wsso-support.web.boeing.com:2015/redirect.html?URL=");
    }
    
    outputTextArea.setText(link.toString());
    msgLabel.setText(" ");
  }
  
  /**
   If the given before string matches the next sequence of characters, then 
   replace it with the after string. 
  
   @param str    The StringBuffer to possibly be changed.
   @param i      The current position in the StringBuffer to be examined. 
   @param before The matching string we are looking for. 
   @param after  The replacement string, if a match is found.
  
   @return The resulting next index position to be examined. If no replacement
           was made, this will be equal to the i param. If a replacement was 
           made, it will be equal to i + the length of the to string. 
  */
  private int ifMatchReplaceWith 
      (StringBuilder str, int i, String before, String after) {
    if ((i + before.length()) <= str.length()
        && str.substring(i, i + before.length()).equals(before)) {
      str.delete(i, i + before.length());
      str.insert(i, after);
      return (i + after.length());
    } else {
      return i;
    }
  }
  
  public void hyperlinkUpdate (HyperlinkEvent e) {
    HyperlinkEvent.EventType type = e.getEventType();
    if (type == HyperlinkEvent.EventType.ACTIVATED) {
      home.openURL(e.getURL()); 
    }
  }

  /**
   This method is called from within the constructor to initialize the form.
   WARNING: Do NOT modify this code. The content of this method is always
   regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    tabs = new javax.swing.JTabbedPane();
    linksTab = new javax.swing.JPanel();
    inputLabel = new javax.swing.JLabel();
    inputScrollPane = new javax.swing.JScrollPane();
    inputTextArea = new javax.swing.JTextArea();
    outputLabel = new javax.swing.JLabel();
    outputScrollPane = new javax.swing.JScrollPane();
    outputTextArea = new javax.swing.JTextArea();
    spCruftCheckBox = new javax.swing.JCheckBox();
    redirectCheckBox = new javax.swing.JCheckBox();
    spacesCheckBox = new javax.swing.JCheckBox();
    tweakButton = new javax.swing.JButton();
    launchButton = new javax.swing.JButton();
    copyButton = new javax.swing.JButton();
    msgLabel = new javax.swing.JLabel();
    aboutTab = new javax.swing.JPanel();
    programNameAndVersionText = new javax.swing.JLabel();
    aboutScrollPane = new javax.swing.JScrollPane();
    aboutTextPane = new javax.swing.JEditorPane();
    aboutJavaLabel = new javax.swing.JLabel();
    aboutJavaText = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("LinkTweaker");

    linksTab.setLayout(new java.awt.GridBagLayout());

    inputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inputLabel.setText("Input Link:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(inputLabel, gridBagConstraints);

    inputTextArea.setColumns(20);
    inputTextArea.setLineWrap(true);
    inputTextArea.setRows(5);
    inputTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent evt) {
        inputTextAreaFocusGained(evt);
      }
      public void focusLost(java.awt.event.FocusEvent evt) {
        inputTextAreaFocusLost(evt);
      }
    });
    inputScrollPane.setViewportView(inputTextArea);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 221;
    gridBagConstraints.ipady = 61;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(inputScrollPane, gridBagConstraints);

    outputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    outputLabel.setText("Output Link:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(outputLabel, gridBagConstraints);

    outputTextArea.setColumns(20);
    outputTextArea.setLineWrap(true);
    outputTextArea.setRows(5);
    outputTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent evt) {
        outputTextAreaFocusGained(evt);
      }
    });
    outputScrollPane.setViewportView(outputTextArea);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 221;
    gridBagConstraints.ipady = 61;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(outputScrollPane, gridBagConstraints);

    spCruftCheckBox.setText("Remove SharePoint Cruft?");
    spCruftCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        spCruftCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    linksTab.add(spCruftCheckBox, gridBagConstraints);

    redirectCheckBox.setText("Insert WSSO Redirect?");
    redirectCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        redirectCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(redirectCheckBox, gridBagConstraints);

    spacesCheckBox.setText("Show spaces as spaces?");
    spacesCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        spacesCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    linksTab.add(spacesCheckBox, gridBagConstraints);

    tweakButton.setText("Tweak");
    tweakButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        tweakButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(tweakButton, gridBagConstraints);

    launchButton.setText("Launch");
    launchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        launchButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 8;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(launchButton, gridBagConstraints);

    copyButton.setText("Copy");
    copyButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        copyButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 9;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(copyButton, gridBagConstraints);

    msgLabel.setText(" ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 10;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    linksTab.add(msgLabel, gridBagConstraints);

    tabs.addTab("Links", linksTab);

    aboutTab.setLayout(new java.awt.GridBagLayout());

    programNameAndVersionText.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
    programNameAndVersionText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    programNameAndVersionText.setText("xxx version n.nn");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    aboutTab.add(programNameAndVersionText, gridBagConstraints);

    aboutTextPane.setEditable(false);
    aboutTextPane.setContentType("text/html"); // NOI18N
    aboutScrollPane.setViewportView(aboutTextPane);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    aboutTab.add(aboutScrollPane, gridBagConstraints);

    aboutJavaLabel.setText("About Java:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.ipadx = 2;
    gridBagConstraints.ipady = 2;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    aboutTab.add(aboutJavaLabel, gridBagConstraints);

    aboutJavaText.setEditable(false);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.ipadx = 2;
    gridBagConstraints.ipady = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    aboutTab.add(aboutJavaText, gridBagConstraints);

    tabs.addTab("About", aboutTab);

    getContentPane().add(tabs, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void inputTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputTextAreaFocusLost
    tweakLink();
  }//GEN-LAST:event_inputTextAreaFocusLost

  private void redirectCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redirectCheckBoxActionPerformed
    tweakLink();
  }//GEN-LAST:event_redirectCheckBoxActionPerformed

  private void tweakButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tweakButtonActionPerformed
    tweakLink();
  }//GEN-LAST:event_tweakButtonActionPerformed

  private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
    tweakLink();
    boolean ok = home.openURL(outputTextArea.getText());
    if (! ok) {
      msgLabel.setText("Error in launching link in Web browser");
    }
  }//GEN-LAST:event_launchButtonActionPerformed

  private void spCruftCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spCruftCheckBoxActionPerformed
    tweakLink();
  }//GEN-LAST:event_spCruftCheckBoxActionPerformed

  private void inputTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputTextAreaFocusGained
    inputTextArea.selectAll();
  }//GEN-LAST:event_inputTextAreaFocusGained

  private void outputTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_outputTextAreaFocusGained
    tweakLink();
    outputTextArea.selectAll();
  }//GEN-LAST:event_outputTextAreaFocusGained

  private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
    tweakLink();
    outputTextArea.selectAll();
    outputTextArea.copy();
  }//GEN-LAST:event_copyButtonActionPerformed

  private void spacesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spacesCheckBoxActionPerformed
    outputTextArea.setWrapStyleWord(spacesCheckBox.isSelected());
    tweakLink();
  }//GEN-LAST:event_spacesCheckBoxActionPerformed

  /**
   @param args the command line arguments
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
      java.util.logging.Logger.getLogger(LinkTweaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(LinkTweaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(LinkTweaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(LinkTweaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new LinkTweaker().setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel aboutJavaLabel;
  private javax.swing.JTextField aboutJavaText;
  private javax.swing.JScrollPane aboutScrollPane;
  private javax.swing.JPanel aboutTab;
  private javax.swing.JEditorPane aboutTextPane;
  private javax.swing.JButton copyButton;
  private javax.swing.JLabel inputLabel;
  private javax.swing.JScrollPane inputScrollPane;
  private javax.swing.JTextArea inputTextArea;
  private javax.swing.JButton launchButton;
  private javax.swing.JPanel linksTab;
  private javax.swing.JLabel msgLabel;
  private javax.swing.JLabel outputLabel;
  private javax.swing.JScrollPane outputScrollPane;
  private javax.swing.JTextArea outputTextArea;
  private javax.swing.JLabel programNameAndVersionText;
  private javax.swing.JCheckBox redirectCheckBox;
  private javax.swing.JCheckBox spCruftCheckBox;
  private javax.swing.JCheckBox spacesCheckBox;
  private javax.swing.JTabbedPane tabs;
  private javax.swing.JButton tweakButton;
  // End of variables declaration//GEN-END:variables
}
