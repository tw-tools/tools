package org.woehlke.tools.view.tabs;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.model.services.JobService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import java.awt.*;

@Log
@Getter
@Component
public class JobTabTree extends JPanel {

    private final ApplicationProperties prop;
    private final JobService jobService;
    private final String frameTitle;


    JTextField textField = new JTextField();
    JScrollPane scrollPane = new JScrollPane();

    JTree tree;

    MyRenderer renderer = new MyRenderer();

    DefaultMutableTreeNode nba = new DefaultMutableTreeNode("National Basketball Association");

    DefaultMutableTreeNode western = new DefaultMutableTreeNode("Western Conference");

    DefaultMutableTreeNode pacific = new DefaultMutableTreeNode("Pacific Division Teams");

    DefaultMutableTreeNode midwest = new DefaultMutableTreeNode("Midwest Division Teams");

    DefaultMutableTreeNode denver = new DefaultMutableTreeNode("Denver");

    DefaultMutableTreeNode eastern = new DefaultMutableTreeNode("Eastern Conference");

    DefaultMutableTreeNode atlantic = new DefaultMutableTreeNode("Atlantic Division Teams");
    DefaultMutableTreeNode central = new DefaultMutableTreeNode("Central Division Teams");

    @Autowired
    public JobTabTree(ApplicationProperties prop, JobService jobService) {
        this.prop = prop;
        this.jobService = jobService;
        this.frameTitle = "Job Tree Test";
        initUI();
    }

    private JPanel scrollPanePanel;

    private void initUI() {
        nba.add(western);
        nba.add(eastern);
        western.add(pacific);
        western.add(midwest);
        eastern.add(atlantic);
        eastern.add(central);
        pacific.add(new DefaultMutableTreeNode("Los Angeles (Lakers)"));
        pacific.add(new DefaultMutableTreeNode("Los Angeles (Clippers)"));
        pacific.add(new DefaultMutableTreeNode("San Francisco"));
        pacific.add(new DefaultMutableTreeNode("Seattle"));
        pacific.add(new DefaultMutableTreeNode("Phoenix"));
        pacific.add(new DefaultMutableTreeNode("Portland"));
        pacific.add(new DefaultMutableTreeNode("Sacramento"));


        midwest.add(new DefaultMutableTreeNode("Utah"));
        midwest.add(new DefaultMutableTreeNode("San Antonio"));
        midwest.add(new DefaultMutableTreeNode("Houston"));
        midwest.add(new DefaultMutableTreeNode("Minnesota"));
        midwest.add(new DefaultMutableTreeNode("Vancouver"));
        midwest.add(new DefaultMutableTreeNode("Dallas"));

        midwest.add(denver);
        atlantic.add(new DefaultMutableTreeNode("Miami"));
        atlantic.add(new DefaultMutableTreeNode("New York"));
        atlantic.add(new DefaultMutableTreeNode("New Jersey"));
        atlantic.add(new DefaultMutableTreeNode("Washington"));
        atlantic.add(new DefaultMutableTreeNode("Orlando"));
        atlantic.add(new DefaultMutableTreeNode("Boston"));
        atlantic.add(new DefaultMutableTreeNode("Philadelphia"));


        central.add(new DefaultMutableTreeNode("Chicago"));
        central.add(new DefaultMutableTreeNode("Indiana"));
        central.add(new DefaultMutableTreeNode("Charlotte"));
        central.add(new DefaultMutableTreeNode("Atlanta"));
        central.add(new DefaultMutableTreeNode("Cleveland"));
        central.add(new DefaultMutableTreeNode("Detroit"));
        central.add(new DefaultMutableTreeNode("Milwaukee"));
        central.add(new DefaultMutableTreeNode("Toronto"));
        tree = new JTree(nba);

        setLayout(new BorderLayout());
        tree.setCellRenderer(renderer);
        tree.addTreeSelectionListener(new TreeHandler());
        scrollPane.getViewport().add(tree);
        add("Center", scrollPane);
        add("South", textField);
    }

    public class TreeHandler implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            String text = path.getPathComponent(path.getPathCount() - 1).toString();
            if (path.getPathCount() > 3) {
                text += ": ";
                text += Integer.toString((int) (Math.random() * 50)) + " Wins ";
                text += Integer.toString((int) (Math.random() * 50)) + " Losses";
            }
            textField.setText(text);
        }
    }

    class MyRenderer extends JLabel implements TreeCellRenderer {
        public java.awt.Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                                                      boolean expanded, boolean leaf, int row, boolean hasFocus) {
            setText(value.toString() + "                   ");
            return this;
        }
    }
}
