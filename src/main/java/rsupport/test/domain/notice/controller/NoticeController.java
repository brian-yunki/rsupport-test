package rsupport.test.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rsupport.test.domain.notice.model.Notice;
import rsupport.test.domain.notice.service.NoticeService;

import java.util.List;

@Tag(name = "공지사항")
@RestController
@RequestMapping(value = "/notices", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public List<Notice> getAll() {
        return noticeService.selectAll();
    }

    @Operation(summary = "공지글 가져오기")
    @GetMapping("/{id:[0-9]*}")
    public Notice getById(@PathVariable("id") Long id) {
        return noticeService.selectById(id);
    }

    @Operation(summary = "공지글 저장하기")
    @PostMapping("")
    public void save(@Valid @RequestBody Notice notice) {
        noticeService.save(notice);
    }

    public void update(@Valid @RequestBody Notice notice) {

    }

    @Operation(summary = "공지글 삭제하기")
    @DeleteMapping("/{id:[0-9]*}")
    public void delteById(@PathVariable Long id) {
         noticeService.deleteById(id);
    }

}
