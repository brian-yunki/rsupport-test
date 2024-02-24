package rsupport.test.domain.notice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Upload;
import rsupport.test.domain.notice.service.AttachmentService;

import java.util.List;

@Tag(name = "첨부파일")
@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;


    @GetMapping("/{id:[0-9]*}")
    public Attachment get(@PathVariable("id") Long id) {
        return attachmentService.selectById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Upload> upload(@RequestPart("files") List<MultipartFile> files) {
        return attachmentService.storeFiles(files);
    }



 }
