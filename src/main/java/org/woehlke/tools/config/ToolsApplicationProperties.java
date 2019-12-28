package org.woehlke.tools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("org.woehlke.tools.config")
public class ToolsApplicationProperties {

    private String title;
    private String subtitle;
    private String copyright;
    private Integer width;
    private Integer height;
    private Boolean dryRun;
    private Boolean dbActive;
    private String jobRenameFiles;
    private String jobScaleImages;
    private String quitButton;
    private String fieldDirectoryName;
    private String buttonRenameFilesAndDirs;
    private String openedFileChooser;
    private String choosenNothing;
    private String startingJob;
    private String seperatorTxt;
    private String jobRenameFilesRunning;
    private String jobScaleImagesRunning;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    public Boolean getDbActive() {
        return dbActive;
    }

    public void setDbActive(Boolean dbActive) {
        this.dbActive = dbActive;
    }

    public String getJobRenameFiles() {
        return jobRenameFiles;
    }

    public void setJobRenameFiles(String jobRenameFiles) {
        this.jobRenameFiles = jobRenameFiles;
    }

    public String getJobScaleImages() {
        return jobScaleImages;
    }

    public void setJobScaleImages(String jobScaleImages) {
        this.jobScaleImages = jobScaleImages;
    }

    public String getQuitButton() {
        return quitButton;
    }

    public void setQuitButton(String quitButton) {
        this.quitButton = quitButton;
    }

    public String getFieldDirectoryName() {
        return fieldDirectoryName;
    }

    public void setFieldDirectoryName(String fieldDirectoryName) {
        this.fieldDirectoryName = fieldDirectoryName;
    }

    public String getButtonRenameFilesAndDirs() {
        return buttonRenameFilesAndDirs;
    }

    public void setButtonRenameFilesAndDirs(String buttonRenameFilesAndDirs) {
        this.buttonRenameFilesAndDirs = buttonRenameFilesAndDirs;
    }

    public String getOpenedFileChooser() {
        return openedFileChooser;
    }

    public void setOpenedFileChooser(String openedFileChooser) {
        this.openedFileChooser = openedFileChooser;
    }

    public String getChoosenNothing() {
        return choosenNothing;
    }

    public void setChoosenNothing(String choosenNothing) {
        this.choosenNothing = choosenNothing;
    }

    public String getStartingJob() {
        return startingJob;
    }

    public void setStartingJob(String startingJob) {
        this.startingJob = startingJob;
    }

    public String getSeperatorTxt() {
        return seperatorTxt;
    }

    public void setSeperatorTxt(String seperatorTxt) {
        this.seperatorTxt = seperatorTxt;
    }

    public String getJobRenameFilesRunning() {
        return jobRenameFilesRunning;
    }

    public void setJobRenameFilesRunning(String jobRenameFilesRunning) {
        this.jobRenameFilesRunning = jobRenameFilesRunning;
    }

    public String getJobScaleImagesRunning() {
        return jobScaleImagesRunning;
    }

    public void setJobScaleImagesRunning(String jobScaleImagesRunning) {
        this.jobScaleImagesRunning = jobScaleImagesRunning;
    }
}
