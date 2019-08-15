package org.woehlke.tools.filenames;

import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

public class RenameFilesAndDirs implements Runnable {

    private final String dataRootDir;

    public RenameFilesAndDirs(String args[]) {
        this.dataRootDir = args[0];
    }

    @Override
    public void run() {
        line();
        System.out.print("RenameFilesAndDirs: "+this.dataRootDir);
        line();
        System.out.println();
        renameDirectories();
        line();
        renameFiles();
        System.out.println("fertig: RenameFilesAndDirs: "+this.dataRootDir);
        line();
    }

    private void renameDirectories(){
        TraverseDirs runner = new TraverseDirs(this.dataRootDir);
        runner.run();
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(runner);
        renameDirectoriesAndFiles.run();
    }

    private void renameFiles(){
        TraverseDirs runner = new TraverseDirs(this.dataRootDir);
        runner.run();
        TraverseFiles traverseFiles = new TraverseFiles(this.dataRootDir);
        traverseFiles.run();
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(traverseFiles);
        renameDirectoriesAndFiles.run();
    }

    private void line(){
        System.out.println("*********************");
    }

    public static void main(String args[]){
        RenameFilesAndDirs app = new RenameFilesAndDirs(args);
        app.run();
    }
}
