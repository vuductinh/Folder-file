package vn.com.gsoft.tcom.service;
import vn.com.gsoft.tcom.entity.Folder;

import java.util.List;

public interface FolderService {
    List<Folder> getFolderTree () throws Exception;

    Folder createFolder(Long idFolder, String folderName) throws Exception;

    Folder updateFolderName(Long folderId, String newFolderName) throws Exception;
}
