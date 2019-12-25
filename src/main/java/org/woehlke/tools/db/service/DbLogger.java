package org.woehlke.tools.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.entity.Logbuch;
import org.woehlke.tools.view.LoggingCallback;

@Service
public class DbLogger implements LoggingCallback {

    private final ProtokollService protokollService;

    @Autowired
    public DbLogger(ProtokollService protokollService) {
        this.protokollService = protokollService;
    }

    public void info(String msg) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        protokollService.add(newLogbuch);
    }

    public void info(String msg, String category, String job) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        newLogbuch.setJob(job);
        protokollService.add(newLogbuch);
    }

    public void info(String msg, String category) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        protokollService.add(newLogbuch);
    }

    public StringBuffer getInfo() {
        StringBuffer b = new StringBuffer();
        for(Logbuch p :protokollService.getAll()){
            b.append(p.getLine());
            b.append("\n");
        }
        return b;
    }
}
