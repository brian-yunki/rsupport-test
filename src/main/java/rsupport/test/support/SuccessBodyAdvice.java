package rsupport.test.support;

import io.micrometer.common.lang.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"rsupport.test.domain.notice.controller"})
public class SuccessBodyAdvice implements ResponseBodyAdvice<Object>  {
    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response)
    {
        if (body instanceof Page<?> page) {
//            return body;
            return Response.builder()
                    .content(page.getContent())
                    .pageable(Pageable.builder()
                            .pageNumber(page.getPageable().getPageNumber())
                            .pageSize(page.getPageable().getPageSize())
                            .totalPages(page.getTotalPages())
                            .totalElements(page.getTotalElements())
                            .first(page.isFirst())
                            .last(page.isLast())
                            .build())
                    // 불필요한 것 제거
//                    .pageable(page.getPageable())
//                    .numberOfElements(page.getNumberOfElements())
//                    .totalPages(page.getTotalPages())
//                    .size(page.getSize())
//                    .first(page.isFirst())
//                    .last(page.isLast())
//                    .totalElements(page.getTotalElements())
                    .message("success")
                    .build();
        } else if (body instanceof InputStreamResource) {
            return body;
        }

        return Response.builder().message("success").content(body).build();
    }
}
