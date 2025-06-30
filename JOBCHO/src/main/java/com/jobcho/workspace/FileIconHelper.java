package com.jobcho.workspace;

import org.springframework.stereotype.Component;

@Component("fileIconHelper") 
public class FileIconHelper {

	public String getFileIcon(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "file-icon.png";
        }

        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return switch (ext) {
            case "pdf" -> "file-icon.png";
            case "zip", "rar" -> "file-icon.png";
            case "doc", "docx" -> "file-icon.png";
            case "xls", "xlsx" -> "file-icon.png";
            case "ppt", "pptx" -> "file-icon.png";
            case "txt" -> "file-icon.png";
            default -> "file-icon.png";
        };
    }
}
