package com.social.net.domain;

import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.common.utils.App;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class File extends AbstractEntity implements App {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private Long size;

    @Column
    private String mimeType;
}
