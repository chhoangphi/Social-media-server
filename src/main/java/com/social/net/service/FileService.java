package com.social.net.service;

import com.social.net.payload.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService extends ICrudService<FileResponse, UUID> {
    Optional<FileResponse> uploadFile(MultipartFile multipartFile) throws IOException;

    List<FileResponse> uploadMultiFile(List<MultipartFile> multipartFiles) throws IOException;

}
