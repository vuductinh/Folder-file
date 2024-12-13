package vn.com.gsoft.tcom.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.tcom.entity.Folder;
import vn.com.gsoft.tcom.service.FolderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/folders")
public class FolderController {
    @Autowired
    private FolderService service;

    @GetMapping("/tree")
    public List<Folder> getFolderTree() throws Exception {
        return service.getFolderTree();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadFolder(@RequestParam("files") List<MultipartFile> files) {
        try {
            service.uploadFolder(files);
            return ResponseEntity.ok("Folder uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload folder: " + e.getMessage());
        }
    }
}