package com.smartech.i2019.adaptiveinterviews.service;

import com.smartech.i2019.adaptiveinterviews.api.UploadFileService;
import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import com.smartech.i2019.adaptiveinterviews.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final UploadFileRepository uploadFileRepository;
    private static final Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Override
    public void add(UploadFile file) {
        uploadFileRepository.saveAndFlush(file);
    }

    @Override
    public void delete(long id) {
        uploadFileRepository.deleteById(id);
    }

    @Override
    public UploadFile findById(long id) throws FileNotFoundException {
        UploadFile file = uploadFileRepository.findById(id).orElse(null);
        if (file == null) {
            logger.warn("Файла с указанным идентификатором нет в базе данных: ({})", id);
            throw new FileNotFoundException("Файла с указанным идентификатором нет в базе данных");
        }
        return file;
    }

    @Override
    public List<UploadFile> findAll() {
        return uploadFileRepository.findAll();
    }

}
