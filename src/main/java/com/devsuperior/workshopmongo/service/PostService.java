package com.devsuperior.workshopmongo.service;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.models.entities.Post;
import com.devsuperior.workshopmongo.models.entities.User;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.service.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostDTO findById(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Post not found"));

        return new PostDTO(post);
    }

    private Post getEntityById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ResourceNotFound("Objeto não encontrado."));
    }

    public List<PostDTO> searchByTitle(String text) {
        List<Post> posts = postRepository.findByTitleContaingIgnoreCase(text);

        return posts.stream().map(PostDTO::new).toList();
    }

    public List<PostDTO> fullSearch(String text, String start, String end) {
        //esse epochmilli garante que se o usuário não passar o início,
        // ele pegue a data de 1970 (menor valor possível)
        Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));

        //Caso o usuário não passe nada, será o instant de agora.
        //ou seja: com certeza não tem nenhum post da hora atual
        Instant endMoment = convertMoment(end, Instant.now());

        List<Post> posts = postRepository.fullSearch(text, startMoment, endMoment);

        return posts.stream().map(PostDTO::new).toList();
    }

    private Instant convertMoment(String originalString, Instant alternative) {
        try {
            return Instant.parse(originalString);
        }
        catch (DateTimeParseException e) {
            return alternative;
        }
    }
}
