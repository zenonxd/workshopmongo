package com.devsuperior.workshopmongo.models.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String text;
    private Instant moment;

    private Author author;


}
