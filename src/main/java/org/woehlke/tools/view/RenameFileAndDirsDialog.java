package org.woehlke.tools.view;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class RenameFileAndDirsDialog extends JDialog implements ActionListener {

    public RenameFileAndDirsDialog() {
        initUI();
    }

    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("Cancel");
    private JTextField fieldDirectoryName = new JTextField("You must choose the Root Directory first");
    private JButton buutonDirectoryName = new JButton("Choose Root Directory");

    private JPanel buttonFooter = new JPanel();
    private JSeparator separator = new JSeparator();
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
        rootPane.add(separator);
        rootPane.add(buttonFooter);
        ok.addActionListener(this);
        cancel.addActionListener(this);
        buutonDirectoryName.addActionListener(this);
        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== ok ) {
            System.out.println("OK");
        } else if (e.getSource()== cancel ) {
            System.out.println("cancel");
            dispose();
        } else if (e.getSource()== buutonDirectoryName ) {
            System.out.println("Starte");
        }
    }

}
