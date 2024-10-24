package com.devsuperior.workshopmongo.dto;

import com.devsuperior.workshopmongo.models.embedded.Author;
import com.devsuperior.workshopmongo.models.embedded.Comment;
import com.devsuperior.workshopmongo.models.entities.Post;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private String id;
    private Instant moment;
    private String title;
    private String body;

    private Author author;

    @Setter(AccessLevel.NONE)
    private List<Comment> comments = new ArrayList<>();

    public PostDTO(String id, Instant moment, String title, String body, Author author) {
        this.id = id;
        this.moment = moment;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.moment = post.getMoment();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.author = post.getAuthor();

        this.comments.addAll(post.getComments());
    }
}
