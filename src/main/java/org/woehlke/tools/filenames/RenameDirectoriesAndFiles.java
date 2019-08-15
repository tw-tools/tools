package org.woehlke.tools.filenames;


import org.woehlke.tools.filesystem.Traverse;

import java.io.File;
import java.util.Deque;

public class RenameDirectoriesAndFiles implements Runnable  {

    private final Traverse runner;

    public RenameDirectoriesAndFiles(final Traverse runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        Deque<File> stack =  this.runner.getResult();
        while (!stack.isEmpty()){
            File directory = stack.pop();
            String parentPath = directory.getParent();
            String oldFilename = directory.getName();
            String newFilename = FilenameTransform.getNewName(oldFilename);
            if(oldFilename.compareTo(newFilename)!=0){
                String newFilepath = parentPath + File.separator + newFilename;
                File targetFile = new File(newFilepath);
                System.out.println("rename: "+directory.getAbsolutePath()+" -> "+targetFile.getAbsolutePath());
                //directory.renameTo(targetFile);
            }
        }
    }


}
