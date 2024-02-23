package rsupport.test.domain.notice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.service.AttachmentService;

import java.util.List;

@Tag(name = "첨부파일")
@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService AttachmentService;

    @GetMapping("")
    public List<AttachmentEntity> getAll() {
        return AttachmentService.findAll();
    }




 }
