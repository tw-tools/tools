package org.woehlke.tools.view.tabs;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.model.services.JobService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


@Log
@Getter
@Component
public class JobTablePanel extends JPanel {

    private final ApplicationProperties prop;
    private final JobService jobService;
    private final String frameTitle;

    @Autowired
    public JobTablePanel(ApplicationProperties prop, JobService jobService) {
        this.prop = prop;
        this.jobService = jobService;
        this.frameTitle = "JOB Table Test";
        initUI();
    }

    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;

    private void initUI() {
        BoxLayout layoutRenameFilesAndDirs = new BoxLayout(this, Y_AXIS);
        setLayout(layoutRenameFilesAndDirs);
        this.setName(frameTitle);
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout( new BoxLayout(scrollPanePanel, Y_AXIS));
        scrollPanePanel.setName(frameTitle);
        int thickness = 10;
        Border border =  BorderFactory.createEmptyBorder(thickness,thickness,thickness,thickness);
        TitledBorder myTitledBorder =  BorderFactory.createTitledBorder(border,frameTitle);
        scrollPanePanel.setBorder(myTitledBorder);
        int zeilenanzahl = 30;
        String [] spalten = {"spalte1", "spalte2", "spalte3", "spalte4", "spalte5"};
        String [][] daten = new String [zeilenanzahl][5];
        for(int i=0; i< zeilenanzahl; i++){
            for(int k=0;k<spalten.length;k++){
                daten[i][k]  = "wert der spalte "+k+" und zeile "+i;
            }
        }
        JTable jTable = new JTable(daten, spalten);
        scrollPane = new JScrollPane();
        scrollPane.add(jTable);
        scrollPane.setViewportView(jTable);
        scrollPane.setAutoscrolls(true);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanePanel.add(scrollPane);
        this.setLayout( new BoxLayout(this, Y_AXIS));
        this.add(scrollPanePanel);
    }
}
