package org.woehlke.tools.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class RenameFileAndDirsDialog extends JDialog {

    public RenameFileAndDirsDialog() {
        initUI();
    }

    private JButton ok = new JButton("Start");
    private JButton cancel = new JButton("Cancel");
    private JTextField fieldDirectoryName = new JTextField("You must choose the Root Directory first");
    private JButton buutonDirectoryName = new JButton("Choose Root Directory");


    private JPanel buttonFooter = new JPanel();
    private JPanel directoryChosser = new JPanel();

    private void initUI() {
        setTitle("Rename Files and Dirs");
        rootPane.setLayout( new BoxLayout(rootPane, Y_AXIS));
        buttonFooter.setLayout(new FlowLayout());
        buttonFooter.add( ok );
        buttonFooter.add(cancel);
        directoryChosser.setLayout(new FlowLayout());
        directoryChosser.add(fieldDirectoryName);
        directoryChosser.add(buutonDirectoryName);
        rootPane.add(directoryChosser);
        rootPane.add(buttonFooter);
        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }


}
