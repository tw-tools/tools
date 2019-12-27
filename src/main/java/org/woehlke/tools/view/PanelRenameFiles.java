package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.PanelRenameFilesGateway;
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

@Component
public class PanelRenameFiles extends JPanel implements ActionListener, PanelRenameFilesGateway {

    private final JobRenameFiles jobRenameFiles;
    private final MyDirectoryChooser chooser;
    private final StringBuffer text;

    @Autowired
    public PanelRenameFiles(JobRenameFiles jobRenameFiles,
                            MyDirectoryChooser chooser) {
        this.jobRenameFiles = jobRenameFiles;
        this.chooser = chooser;
        text = new StringBuffer();
        initUI();
    }

    private JTextField fieldDirectoryName = new JTextField("Please choose Root Directory");
    private JButton buttonRenameFilesAndDirs = new JButton("Choose Root Directory and start");
    private JTextArea textArea;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;
    private String frameTitle = "Running: Rename Files and Dirs";

    private final String seperatorTxt = "\n---------------------\n";

    private void initUI() {
        BoxLayout layoutRenameFilesAndDirs = new BoxLayout(this, Y_AXIS);
        setLayout(layoutRenameFilesAndDirs);
        PanelButtonsRow panelRenameFilesAndDirsButtonRow = new PanelButtonsRow();
        fieldDirectoryName.setColumns(40);
        panelRenameFilesAndDirsButtonRow.add(fieldDirectoryName);
        panelRenameFilesAndDirsButtonRow.add(buttonRenameFilesAndDirs);
        this.setName(frameTitle);
        String text = "Rename Files and Dirs" + seperatorTxt;
        int rows=40;
        int columns=255;
        textArea = new JTextArea(text,rows,columns);
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout( new BoxLayout(scrollPanePanel, Y_AXIS));
        scrollPanePanel.setName(frameTitle);
        int thickness = 10;
        Border border =  BorderFactory.createEmptyBorder(thickness,thickness,thickness,thickness);
        TitledBorder myTitledBorder =  BorderFactory.createTitledBorder(border,frameTitle);
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
           this.updatePanel("buttonDirectoryName Pressed");
           File rootDirectory = chooser.openDialog(this);
           if(rootDirectory != null){
                this.start(rootDirectory);
            } else {
                this.updatePanel("choosen: NOTHING");
            }
        }
    }

    public void start(File rootDirectory){
        this.fieldDirectoryName.setText(rootDirectory.getAbsolutePath());
        this.updatePanel("STARTING... with root Directory "+rootDirectory.getAbsolutePath()+seperatorTxt);
        boolean dryRun = true;
        jobRenameFiles.setRootDirectory(rootDirectory, dryRun);
        jobRenameFiles.start();
    }

    @Override
    public String listen(String payload) {
        this.updatePanel(payload);
        return payload;
    }

    public void updatePanel(String line) {
        text.append(line + "\n");
        int rows = text.length()+1;
        String newtext = text.toString();
        textArea.setRows(rows);
        textArea.setText(newtext);
    }
}
