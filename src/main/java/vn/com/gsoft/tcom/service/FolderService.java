package vn.com.gsoft.tcom.service;

import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.tcom.entity.Folder;

import java.util.List;

public interface FolderService {
    List<Folder> getFolderTree () throws Exception;

    void uploadFolder(List<MultipartFile> files) throws Exception;
}
