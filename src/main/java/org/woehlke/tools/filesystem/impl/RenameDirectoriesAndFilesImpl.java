package org.woehlke.tools.filesystem.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.filesystem.RenameDirectoriesAndFiles;
import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

import java.io.File;
import java.util.Deque;

@Component
public class RenameDirectoriesAndFilesImpl implements RenameDirectoriesAndFiles {

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;
    private final LogbuchQueueService log;

    @Autowired
    public RenameDirectoriesAndFilesImpl(final TraverseDirs traverseDirs,
                                         final TraverseFiles traverseFiles,
                                         final LogbuchQueueService log) {
        this.traverseDirs = traverseDirs;
        this.traverseFiles = traverseFiles;
        this.log = log;
    }

    @Override
    public void run() {
        Deque<File> stack =  this.traverseDirs.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String parentPath = srcFile.getParent();
            String oldFilename = srcFile.getName();
            String newFilename = FilenameTransform.getNewName(oldFilename);
            if(oldFilename.compareTo(newFilename)!=0){
                String newFilepath = parentPath + File.separator + newFilename;
                File targetFile = new File(newFilepath);
                String msg="rename: "+srcFile.getAbsolutePath()+" -> "+targetFile.getAbsolutePath();
                String category = "rename";
                String job = "RenameDirectoriesAndFiles";
                log.info(msg,category,job);
                if(!traverseDirs.isDryRun()){
                    srcFile.renameTo(targetFile);
                }
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){

            }
        }
    }

}
