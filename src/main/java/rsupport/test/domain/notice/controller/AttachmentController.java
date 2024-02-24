package rsupport.test.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Download;
import rsupport.test.domain.notice.model.Upload;
import rsupport.test.domain.notice.service.AttachmentService;

import java.util.List;

@Tag(name = "첨부파일")
@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;


    // get attachment
    @GetMapping(value = "/{id:[0-9]*}")
    public Attachment get(@PathVariable("id") Long id) {
        return attachmentService.selectById(id);
    }


    // upload
    @Operation(summary = "업로드", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Upload> upload(@RequestPart("files") List<MultipartFile> files) {
        return attachmentService.storeFiles(files);
    }


    // download
    @Operation(summary = "다운로드")
    @GetMapping(value = "/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
        Download download = attachmentService.loadFile(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(download.getContentType()))
                // TODO #1-3. replace content disposition filename attribute. to real filename.
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + download.getResource().getFilename() + "\"")
                .header(HttpHeaders.TRANSFER_ENCODING, "binary")
                .body(download.getResource());
    }
 }
