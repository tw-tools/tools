package org.woehlke.tools.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.LogbuchQueueService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class RenameFileAndDirsDialog extends JDialog implements ActionListener {

    private final LogbuchQueueService log;
    private final MyDirectoryChooser chooser;
    private final RenameFilesAndDirsDialog jobDialog;

    @Autowired
    public RenameFileAndDirsDialog(LogbuchQueueService log, MyDirectoryChooser myDirectoryChooser, RenameFilesAndDirsDialog jobDialog) {
        this.chooser = myDirectoryChooser;
        this.jobDialog = jobDialog;
        this.log = log;
        initUI();
    }

    private JTextField fieldDirectoryName = new JTextField("You must choose the Root Directory first");
    private JButton buutonDirectoryName = new JButton("Choose");
    private JButton cancel = new JButton("Cancel");
    private JPanel directoryChosser = new JPanel();

    private void initUI() {
        setTitle("choose the Root Directory");
        rootPane.setLayout( new BoxLayout(rootPane, Y_AXIS));
        directoryChosser.setLayout(new FlowLayout());
        directoryChosser.add(fieldDirectoryName);
        directoryChosser.add(buutonDirectoryName);
        directoryChosser.add(cancel);
        rootPane.add(directoryChosser);
        cancel.addActionListener(this);
        buutonDirectoryName.addActionListener(this);
        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource()== cancel ) {
            this.log.info("cancel");
            dispose();
        } else if (e.getSource()== buutonDirectoryName ) {
            this.log.info("buttonDirectoryName Pressed");
            File rootDirectory = chooser.openDialog(this);
            if(rootDirectory != null){
                this.log.info("choosen: "+rootDirectory.getAbsolutePath());
                jobDialog.start(rootDirectory);
                dispose();
            } else {
                this.log.info("choosen: NOTHING");
            }
        }
    }

}
