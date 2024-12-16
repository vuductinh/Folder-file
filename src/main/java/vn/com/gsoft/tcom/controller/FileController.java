package vn.com.gsoft.tcom.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.tcom.model.FileResponse;
import vn.com.gsoft.tcom.service.FileService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping("/uploadFiles")
    public ResponseEntity<String> uploadFiles(@RequestParam("files[]") List<MultipartFile> files, @RequestParam("folderId") Long folderId) {
        try {

            String response = service.uploadFiles(files, folderId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            log.error("Lỗi khi lưu tệp vào cơ sở dữ liệu", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi lưu tệp.");
        }
    }

    @GetMapping("/downloadFile/{idFile}")
    public ResponseEntity<FileResponse> downloadFile(@PathVariable Long idFile) {
        try {
            FileResponse fileResponse = service.downloadFile(idFile);
            if (fileResponse.getFileData() == null || fileResponse.getFileData().length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            String encodedFileName = URLEncoder.encode(fileResponse.getName(), StandardCharsets.UTF_8.toString());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(fileResponse);
        } catch (UnsupportedEncodingException e) {
            log.error("Lỗi khi mã hóa tên tệp", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            log.error("Lỗi khi tải tệp với id " + idFile, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
