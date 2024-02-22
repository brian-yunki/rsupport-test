package rsupport.test.domain.notice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsupport.test.domain.notice.service.NoticeService;
import rsupport.test.domain.notice.entity.NoticeEntity;

import java.util.List;

@RestController
@RequestMapping(value = "/notices", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public List<NoticeEntity> getAll() {
        return noticeService.getAll();
    }

    @GetMapping("/{id:[0-9]*}")
    public List<NoticeEntity> getById(@PathVariable("id") Long id) {
        return noticeService.getById(id);
    }

    @DeleteMapping("/{id:[0-9]*}")
    public void delteById(@PathVariable Long id) {
        noticeService.deleteById(id);
    }


}
