package rsupport.test.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.notice.entity.Notice;
import rsupport.test.notice.service.NoticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("/notice")
    public List<Notice> getAll() {
        return noticeService.findAll();
    }
}
