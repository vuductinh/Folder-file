package vn.com.gsoft.tcom.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.tcom.entity.File;
import vn.com.gsoft.tcom.entity.Folder;
import vn.com.gsoft.tcom.repository.FileRepository;
import vn.com.gsoft.tcom.repository.FolderRepository;
import vn.com.gsoft.tcom.service.FolderService;
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
    public Folder createFolder(Long idFolder, String folderName) throws Exception {
        Folder folder = new Folder();
        if (idFolder != null) {
            Folder parentFolder = folderRepository.findById(idFolder)
                    .orElseThrow(() -> new Exception("Không tìm thấy thư mục"));
            List<Folder> parentFolders = new ArrayList<>();
            parentFolders.add(parentFolder);
            folder.setChildren(parentFolders);
        }
        folder.setName(folderName);
        folder.setCreated(new Date());
        return folderRepository.save(folder);
    }

    @Override
    public Folder updateFolderName(Long folderId, String newFolderName) throws Exception {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new Exception("Không tìm thấy thư mục"));
        folder.setName(newFolderName);
        return folderRepository.save(folder);
    }
}
