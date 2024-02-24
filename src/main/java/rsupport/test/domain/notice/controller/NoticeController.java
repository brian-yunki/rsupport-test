package rsupport.test.domain.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Notice;
import rsupport.test.domain.notice.service.NoticeService;

import java.util.List;
import java.util.Map;

@Tag(name = "공지사항")
@RestController
@RequestMapping(value = "/notices", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @Operation(
            summary     = "공지사항 조회",
            parameters  = {
                    @Parameter(name = "page",   in = ParameterIn.QUERY, required = false,  description = "페이지 번호",         example = "0"),
                    @Parameter(name = "size",   in = ParameterIn.QUERY, required = false,  description = "페이지 목록 사이즈",   example = "10"),
                    @Parameter(name = "sort",   in = ParameterIn.QUERY, required = false,  description = "정렬 조건",           example = "createDate,desc")
            }
    )
    @GetMapping("")
    public Page<Notice> search(@Parameter(hidden = true) @Nullable @RequestParam Map<String, String> params,
                                 @PageableDefault(page = 0, size = 10)
                                 @SortDefault.SortDefaults({@SortDefault(sort = "createDate", direction = Sort.Direction.DESC)}) Pageable pageable) {
        return noticeService.searchBy(params, pageable);
    }

    @Operation(summary = "공지글 가져오기")
    @GetMapping("/{id:[0-9]*}")
    public Notice get(@PathVariable("id") Long id) {
        return noticeService.selectById(id);
    }

    @Operation(summary = "공지파일 가져오기")
    @GetMapping("/{id:[0-9]*}/files")
    public List<Attachment> getNoticeFiles(@PathVariable("id") Long id) {
        return noticeService.selectNoticeFiles(id);
    }

    @Operation(summary = "공지사항 저장", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @PostMapping("")
    public void save(@Valid @RequestBody Notice notice) {
        noticeService.save(notice);
    }

    public void update(@Valid @RequestBody Notice notice) {

    }

    @Operation(summary = "공지사항 삭제", security = {@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)})
    @DeleteMapping("/{id:[0-9]*}")
    public void delete(@PathVariable Long id) {
         noticeService.deleteById(id);
    }


}
