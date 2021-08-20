package com.smartech.i2019.adaptiveinterviews.service.impl;

import com.smartech.i2019.adaptiveinterviews.model.UploadFile;
import com.smartech.i2019.adaptiveinterviews.repository.UploadFileRepository;
import com.smartech.i2019.adaptiveinterviews.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {
    private final UploadFileRepository uploadFileRepository;

    @Override
    public void add(UploadFile file) {
        uploadFileRepository.saveAndFlush(file);
    }

    @Override
    public void delete(long id) throws FileNotFoundException {
        log.info("Файл ({}) у сотрудника ({} {}) удален",
                findById(id).getFileName(),
                findById(id).getEmployee().getFirstName(),
                findById(id).getEmployee().getLastName());
        uploadFileRepository.deleteById(id);
    }

    @Override
    public UploadFile findById(long id) throws FileNotFoundException {
        UploadFile file = uploadFileRepository.findById(id).orElse(null);
        if (file == null) {
            log.warn("Файла с указанным идентификатором нет в базе данных: ({})", id);
            throw new FileNotFoundException("Файла с указанным идентификатором нет в базе данных");
        }
        return file;
    }

    @Override
    public List<UploadFile> findAll() {
        return uploadFileRepository.findAll();
    }

}
