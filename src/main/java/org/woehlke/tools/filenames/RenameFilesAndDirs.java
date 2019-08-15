package org.woehlke.tools.filenames;

import org.woehlke.tools.filesystem.TraverseDirs;
import org.woehlke.tools.filesystem.TraverseFiles;

public class RenameFilesAndDirs implements Runnable {

    private final String dataRootDir;

    private final boolean dryRun = false;

    private final TraverseDirs traverseDirs;
    private final TraverseFiles traverseFiles;

    public RenameFilesAndDirs(String args[]) {
        this.dataRootDir = args[0];
        traverseDirs = new TraverseDirs(this.dataRootDir,this.dryRun);
        traverseFiles = new TraverseFiles(this.dataRootDir,this.dryRun);
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
        traverseDirs.run();
        RenameDirectoriesAndFiles renameDirectoriesAndFiles = new RenameDirectoriesAndFiles(traverseDirs);
        renameDirectoriesAndFiles.run();
    }

    private void renameFiles(){
        traverseDirs.run();
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
