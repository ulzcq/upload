package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/** 파일을 통으로 저장하는게 아닌, 파일의 이름 정보만 저장하기 위해 ItemForm과 Item을 분리해서 구분*/
@Data
public class ItemForm {
    private Long ItemId;
    private String itemName;
    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
