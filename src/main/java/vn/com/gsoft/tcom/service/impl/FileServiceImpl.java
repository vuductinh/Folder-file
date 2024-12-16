package vn.com.gsoft.tcom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.gsoft.tcom.entity.File;
import vn.com.gsoft.tcom.model.FileResponse;
import vn.com.gsoft.tcom.repository.FileRepository;
import vn.com.gsoft.tcom.service.FileService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository hdrRepo;

    @Override
    public String uploadFiles(List<MultipartFile> files, Long folderId) throws IOException {
        StringBuilder response = new StringBuilder();

        for (MultipartFile file : files) {
            File newFile = File.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(file.getBytes())
                    .idFolder(folderId)
                    .created(new Date())
                    .build();
            File savedFile = hdrRepo.save(newFile);

            if (savedFile.getId() != null) {
                response.append("Tệp ").append(file.getOriginalFilename()).append(" đã được lưu thành công.\n");
            } else {
                response.append("Lỗi khi lưu tệp ").append(file.getOriginalFilename()).append(".\n");
            }
        }
        return response.toString();
    }

    @Override
    public FileResponse downloadFile(Long idFile) throws Exception {
        File file = hdrRepo.findById(idFile)
                .orElseThrow(() -> new Exception("Không tìm thấy tệp với id: " + idFile));
        String fileName = file.getName();
        String fileType = file.getType();
        byte[] fileData = file.getImageData();
        if (fileData == null || fileData.length == 0) {
            throw new Exception("Dữ liệu file trống!");
        }
        return new FileResponse(fileName, fileType, fileData);
    }
}
