package rsupport.test.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsupport.test.domain.notice.model.Notice;
import rsupport.test.domain.notice.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping(value = "/notices", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public List<Notice> getAll() {
        return noticeService.selectAll();
    }

    @GetMapping("/{id:[0-9]*}")
    public Notice getById(@PathVariable("id") Long id) {
        return noticeService.selectById(id);
    }

    @PostMapping("")
    public void save(@RequestBody Notice notice) {
        noticeService.save(notice);
    }

    @DeleteMapping("/{id:[0-9]*}")
    public void delteById(@PathVariable Long id) {
        noticeService.deleteById(id);
    }

}
