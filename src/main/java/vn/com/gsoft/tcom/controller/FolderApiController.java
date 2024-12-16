package vn.com.gsoft.tcom.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.tcom.entity.Folder;
import vn.com.gsoft.tcom.service.FolderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/folders")
public class FolderApiController {

    @Autowired
    private FolderService service;

    @GetMapping("/getFolder")
    public List<Folder> getFolderTree() throws Exception {
        return service.getFolderTree();
    }

    @PostMapping("/createFolder")
    public ResponseEntity<Folder> createFolder(@RequestParam(required = false) Long idFolder, @RequestParam String folderName) {
        try {
            Folder newFolder = service.createFolder(idFolder, folderName);
            return new ResponseEntity<>(newFolder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateFolderName")
    public ResponseEntity<Folder> updateFolderName(@RequestParam(required = false) Long idFolder, @RequestParam String newFolderName) {
        try {
            Folder newFolder = service.updateFolderName(idFolder, newFolderName);
            return new ResponseEntity<>(newFolder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}