package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.view.jobs.JobRename;
import org.woehlke.tools.model.jobs.rename.mq.gateway.JobRenameFilesPanelGateway;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;
import org.woehlke.tools.view.widgets.PanelButtonsRow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

@Component
public class JobRenameFilesPanel extends JPanel implements ActionListener, JobRenameFilesPanelGateway {

    private final ToolsApplicationProperties prop;
    private final JobRename jobRename;
    private final MyDirectoryChooser chooser;
    private final Queue<String> text;
    private final JTextField fieldDirectoryName;
    private final JButton buttonRenameFilesAndDirs;
    private final String frameTitle;
    private final String seperatorTxt;

    @Autowired
    public JobRenameFilesPanel(ToolsApplicationProperties prop,
                               JobRename jobRename,
                               MyDirectoryChooser chooser) {
        this.setName(prop.getJobRenameFiles());
        this.prop = prop;
        this.jobRename = jobRename;
        this.chooser = chooser;
        text = new ConcurrentLinkedQueue<String>();
        fieldDirectoryName = new JTextField(prop.getFieldDirectoryName());
        buttonRenameFilesAndDirs = new JButton(prop.getButtonRenameFilesAndDirs());
        frameTitle = prop.getJobRenameFiles();
        seperatorTxt = "\n"+prop.getSeperatorTxt()+"\n";
        initUI();
    }

    private JTextArea textArea;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;

    private void initUI() {
        BoxLayout layoutRenameFilesAndDirs = new BoxLayout(this, Y_AXIS);
        setLayout(layoutRenameFilesAndDirs);
        PanelButtonsRow panelRenameFilesAndDirsButtonRow = new PanelButtonsRow();
        fieldDirectoryName.setColumns(40);
        panelRenameFilesAndDirsButtonRow.add(fieldDirectoryName);
        panelRenameFilesAndDirsButtonRow.add(buttonRenameFilesAndDirs);
        this.setName(frameTitle);
        String text = prop.getJobRenameFiles() + seperatorTxt;
        int rows=200;
        int columns=512;
        textArea = new JTextArea(text,rows,columns);
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout( new BoxLayout(scrollPanePanel, Y_AXIS));
        scrollPanePanel.setName(frameTitle);
        int thickness = 10;
        Border border =  BorderFactory.createEmptyBorder(thickness,thickness,thickness,thickness);
        TitledBorder myTitledBorder =  BorderFactory.createTitledBorder(border,frameTitle);
        scrollPanePanel.setBorder(myTitledBorder);
        textArea.setEditable(true);
        scrollPane = new JScrollPane();
        scrollPane.add(textArea);
        scrollPane.setViewportView(textArea);
        scrollPane.setAutoscrolls(true);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePanel.add(scrollPane);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
        jobRename.setRootDirectory(rootDirectory);
        jobRename.start();
    }

    @Override
    public String listen(String payload) {
        this.updatePanel(payload);
        return payload;
    }

    public void updatePanel(String line) {
        text.add(line);
        if(text.size()>200){
            text.poll();
        }
        StringBuffer sb = new StringBuffer();
        for(String myline:text){
            sb.append(myline);
            sb.append("\n");
        }
        String newtext = sb.toString();
        int rows = text.size();
        textArea.setRows(rows);
        textArea.setText(newtext);
        scrollPane.updateUI();
    }
}
