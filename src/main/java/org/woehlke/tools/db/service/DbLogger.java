package org.woehlke.tools.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.entity.Protokoll;

@Service
public class DbLogger {

    private final ProtokollService protokollService;

    @Autowired
    public DbLogger(ProtokollService protokollService) {
        this.protokollService = protokollService;
    }

    public void info(String msg) {
        String textAreaMsg = msg;
        Protokoll newProtokoll = new Protokoll(textAreaMsg);
        protokollService.add(newProtokoll);
    }

    public StringBuffer getInfo() {
        StringBuffer b = new StringBuffer();
        for(Protokoll p :protokollService.getAll()){
            b.append(p.getLine());
            b.append("\n");
        }
        return b;
    }
}
