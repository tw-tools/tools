package org.woehlke.tools.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.Logbuch;
import org.woehlke.tools.db.LogbuchQueueService;
import org.woehlke.tools.jobs.JobRenameFiles;
import org.woehlke.tools.view.common.MyDirectoryChooser;
import org.woehlke.tools.view.common.PanelButtonsRow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.BoxLayout.Y_AXIS;
import static org.woehlke.tools.config.SpringIntegrationConfig.LOGBUCH_UPDATED_QUEUE;

@Component
public class PanelRenameFilesAndDirs extends JPanel implements ActionListener {

    private final JobRenameFiles jobRenameFiles;
    private final LogbuchQueueService logbuchQueueService;
    private final MyDirectoryChooser chooser;


    @Autowired
    public PanelRenameFilesAndDirs(JobRenameFiles jobRenameFiles,
                                   LogbuchQueueService logbuchQueueService, MyDirectoryChooser chooser) {
        this.jobRenameFiles = jobRenameFiles;
        this.logbuchQueueService = logbuchQueueService;
        this.chooser = chooser;
        initUI();
    }

    private JTextField fieldDirectoryName = new JTextField("You must choose the Root Directory first");
    private JButton buttonRenameFilesAndDirs = new JButton("Rename Files and Dirs");
    private JTextArea textArea;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;
    String frameTitle = "Running: Rename Files and Dirs";
    String title = "Protokoll: : Rename Files and Dirs";

    private void initUI() {
        BoxLayout layoutRenameFilesAndDirs = new BoxLayout(this, Y_AXIS);
        setLayout(layoutRenameFilesAndDirs);
        PanelButtonsRow panelRenameFilesAndDirsButtonRow = new PanelButtonsRow();
        panelRenameFilesAndDirsButtonRow.add(fieldDirectoryName);
        panelRenameFilesAndDirsButtonRow.add(buttonRenameFilesAndDirs);
        this.setName(frameTitle);
        textArea = new JTextArea(2000, 300);
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout( new BoxLayout(scrollPanePanel, Y_AXIS));
        scrollPanePanel.setName(frameTitle);
        int thickness = 10;
        Border border =  BorderFactory.createEmptyBorder(thickness,thickness,thickness,thickness);
        TitledBorder myTitledBorder =  BorderFactory.createTitledBorder(border,title);
        scrollPanePanel.setBorder(myTitledBorder);
        textArea.setEditable(true);
        scrollPane = new JScrollPane(textArea);
        scrollPanePanel.add(scrollPane);
        this.setLayout( new BoxLayout(this, Y_AXIS));
        this.add(scrollPanePanel);
        add(panelRenameFilesAndDirsButtonRow);
        setSize(800, 500);
        buttonRenameFilesAndDirs.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource()== buttonRenameFilesAndDirs ) {
            this.log.info("buttonDirectoryName Pressed");
           File rootDirectory = chooser.openDialog(this);
            if(rootDirectory != null){
                this.log.info("choosen: "+rootDirectory.getAbsolutePath());
                this.start(rootDirectory);
            } else {
                this.log.info("choosen: NOTHING");
            }
        }
    }

    public void start(File rootDirectory){
        boolean dryRun = true;
        jobRenameFiles.setRootDirectory(rootDirectory, dryRun);
        jobRenameFiles.start();
    }

    public void info(String msg) {
        this.logbuchQueueService.info(msg);
    }

    public void info(String msg, String category, String job) {
        this.logbuchQueueService.info(msg,category,job);
    }

    public void info(String msg, String category) {
        this.logbuchQueueService.info(msg,category);
    }


    public void receiveMessage(Logbuch logbuch) {
        String msg ="received Message from Queue " + LOGBUCH_UPDATED_QUEUE + " msg = " + logbuch.toString();
        log.info(msg);
        StringBuffer b =  this.logbuchQueueService.getInfo();
        textArea.setRows(b.length());
        textArea.setText(b.toString());
    }

    private Log log = LogFactory.getLog(PanelRenameFilesAndDirs.class);
}
