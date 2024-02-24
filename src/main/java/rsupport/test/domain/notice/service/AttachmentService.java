package rsupport.test.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rsupport.test.config.StorageConfig;
import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Upload;
import rsupport.test.domain.notice.repository.AttachmentRepository;
import rsupport.test.domain.support.Converter;
import rsupport.test.exception.CustomException;
import rsupport.test.exception.ErrorCode;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static rsupport.test.support.Utils.calculatedPoolSize;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(StorageConfig.class)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    // XNIX system default (/var/tmp : automatically deleted after one month)
    private final StorageConfig storageConfig;



    // get attachment
    public Attachment selectById(Long id) {
        AttachmentEntity attachmentEntity = attachmentRepository.selectById(id).stream()
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT));

        return Converter.toAttachmentModel(List.of(attachmentEntity)).getFirst();
    }

    // store multipart files ( default /var/tmp/rsupport)
    public List<Upload> storeFiles(List<MultipartFile> files) {
        List<String> extensions = storageConfig.getAllowFileExtension();
        List<String> mimeTypes = storageConfig.getAllowMimeType();
        // 확장자
        if (!checkAllowFileExtension.apply(files, extensions)) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "지원하는 확장자는 " + String.join(",", extensions) + " 입니다");
        }
        // 변조
        if (!checkAllowFileMimeType.apply(files, mimeTypes)) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "지원하는 파일의 컨텐츠 형식이 맞지 않습니다");
        }


        // !! ServletUriComponentsBuilder.fromCurrentContextPath() - ForkJoinPool 스트림에서 사용하지 말 것.
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").toUriString();
        try (ForkJoinPool pool = new ForkJoinPool(calculatedPoolSize.apply(2))) {
            return pool.submit(() -> files.stream()
                            .parallel()
                            .map(file -> {
                                String filename = file.getOriginalFilename();
                                String extension = StringUtils.getFilenameExtension(filename);
                                try {
                                    assert filename != null;
                                    String storedname = UUID.randomUUID() + "." + extension;
                                    file.transferTo(new File(Paths.get(storageConfig.getPath(), storedname).toString())); // Check system 'ulimit' setting.
                                    return Upload.builder()
                                            .name(filename).path(path + storedname).size(file.getSize())
                                            .build();
                                } catch (Exception e) {
                                    throw new CustomException(ErrorCode.SERVER_ERROR);
                                }
                            }).toList())
                            .get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    // check allowed file extension
    public static final BiFunction<List<MultipartFile>, List<String>, Boolean> checkAllowFileExtension = (files, allows) -> files.stream()
            .map(file -> check(allows, StringUtils.getFilenameExtension(file.getOriginalFilename())))
            .allMatch(Predicate.isEqual(true));


    // check allowed file content-type (mimetype)
    public static final BiFunction<List<MultipartFile>, List<String>, Boolean> checkAllowFileMimeType = (files, allows) -> files.stream()
            .map(file -> check(allows, file.getContentType()))
            .allMatch(Predicate.isEqual(true));


    private static Boolean check(List<String> allows, String key) {
        return allows.stream().anyMatch(Predicate.isEqual(key));
    }
}


