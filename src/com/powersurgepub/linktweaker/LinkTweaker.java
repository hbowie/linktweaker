/*
 * Copyright 2012 - 2014 Herb Bowie
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
  import java.awt.*;
  import java.awt.event.*;
  import java.io.*;
  import java.net.*;
  import javax.swing.*;
  import javax.swing.event.*;

/**
 Tweaks URLs for simplicity and clarity. 

 @author Herb Bowie
 */
public class LinkTweaker 
    extends javax.swing.JFrame
        implements
            HyperlinkListener,
            LinkTweakerInterface,
            WindowToManage,
            XHandler {
  
  public static final String PROGRAM_NAME = "LinkTweaker";
  public static final String PROGRAM_VERSION = "1.30";
  
  public static final boolean PREFS_AVAILABLE = true;
  
  public static final String DEFAULT_LINK_ID = "Link";
  
  private boolean runningAsMainApp = true;
  
  private     static  final int   DEFAULT_WIDTH = 620;
  private     static  final int   DEFAULT_HEIGHT = 540;
  
  private                   int   defaultX = 0;
  private                   int   defaultY = 0;
  
  private             Appster appster;

  private             String  country = "  ";
  private             String  language = "  ";

  private             Home                home;
  private             ProgramVersion      programVersion;
  private             XOS                 xos;
  private             Trouble             trouble = Trouble.getShared();
  
  // About window
  private             AboutWindow         aboutWindow;
  
  private             LogWindow           logWindow;
  private             Logger              logger     = Logger.getShared();
  private             LogOutput           logOutput;
  
  private             PrefsWindow         prefsWindow;
  
  private             TweakerPrefs        tweakerPrefs;
  
  private             LinkTweakerApp      linkTweakerApp = null;
  
  // Menu Objects
  private			JMenuBar						menuBar;
  
  private			JMenu								fileMenu;
  private			JMenuItem						fileOpen;
  private			JMenuItem						fileExit;
  
  private     JMenu               toolsMenu;
  private     JMenuItem           toolsOptions;
  
  private     JMenu               windowMenu;
  
  private			JMenu								helpMenu;

  private     JSeparator          helpSeparator2;
  private			JMenuItem						helpAbout;
  
  private			JMenuItem						helpPSPubWebSite;
  private     JSeparator          helpSeparator3;
  private     JMenuItem           helpReduceWindowSize;
  
  private     String              linkID = DEFAULT_LINK_ID;

  /**
   Creates new form LinkTweaker
   */
  public LinkTweaker() {
    constructApp();
  }
  
  public LinkTweaker (
      LinkTweakerApp linkTweakerApp,  
      JTabbedPane prefsTabs) {
    this.runningAsMainApp = false;
    this.linkTweakerApp = linkTweakerApp;
    home = Home.getShared ();
    xos = XOS.getShared();
    tweakerPrefs = new TweakerPrefs();
    if (runningAsMainApp) {
      constructApp();
    } else {
      constructWindow();
    }
  }
  
  private void constructApp() {
    
    appster = new Appster
        ("powersurgepub", "com",
          PROGRAM_NAME, PROGRAM_VERSION,
          language, country,
          this, this);
    
    home = Home.getShared ();
    xos = XOS.getShared();
    
    logWindow = new LogWindow ();
    logOutput = new LogOutputText(logWindow.getTextArea());
    Logger.getShared().setLog (logOutput);
    Logger.getShared().setLogAllData (false);
    Logger.getShared().setLogThreshold (LogEvent.NORMAL);
    WindowMenuManager.getShared().add(logWindow);
    
    aboutWindow = new AboutWindow(false, false, false, false, false, 
        "2012");
    
    tweakerPrefs = new TweakerPrefs();
    
    prefsWindow = new PrefsWindow (tweakerPrefs);
    
    Trouble.getShared().setParent(this);
    if (PREFS_AVAILABLE) {
      xos.enablePreferences();
    }

    constructWindow();
    
    getButton.setVisible(false);
    putButton.setVisible(false);
    
    xos.setXHandler (this);
    xos.setMainWindow (this);
    
    // Create Menus for the app
    menuBar = new JMenuBar();
    this.setJMenuBar (menuBar);
    
    // File menu
    fileMenu = new JMenu("File");
    if (! xos.isRunningOnMacOS()) {
      menuBar.add (fileMenu);
      /*
      fileExit = new JMenuItem ("Exit/Quit");
      fileExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_Q,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
      fileMenu.add (fileExit);
      fileExit.addActionListener (new ActionListener ()
        {
          public void actionPerformed (ActionEvent event) {
            handleQuit();
          } // end actionPerformed method
        } // end action listener
      );
      */
    }
    xos.setFileMenu (fileMenu);
    
    // Tools menu
    if (! xos.isRunningOnMacOS()) {
      toolsMenu = new JMenu("Tools");
      menuBar.add (toolsMenu);
      toolsOptions = new JMenuItem ("Options");
      toolsMenu.add (toolsOptions);
      toolsOptions.addActionListener (new ActionListener ()
        {
          public void actionPerformed (ActionEvent event) {
            handlePreferences();
          } // end actionPerformed method
        } // end action listener
      );
    }
    
    // Window menu
    windowMenu = new JMenu("Window");
    menuBar.add (windowMenu);
    
    // Help Menu 
    helpMenu = new JMenu("Help");
    menuBar.add (helpMenu);
    home.setHelpMenu(this, helpMenu);
    xos.setHelpMenu (helpMenu);
    xos.setHelpMenuItem (home.getHelpMenuItem());
    
    WindowMenuManager.getShared().addWindowMenu(windowMenu);
    /* 
    try {
      userGuideURL = new URL (pageURL, USER_GUIDE);
    } catch (MalformedURLException e) {
    } 
    */
    /* 
    try {
      programHistoryURL = new URL(pageURL, PROGRAM_HISTORY);
    } catch (MalformedURLException e) {
      // shouldn't happen
    } 
    */
    // xos.setHelpMenuItem (helpUserGuideMenuItem);

  }
  
  private void constructWindow() {
    
    programVersion = ProgramVersion.getShared ();
    initComponents();
    setBounds (100, 100, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    try {
      javax.swing.UIManager.setLookAndFeel
        (javax.swing.UIManager.getSystemLookAndFeelClassName());
    } catch (Exception e) {
      // Let's hope this doesn't happen!!
    }
    SwingUtilities.updateComponentTreeUI (this);
  }
  
  private void setDefaultScreenSizeAndLocation() {

		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    this.setResizable (true);
		calcDefaultScreenLocation();
		this.setLocation (defaultX, defaultY);
  }
  
  private void calcDefaultScreenLocation() {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    defaultX = (d.width - this.getSize().width) / 2;
    defaultY = (d.height - this.getSize().height) / 2;
  }
  
  /**
   Set the input link to the passed value, when using this class as an
   auxiliary window to a main program. 
  
   @param passedLink The link to be tweaked. 
  */
  public void setLink(String passedLink) {
    inputTextArea.setText(passedLink);
    outputTextArea.setText(passedLink);
    linkID = "Link";
  }
  
  public void setLink(String passedLink, String LinkID) {
    inputTextArea.setText(passedLink);
    outputTextArea.setText(passedLink);
    this.linkID = linkID;
  }
  
  /**
   Let's straighten out the URL submitted by the user. 
  */
  private void tweakThisLink() {
    
    String tweakedLink = StringUtils.tweakAnyLink (
        inputTextArea.getText(),
        spacesCheckBox.isSelected(),
        spCruftCheckBox.isSelected(),
        redirectCheckBox.isSelected(),
        tweakerPrefs.getRedirectURL());
    
    outputTextArea.setText(tweakedLink);
    msgLabel.setText(" ");
  }
  
  public void hyperlinkUpdate (HyperlinkEvent e) {
    HyperlinkEvent.EventType type = e.getEventType();
    if (type == HyperlinkEvent.EventType.ACTIVATED) {
      home.openURL(e.getURL()); 
    }
  }
  
  /**
     Standard way to respond to an About Menu Item Selection on a Mac.
   */
  public void handleAbout() {
    displayAuxiliaryWindow(aboutWindow);
  }
  
  public void displayAuxiliaryWindow(WindowToManage window) {
    window.setLocation(
        this.getX() + 60,
        this.getY() + 60);
    WindowMenuManager.getShared().makeVisible(window);
  }
  
  /**      
    Standard way to respond to a document being passed to this application on a Mac.
   
    @param inFile File to be processed by this application, generally
                  as a result of a file or directory being dragged
                  onto the application icon.
   */
  public void handleOpenFile (File inFile) {
    // Not supported
  }
  
  /**
   Open the passed URI. 
   
   @param inURI The URI to open. 
  */
  public void handleOpenURI(URI inURI) {
    // Not supported
  }
  
  public boolean preferencesAvailable() {
    return PREFS_AVAILABLE;
  }
  
  /**
     Standard way to respond to a Preferences Item Selection on a Mac.
   */
  public void handlePreferences() {
    displayPrefs ();
  }

  public void displayPrefs () {
    displayAuxiliaryWindow(prefsWindow);
  }
  
  /**
   Standard way to respond to a print request.
   */
  public void handlePrintFile (File printFile) {
    // not supported
  }
  
  private void windowClose() {

    if (runningAsMainApp) {
      // System.out.println("  Running as Main App = true");
      handleQuit();
    } else {
      // System.out.println("  Running as Main App = false");
      WindowMenuManager.getShared().hideAndRemove(this);
    }
  }
  
  /**
     We're out of here!
   */
  public void handleQuit() {
    // System.out.println("LinkTweaker.handleQuit");
    savePrefs();
    System.exit(0);
  }

  private void savePrefs() {
    tweakerPrefs.savePrefs();
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

    inputLabel = new javax.swing.JLabel();
    inputScrollPane = new javax.swing.JScrollPane();
    inputTextArea = new javax.swing.JTextArea();
    outputLabel = new javax.swing.JLabel();
    outputScrollPane = new javax.swing.JScrollPane();
    outputTextArea = new javax.swing.JTextArea();
    msgLabel = new javax.swing.JLabel();
    buttonPanel = new javax.swing.JPanel();
    getButton = new javax.swing.JButton();
    tweakButton = new javax.swing.JButton();
    launchButton = new javax.swing.JButton();
    copyButton = new javax.swing.JButton();
    putButton = new javax.swing.JButton();
    optionPanel = new javax.swing.JPanel();
    spCruftCheckBox = new javax.swing.JCheckBox();
    redirectCheckBox = new javax.swing.JCheckBox();
    spacesCheckBox = new javax.swing.JCheckBox();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    setTitle("LinkTweaker");
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
      public void windowClosed(java.awt.event.WindowEvent evt) {
        formWindowClosed(evt);
      }
    });
    getContentPane().setLayout(new java.awt.GridBagLayout());

    inputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    inputLabel.setText("Input Link:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    getContentPane().add(inputLabel, gridBagConstraints);

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
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 221;
    gridBagConstraints.ipady = 61;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    getContentPane().add(inputScrollPane, gridBagConstraints);

    outputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    outputLabel.setText("Output Link:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    getContentPane().add(outputLabel, gridBagConstraints);

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
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.ipadx = 221;
    gridBagConstraints.ipady = 61;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    getContentPane().add(outputScrollPane, gridBagConstraints);

    msgLabel.setText(" ");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    getContentPane().add(msgLabel, gridBagConstraints);

    buttonPanel.setLayout(new java.awt.GridBagLayout());

    getButton.setText("Get");
    getButton.setMaximumSize(new java.awt.Dimension(120, 29));
    getButton.setMinimumSize(new java.awt.Dimension(89, 29));
    getButton.setPreferredSize(new java.awt.Dimension(100, 29));
    getButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        getButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    buttonPanel.add(getButton, gridBagConstraints);

    tweakButton.setText("Tweak");
    tweakButton.setMaximumSize(new java.awt.Dimension(120, 29));
    tweakButton.setMinimumSize(new java.awt.Dimension(89, 29));
    tweakButton.setPreferredSize(new java.awt.Dimension(100, 29));
    tweakButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        tweakButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    buttonPanel.add(tweakButton, gridBagConstraints);

    launchButton.setText("Launch");
    launchButton.setMaximumSize(new java.awt.Dimension(120, 29));
    launchButton.setPreferredSize(new java.awt.Dimension(100, 29));
    launchButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        launchButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    buttonPanel.add(launchButton, gridBagConstraints);

    copyButton.setText("Copy");
    copyButton.setMaximumSize(new java.awt.Dimension(120, 29));
    copyButton.setMinimumSize(new java.awt.Dimension(89, 29));
    copyButton.setPreferredSize(new java.awt.Dimension(100, 29));
    copyButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        copyButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    buttonPanel.add(copyButton, gridBagConstraints);

    putButton.setText("Put");
    putButton.setMaximumSize(new java.awt.Dimension(120, 29));
    putButton.setMinimumSize(new java.awt.Dimension(89, 29));
    putButton.setPreferredSize(new java.awt.Dimension(100, 29));
    putButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        putButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    buttonPanel.add(putButton, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.5;
    getContentPane().add(buttonPanel, gridBagConstraints);

    optionPanel.setLayout(new java.awt.GridBagLayout());

    spCruftCheckBox.setText("Remove SharePoint Cruft?");
    spCruftCheckBox.setMaximumSize(new java.awt.Dimension(192, 29));
    spCruftCheckBox.setMinimumSize(new java.awt.Dimension(192, 29));
    spCruftCheckBox.setPreferredSize(new java.awt.Dimension(192, 29));
    spCruftCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        spCruftCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    optionPanel.add(spCruftCheckBox, gridBagConstraints);

    redirectCheckBox.setText("Insert Redirect?");
    redirectCheckBox.setMaximumSize(new java.awt.Dimension(128, 29));
    redirectCheckBox.setMinimumSize(new java.awt.Dimension(128, 29));
    redirectCheckBox.setPreferredSize(new java.awt.Dimension(128, 29));
    redirectCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        redirectCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    optionPanel.add(redirectCheckBox, gridBagConstraints);

    spacesCheckBox.setText("Show spaces as spaces?");
    spacesCheckBox.setMaximumSize(new java.awt.Dimension(182, 29));
    spacesCheckBox.setMinimumSize(new java.awt.Dimension(182, 29));
    spacesCheckBox.setPreferredSize(new java.awt.Dimension(182, 29));
    spacesCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        spacesCheckBoxActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
    optionPanel.add(spacesCheckBox, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.5;
    getContentPane().add(optionPanel, gridBagConstraints);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void inputTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputTextAreaFocusLost
    tweakThisLink();
  }//GEN-LAST:event_inputTextAreaFocusLost

  private void redirectCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redirectCheckBoxActionPerformed
    tweakThisLink();
  }//GEN-LAST:event_redirectCheckBoxActionPerformed

  private void tweakButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tweakButtonActionPerformed
    tweakThisLink();
  }//GEN-LAST:event_tweakButtonActionPerformed

  private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
    tweakThisLink();
    boolean ok = home.openURL(outputTextArea.getText());
    if (! ok) {
      msgLabel.setText("Error in launching link in Web browser");
    }
  }//GEN-LAST:event_launchButtonActionPerformed

  private void spCruftCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spCruftCheckBoxActionPerformed
    tweakThisLink();
  }//GEN-LAST:event_spCruftCheckBoxActionPerformed

  private void inputTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inputTextAreaFocusGained
    inputTextArea.selectAll();
  }//GEN-LAST:event_inputTextAreaFocusGained

  private void outputTextAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_outputTextAreaFocusGained
    tweakThisLink();
    outputTextArea.selectAll();
  }//GEN-LAST:event_outputTextAreaFocusGained

  private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
    tweakThisLink();
    outputTextArea.selectAll();
    outputTextArea.copy();
  }//GEN-LAST:event_copyButtonActionPerformed

  private void spacesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spacesCheckBoxActionPerformed
    outputTextArea.setWrapStyleWord(spacesCheckBox.isSelected());
    tweakThisLink();
  }//GEN-LAST:event_spacesCheckBoxActionPerformed

  private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    // System.out.println("LinkTweaker.formWindowClosed");
    windowClose();
  }//GEN-LAST:event_formWindowClosed

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    // System.out.println("LinkTweaker.formWindowClosing");
    windowClose();
  }//GEN-LAST:event_formWindowClosing

  private void getButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getButtonActionPerformed
    if (linkTweakerApp != null) {
      inputTextArea.setText(linkTweakerApp.getLinkToTweak());
    }
  }//GEN-LAST:event_getButtonActionPerformed

  private void putButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_putButtonActionPerformed
    if (linkTweakerApp != null) {
      linkTweakerApp.putTweakedLink(outputTextArea.getText(), outputTextArea.getName());
      this.setVisible(false);
    }
  }//GEN-LAST:event_putButtonActionPerformed

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
  private javax.swing.JPanel buttonPanel;
  private javax.swing.JButton copyButton;
  private javax.swing.JButton getButton;
  private javax.swing.JLabel inputLabel;
  private javax.swing.JScrollPane inputScrollPane;
  private javax.swing.JTextArea inputTextArea;
  private javax.swing.JButton launchButton;
  private javax.swing.JLabel msgLabel;
  private javax.swing.JPanel optionPanel;
  private javax.swing.JLabel outputLabel;
  private javax.swing.JScrollPane outputScrollPane;
  private javax.swing.JTextArea outputTextArea;
  private javax.swing.JButton putButton;
  private javax.swing.JCheckBox redirectCheckBox;
  private javax.swing.JCheckBox spCruftCheckBox;
  private javax.swing.JCheckBox spacesCheckBox;
  private javax.swing.JButton tweakButton;
  // End of variables declaration//GEN-END:variables
}
