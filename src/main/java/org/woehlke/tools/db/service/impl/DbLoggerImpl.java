package org.woehlke.tools.db.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.entity.Logbuch;
import org.woehlke.tools.db.service.DbLogger;
import org.woehlke.tools.db.service.ProtokollService;

@Service
public class DbLoggerImpl implements DbLogger {

    private final ProtokollService protokollService;

    @Autowired
    public DbLoggerImpl(ProtokollService protokollService) {
        this.protokollService = protokollService;
    }

    @Override
    public void info(String msg) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        protokollService.add(newLogbuch);
    }

    @Override
    public void info(String msg, String category, String job) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        newLogbuch.setJob(job);
        protokollService.add(newLogbuch);
    }

    @Override
    public void info(String msg, String category) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        protokollService.add(newLogbuch);
    }

    @Override
    public StringBuffer getInfo() {
        StringBuffer b = new StringBuffer();
        for(Logbuch p :protokollService.getAll()){
            b.append(p.getLine());
            b.append("\n");
        }
        return b;
    }
}
