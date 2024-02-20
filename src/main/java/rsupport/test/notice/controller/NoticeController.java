package rsupport.test.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.notice.entity.NoticeEntity;
import rsupport.test.notice.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping(value = "/notice", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("")
    public List<NoticeEntity> getAll() {
        return noticeService.findAll();
    }
}
