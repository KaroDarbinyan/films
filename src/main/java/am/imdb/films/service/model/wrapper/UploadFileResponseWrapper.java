package am.imdb.films.service.model.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UploadFileResponseWrapper {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

}
