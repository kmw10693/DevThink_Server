package com.devthink.devthink_server.domain;

import com.devthink.devthink_server.dto.CommentResponseDto;
import com.devthink.devthink_server.dto.PostListData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Category category;

    private boolean image;

    private String title;

    private String content;

    @Builder.Default
    private String imageUrl = "";

    @Builder.Default
    private Boolean deleted = false;

    @Builder.Default
    private Integer heart = 0;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostListData toPostListData() {
        return PostListData.builder()
                .userId(user.getId())
                .categoryId(category.getId())
                .content(content)
                .title(title)
                .deleted(deleted)
                .createAt(getCreateAt())
                .updateAt(getUpdateAt())
                .heart(heart)
                .id(id)
                .imageUrl(imageUrl)
                .Image(isImage())
                .nickname(user.getNickname())
                .build();
    }
}
