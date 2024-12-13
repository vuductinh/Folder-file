package vn.com.gsoft.tcom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.tcom.entity.File;
import vn.com.gsoft.tcom.entity.Folder;
import vn.com.gsoft.tcom.repository.FileRepository;
import vn.com.gsoft.tcom.repository.FolderRepository;
import vn.com.gsoft.tcom.service.FolderService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FileRepository fileRepository;

    public List<Folder> getFolderTree() throws Exception {
        List<Folder> rootFolders = folderRepository.findAllByIdFolderIsNull();
        for (Folder folder : rootFolders) {
            loadSubFoldersAndFiles(folder);
        }
        return rootFolders;
    }

    private void loadSubFoldersAndFiles(Folder folder) {
        if (folder != null) {
            List<Folder> subFolders = folderRepository.findAllByIdFolder(folder.getId());
            folder.setChildren(subFolders);
            List<File> files = fileRepository.findAllByIdFolder(folder.getId());
            folder.setFiles(files);
            for (Folder subFolder : subFolders) {
                loadSubFoldersAndFiles(subFolder);
            }
        }
    }

    @Override
    public void uploadFolder(List<MultipartFile> files) throws Exception {

    }

}
