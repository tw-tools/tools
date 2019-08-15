package org.woehlke.tools.filesystem;

import org.woehlke.filesortwin10.oodm.model.FileProxy;
import org.woehlke.filesortwin10.oodm.model.transients.FileInfo;
import org.woehlke.filesortwin10.oodm.model.transients.FileInfoImagePng;

public interface InfoImagePng {

    FileInfoImagePng analyseFileContentInformation(FileProxy file);

    FileInfo getFileInfo(FileProxy file);
}
