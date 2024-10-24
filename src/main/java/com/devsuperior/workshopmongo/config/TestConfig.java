package com.devsuperior.workshopmongo.config;

import com.devsuperior.workshopmongo.models.embedded.Author;
import com.devsuperior.workshopmongo.models.embedded.Comment;
import com.devsuperior.workshopmongo.models.entities.Post;
import com.devsuperior.workshopmongo.models.entities.User;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostConstruct
    public void init() {

        //caso exista um Usuário, será deletado
        //o banco sempre será iniciado vazio
        userRepository.deleteAll();
        postRepository.deleteAll();


        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        //veja no fim da instanciação, agregamos um author para o post
        //usando os Users acima criados
        Post post1 = new Post(null, Instant.parse("2021-02-13T11:15:01Z"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new Author(maria));
        Post post2 = new Post(null, Instant.parse("2021-02-14T10:05:49Z"), "Bom dia", "Acordei feliz hoje!", new Author(maria));

        //mesma coisa nos comentarios, passamos um author, alocando um User instanciado
        Comment c1 = new Comment("Boa viagem mano!", Instant.parse("2021-02-13T14:30:01Z"), new Author(alex));
        Comment c2 = new Comment("Aproveite", Instant.parse("2021-02-13T15:38:05Z"), new Author(bob));
        Comment c3 = new Comment("Tenha um ótimo dia!", Instant.parse("2021-02-14T12:34:26Z"), new Author(alex));

        //entramos no Post, puxamos a lista de comentários e adicionamos como Array
        //os comments criados acima (agregando os comments ao post)
        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().addAll(Arrays.asList(c3));

        //depois de agregar tudo ao post, salvamos no banco
        postRepository.saveAll(Arrays.asList(post1, post2));

        //acessamos os posts da maria, e colocamos o post1 e 2 associados a ela
        //afinal, um User tem uma lista de Posts, correto?
        maria.getPosts().addAll(Arrays.asList(post1, post2));
        //salva tudo do usuário
        userRepository.save(maria);
    }
}
