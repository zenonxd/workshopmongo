package com.devsuperior.workshopmongo.models.entities;

import com.devsuperior.workshopmongo.models.embedded.Author;
import com.devsuperior.workshopmongo.models.embedded.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    private Instant moment;
    private String title;
    private String body;

    private Author author;

    @Setter(AccessLevel.NONE)
    private List<Comment> comments = new ArrayList<>();


    public Post(String id, Instant moment, String title, String body, Author author) {
        this.id = id;
        this.moment = moment;
        this.title = title;
        this.body = body;
        this.author = author;
    }


}
