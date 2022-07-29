package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}") //스프링꺼 써야함, 파일 경로 가져옴
    private String fileDir;

    @GetMapping("/upload")
    public String newFile(){
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws IOException, ServletException {
        log.info("request={}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts={}", parts);

        for (Part part : parts) {
            log.info("=== PART ===");
            log.info("name={}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("headerName={}: {}", headerName, part.getHeader(headerName)); //클라이언트가 전달한 파일명
            }

            //편의 메서드, 헤더에서 뽑을 수 있게 파싱해줌
            //content-disposition; filename
            log.info("submittedFilename={}", part.getSubmittedFileName());
            log.info("size={}", part.getSize());

            //데이터 읽기
            InputStream inputStream = part.getInputStream(); //Part의 전송 데이터를 읽을 수 있다.
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);//문자<--> 파일 :항상 charset을 정해줘야 한다
            log.info("body={}", body);

            //파일에 저장하기
            if(StringUtils.hasText(part.getSubmittedFileName())){
                String fullPath = fileDir + part.getSubmittedFileName(); //디렉토리 + 파일명
                log.info("파일 저장 fullPath={}", fullPath);
                part.write(fullPath); //Part를 통해 전송된 데이터를 저장할 수 있다.
            }
        }

        return "upload-form";
    }
}
