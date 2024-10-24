package com.devsuperior.workshopmongo.models.embedded;

import com.devsuperior.workshopmongo.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private String id;
    private String name;


    public Author(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

}
