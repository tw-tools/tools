package org.woehlke.tools.config.application;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties("org.woehlke.tools.gui")
@Valid
@Validated
public class ToolsGuiProperties {

    @NotBlank private String title;
    @NotBlank private String subtitle;
    @NotBlank private String copyright;
    @NotBlank private String jobRenameFiles;
    @NotBlank private String jobScaleImages;
    @NotBlank private String quitButton;
    @NotBlank private String fieldRoorDirectory;
    @NotBlank private String buttonRenameFilesAndDirs;
    @NotBlank private String openedFileChooser;
    @NotBlank private String choosenNothing;
    @NotBlank private String startingJob;
    @NotBlank private String seperatorTxt;
    @NotBlank private String jobRenameFilesRunning;
    @NotBlank private String jobScaleImagesRunning;
    @NotBlank private String jobtableTitle;
    @NotBlank private String shrinkJpgImage;

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

    public String getFieldRoorDirectory() {
        return fieldRoorDirectory;
    }

    public void setFieldRoorDirectory(String fieldRoorDirectory) {
        this.fieldRoorDirectory = fieldRoorDirectory;
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

    public String getShrinkJpgImage() {
        return shrinkJpgImage;
    }

    public void setShrinkJpgImage(String shrinkJpgImage) {
        this.shrinkJpgImage = shrinkJpgImage;
    }
}
