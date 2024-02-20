package rsupport.test.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.storage.entity.FileEntity;
import rsupport.test.storage.service.FileService;

import java.util.List;

@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("")
    public List<FileEntity> getAll() {
        return fileService.findAll();
    }


 }
