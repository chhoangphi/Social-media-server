package com.social.net.payload.response;

import com.social.net.common.utils.AppResponse;
import com.social.net.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse extends AppResponse<FileResponse, File> {
    private UUID id;
    private String name;
    private String url;
    private Long size;
    private String mimeType;
    private Timestamp createdAt;

    public static FileResponse emptyInstance() {
        return new FileResponse();
    };

    @Override
    public FileResponse fromEntity(File entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.url = entity.getUrl();
        this.size = entity.getSize();
        this.mimeType = entity.getMimeType();
        this.createdAt = entity.getCreatedAt();
        return this;
    }

    @Override
    public File toUpdatedEntity(File entity) {
        entity.setName(name);
        entity.setUrl(url);
        entity.setSize(size);
        entity.setMimeType(mimeType);
        entity.setCreatedAt(createdAt);
        return entity;
    }

    @Override
    public File toEntity() {
        return File.builder()
                .id(id)
                .name(name)
                .url(url)
                .size(size)
                .mimeType(mimeType)
                .build();
    }
}
