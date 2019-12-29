package org.woehlke.tools.config.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties("org.woehlke.tools.config")
@Valid
@Validated
public class ToolsApplicationProperties {

    @NotBlank private  String title;
    @NotBlank private  String subtitle;
    @NotBlank private  String copyright;
    @NotNull private  Integer width;
    @NotNull private  Integer height;
    @NotNull private  Boolean dryRun;
    @NotNull private  Boolean dbActive;
    @NotBlank private  String jobRenameFiles;
    @NotBlank private  String jobScaleImages;
    @NotBlank private  String quitButton;
    @NotBlank private  String fieldDirectoryName;
    @NotBlank private  String buttonRenameFilesAndDirs;
    @NotBlank private  String openedFileChooser;
    @NotBlank private  String choosenNothing;
    @NotBlank private  String startingJob;
    @NotBlank private  String seperatorTxt;
    @NotBlank private  String jobRenameFilesRunning;
    @NotBlank private  String jobScaleImagesRunning;
    @NotBlank private  String jobtableTitle;

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

    public String getJobtableTitle() {
        return jobtableTitle;
    }

    public void setJobtableTitle(String jobtableTitle) {
        this.jobtableTitle = jobtableTitle;
    }
}
