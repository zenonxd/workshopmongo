package com.devsuperior.workshopmongo.controllers;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.models.entities.Post;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/{id}")
    public PostDTO findById(@PathVariable String id) {
        return postService.findById(id);
    }

    @GetMapping("/titlesearch")
    public ResponseEntity<List<PostDTO>> searchByTitle(@RequestParam("text") String text) {
        List<PostDTO> posts = postService.searchByTitle(text);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/fullSearch")
    public ResponseEntity<List<PostDTO>> fullSearch(
            @RequestParam("text") String text,
            @RequestParam("start") String start,
            @RequestParam("end") String end) {

        List<PostDTO> posts = postService.fullSearch(text, start, end);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }
}
