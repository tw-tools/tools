package org.woehlke.tools.view.common;

import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;
import org.woehlke.tools.view.widgets.PanelButtonsRow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


public abstract class AbstractJobPanel extends JPanel implements JobPanel {

    private final ApplicationProperties cfg;
    private final MmiProperties prop;
    private final Queue<String> textAreaBuffer;
    private final JTextField fieldRoorDirectory;
    private final String jobName;
    protected final MyDirectoryChooser chooser;
    protected final JButton buttonChooseRootDirAndStartJob;

    private JTextArea textArea;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;
    private PanelButtonsRow buttonRow;

    public AbstractJobPanel(
        final String jobName,
        ApplicationProperties cfg,
        MmiProperties prop,
        MyDirectoryChooser chooser
    ) {
        this.cfg = cfg;
        this.prop = prop;
        this.chooser = chooser;
        this.jobName = jobName;
        this.setName( this.jobName );
        this.textAreaBuffer = new ConcurrentLinkedQueue<>();
        this.fieldRoorDirectory = new JTextField(prop.getFieldRoorDirectory());
        this.buttonChooseRootDirAndStartJob = new JButton(prop.getButtonRenameFilesAndDirs());
    }

    protected void initUI() {
        getButtonRow();
        getTextArea();
        getScrollPanePanel();
        getCaret();
        this.setLayout(new BoxLayout(this, Y_AXIS));
        this.setName(jobName);
        this.add(scrollPanePanel);
        this.add(buttonRow);
        //setSize(800, 500);
    }

    public abstract void start(File rootDirectory);

    protected void started(File rootDirectory){
        this.buttonChooseRootDirAndStartJob.setText(rootDirectory.getAbsolutePath());
        this.updatePanel(getStartMessage(rootDirectory));
    }

    private void updateMyPanel(String line) {
        int textAreaMaxLines = 200; //TODO: cfg
        textAreaBuffer.add(line);
        if(textAreaBuffer.size()> textAreaMaxLines){
            textAreaBuffer.poll();
        }
        StringBuffer sb = new StringBuffer();
        for(String myline:textAreaBuffer){
            sb.append(myline);
            sb.append(newLine);
        }
        String newtext = sb.toString();
        int rows = textAreaBuffer.size();
        textArea.setRows(rows);
        textArea.setText(newtext);
        scrollPane.updateUI();
    }

    protected void updatePanel(String line) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                updateMyPanel(line);
            }
        });
    }

    private void getButtonRow(){
        int fieldDirectoryColumns = 40; //TODO: cfg
        fieldRoorDirectory.setColumns(fieldDirectoryColumns);
        buttonRow = new PanelButtonsRow();
        buttonRow.add(fieldRoorDirectory);
        buttonRow.add(buttonChooseRootDirAndStartJob);
    }

    private void getTextArea(){
        int textAreaRows = 200; //TODO: cfg
        int textAreaColumns = 512; //TODO: cfg
        textArea = new JTextArea(
            getJobnameForTextArea(),
            textAreaRows,
            textAreaColumns
        );
        textArea.setEditable(true);
    }

    private TitledBorder getTitledBorder(){
        int borderThickness = 10; //TODO: cfg
        Border border =  BorderFactory.createEmptyBorder(
            borderThickness,borderThickness,borderThickness,borderThickness
        );
        return BorderFactory.createTitledBorder(border,jobName);
    }

    private JScrollPane getJScrollPane(){
        JScrollPane myJScrollPane = new JScrollPane();
        myJScrollPane.add(textArea);
        myJScrollPane.setViewportView(textArea);
        myJScrollPane.setAutoscrolls(true);
        myJScrollPane.setWheelScrollingEnabled(true);
        myJScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        return myJScrollPane;
    }

    private void getScrollPanePanel(){
        TitledBorder myTitledBorder = getTitledBorder();
        scrollPane = getJScrollPane();
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout( new BoxLayout(scrollPanePanel, Y_AXIS));
        scrollPanePanel.setName(jobName);
        scrollPanePanel.setBorder(myTitledBorder);
        scrollPanePanel.add(scrollPane);
    }

    private void getCaret(){
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    private String getStartMessage(File rootDirectory){
        String startMsg1 = "STARTING... ";  //TODO: cfg
        String startMsg2 = " with root Directory: ";
        return newLine
            + newLine
            + prop.getSeperatorTxt()
            + newLine
            + startMsg1
            + jobName
            + startMsg2
            + rootDirectory.getAbsolutePath()
            + newLine
            + prop.getSeperatorTxt();
    }

    private String getJobnameForTextArea(){
        return prop.getSeperatorTxt()
            + newLine
            + jobName
            + newLine
            + prop.getSeperatorTxt()
            + newLine;
    }

    protected final String blank = " ";
    protected final String newLine = "\n";

    protected void myActionPerformed(ActionEvent e) {
        String msgDo = "buttonDirectoryName Pressed";
        String msgCancel = "choosen: NOTHING";
        if (e.getSource() == buttonChooseRootDirAndStartJob) {
            this.updatePanel(msgDo);
            File rootDirectory = chooser.openDialog(this);
            if(rootDirectory != null){
                this.start(rootDirectory);
            } else {
                this.updatePanel(msgCancel);
            }
        }
    }
}
