package rsupport.test.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.service.AttachmentService;

import java.util.List;

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
