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
public class FolderController {

    @Autowired
    private FolderService service;

    @GetMapping("/getFolder")
    public ResponseEntity<List<Folder>> getFolderTree() {
        try {
            List<Folder> folders = service.folderTree();
            return new ResponseEntity<>(folders, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Lỗi khi lấy cây thư mục", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Folder> getFolderDetails(@PathVariable Long id) {
        try {
            Folder folder = service.details(id);
            return folder != null ?
                    new ResponseEntity<>(folder, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Lỗi khi lấy chi tiết thư mục với id " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createFolder")
    public ResponseEntity<String> createFolder(@RequestParam(required = false) Long idFolder, @RequestParam String name) {
        try {
            Folder folder = service.create(idFolder, name);
            return new ResponseEntity<>("Thư mục đã được tạo thành công!", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Lỗi khi tạo thư mục", e);
            return new ResponseEntity<>("Đã xảy ra lỗi khi tạo thư mục", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateFolder")
    public ResponseEntity<String> updateFolder(@RequestParam Long idFolder, @RequestParam String newName) {
        try {
            Folder updatedFolder = service.update(idFolder, newName);
            if (updatedFolder != null) {
                return new ResponseEntity<>("Thư mục đã được cập nhật thành công!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không tìm thấy thư mục với ID này!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật thư mục", e);
            return new ResponseEntity<>("Đã xảy ra lỗi khi cập nhật thư mục", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteFolder(@PathVariable Long idFolder) {
        try {
            boolean isDeleted = service.delete(idFolder);
            if (isDeleted) {
                return new ResponseEntity<>("Thư mục đã được xóa thành công!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không tìm thấy thư mục để xóa", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Lỗi khi xóa thư mục với id " + idFolder, e);
            return new ResponseEntity<>("Đã xảy ra lỗi khi xóa thư mục", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
