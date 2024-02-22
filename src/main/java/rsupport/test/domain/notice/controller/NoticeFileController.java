package rsupport.test.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.domain.notice.entity.NoticeFileEntity;
import rsupport.test.domain.notice.service.NoticeFIleService;

import java.util.List;

@RestController
@RequestMapping(value = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeFileController {

    private final NoticeFIleService noticeFIleService;

    @GetMapping("")
    public List<NoticeFileEntity> getAll() {
        return noticeFIleService.findAll();
    }


 }
