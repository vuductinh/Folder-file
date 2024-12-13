package vn.com.gsoft.tcom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.tcom.entity.File;
import vn.com.gsoft.tcom.entity.Folder;
import vn.com.gsoft.tcom.repository.FileRepository;
import vn.com.gsoft.tcom.repository.FolderRepository;
import vn.com.gsoft.tcom.service.FolderService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Long> folderMap = new HashMap<>();
        for (MultipartFile file : files) {
            String fullPath = file.getOriginalFilename();
            if (fullPath == null || !fullPath.contains("/")) {
                throw new IllegalArgumentException("Đường dẫn không hợp lệ " + fullPath);
            }
            String[] parts = fullPath.split("/");
            String folderPath = String.join("/", Arrays.copyOf(parts, parts.length - 1)); // Đường dẫn thư mục
            String fileName = parts[parts.length - 1]; // Tên tệp
            Long parentId = folderMap.get(folderPath);
            if (parentId == null) {
                parentId = createFolderHierarchy(folderPath, folderMap);  // Tạo thư mục nếu chưa có
            }
            saveFileToDatabase(file, parentId, fileName);
        }
    }

    private Long createFolderHierarchy(String folderPath, Map<String, Long> folderMap) {
        String[] pathParts = folderPath.split("/");
        Long parentId = null;
        StringBuilder currentPath = new StringBuilder();
        for (String part : pathParts) {
            currentPath.append(part).append("/");
            String currentFolder = currentPath.toString().replaceAll("/$", ""); // Loại bỏ dấu gạch chéo ở cuối
            if (!folderMap.containsKey(currentFolder)) {
                Long finalParentId = parentId;
                Folder folder = folderRepository.findByNameAndIdFolder(part, finalParentId)
                        .orElseGet(() -> folderRepository.save(
                                new Folder(null, part, finalParentId, null, null)
                        ));
                folderMap.put(currentFolder, folder.getId());
                parentId = folder.getId();
            } else {
                parentId = folderMap.get(currentFolder);
            }
        }
        return parentId;
    }

    private void saveFileToDatabase(MultipartFile file, Long folderId, String fileName) throws Exception {
        String fileStoragePath = "uploaded_files/" + fileName;
        Path path = Paths.get(fileStoragePath);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        File fileEntity = new File(null, fileName, fileStoragePath, folderId, null);
        fileRepository.save(fileEntity);
    }

}
