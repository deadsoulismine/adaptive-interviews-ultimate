package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UploadFileService;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import com.smartech.i2019.adaptiveinterviews.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final UploadFileRepository uploadFileRepository;

    @Override
    public void add(UploadFile file) {
        uploadFileRepository.saveAndFlush(file);
    }

    @Override
    public void delete(long id) {
        uploadFileRepository.deleteById(id);
    }

    @Override
    public UploadFile findById(long id) {
        return uploadFileRepository.findById(id).orElse(null);
    }

    @Override
    public List<UploadFile> findAll() {
        return uploadFileRepository.findAll();
    }

}
