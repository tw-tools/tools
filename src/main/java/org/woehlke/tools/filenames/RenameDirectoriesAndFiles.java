package org.woehlke.tools.filenames;


import org.woehlke.tools.filesystem.Traverse;

import java.io.File;
import java.util.Deque;
import java.util.logging.Logger;

public class RenameDirectoriesAndFiles implements Runnable  {

    private final Traverse runner;

    private static final Logger log = Logger.getLogger(RenameDirectoriesAndFiles.class.getName());

    public RenameDirectoriesAndFiles(final Traverse runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        Deque<File> stack =  this.runner.getResult();
        while (!stack.isEmpty()){
            File srcFile = stack.pop();
            String parentPath = srcFile.getParent();
            String oldFilename = srcFile.getName();
            String newFilename = FilenameTransform.getNewName(oldFilename);
            if(oldFilename.compareTo(newFilename)!=0){
                String newFilepath = parentPath + File.separator + newFilename;
                File targetFile = new File(newFilepath);
                log.info("rename: "+srcFile.getAbsolutePath()+" -> "+targetFile.getAbsolutePath());
                if(!runner.isDryRun()){
                    srcFile.renameTo(targetFile);
                }
            }
        }
    }

}
