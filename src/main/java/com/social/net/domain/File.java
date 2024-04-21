package com.social.net.domain;

import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class File extends AbstractEntity {
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
