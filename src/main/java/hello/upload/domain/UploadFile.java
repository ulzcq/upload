package hello.upload.domain;

import lombok.Data;

/**
 * 파일명이 겹칠 수 있으므로 내부에서 관리하는 별도의 파일명이 필요
 */
@Data
public class UploadFile {

    private String uploadFileName; //고객이 업로드한 파일명
    private String storeFileName; //서버 내부에서 관리하는 파일명

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
