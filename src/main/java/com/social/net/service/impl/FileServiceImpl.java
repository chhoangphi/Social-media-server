package com.social.net.service.impl;

import com.social.net.common.exception.FileStorageException;
import com.social.net.common.utils.FileUtil;
import com.social.net.domain.File;
import com.social.net.payload.response.FileResponse;
import com.social.net.repository.FileRepository;
import com.social.net.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Override
    public Optional<FileResponse> uploadFile(MultipartFile multipartFile) throws FileStorageException, IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        String savePath = FileUtil.saveFile(multipartFile);

        File fileEntity = File.builder()
                .name(fileName)
                .size(multipartFile.getSize())
                .url(savePath)
                .mimeType(multipartFile.getContentType())
                .build();
        fileEntity = fileRepository.save(fileEntity);
        return Optional.of(FileResponse.emptyInstance().fromEntity(fileEntity));
    }

    @Override
    public List<FileResponse> uploadMultiFile(List<MultipartFile> multipartFiles)
            throws FileStorageException, IOException {
        List<String> savePaths = FileUtil.saveMultipleFile(multipartFiles);
        List<FileResponse> results = new ArrayList<>();
        for (int i = 0; i < multipartFiles.size(); i++) {
            MultipartFile multipartFile = multipartFiles.get(i);

            File fileEntity = File.builder()
                    .name(StringUtils.cleanPath(multipartFile.getOriginalFilename()))
                    .size(multipartFile.getSize())
                    .url(savePaths.get(i))
                    .mimeType(multipartFile.getContentType())
                    .build();
            fileEntity = fileRepository.save(fileEntity);
            results.add(FileResponse.emptyInstance().fromEntity(fileEntity));
        }
        return results;
    }

    @Override
    public List<FileResponse> getAll() {
        return fileRepository.findAll().stream().map((entity) -> FileResponse.emptyInstance().fromEntity(entity)).collect(Collectors.toList());
    }

    @Override
    public Optional<FileResponse> getDataById(UUID id) throws NoSuchElementException {
        File fileEntity = fileRepository.findById(id).orElseThrow();
        return Optional.of(FileResponse.emptyInstance().fromEntity(fileEntity));
    }

    @Override
    public Optional<FileResponse> insertData(FileResponse data) throws IllegalArgumentException {
        File fileEntity = fileRepository.save(data.toEntity());
        return Optional.of(FileResponse.emptyInstance().fromEntity(fileEntity));
    }

    @Override
    public Optional<FileResponse> updateData(UUID id, FileResponse data) {
        throw new UnsupportedOperationException("Unimplemented method 'updateData'");
    }

    @Override
    public void deleteData(UUID id) throws IllegalArgumentException, NoSuchElementException {
        File entity = fileRepository.findById(id).orElseThrow();
        try {
            FileUtil.deleteFile(entity.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileRepository.deleteById(id);
    }

}
