package com.devsuperior.workshopmongo.service;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.models.entities.Post;
import com.devsuperior.workshopmongo.models.entities.User;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import com.devsuperior.workshopmongo.service.exceptions.DatabaseException;
import com.devsuperior.workshopmongo.service.exceptions.EntityNotFoundException;
import com.devsuperior.workshopmongo.service.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserDTO::new);
    }

    public UserDTO findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Recurso não encontrado."));

        return new UserDTO(user);
    }

    private User getEntityById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFound("Objeto não encontrado."));
    }

    public UserDTO insert(UserDTO userDTO) {
        User user = new User();
        copyDtoToEntity(userDTO, user);
        //agora usar insert para o mongoDB, não save
        user = userRepository.insert(user);
        return new UserDTO(user);
    }

    public UserDTO update(String id, UserDTO dto) {
        try {
            User entity = getEntityById(id);
            copyDtoToEntity(dto, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Recurso não encontrado");
        }
    }

    public void delete(String id) {
        getEntityById(id);
        userRepository.deleteById(id);
    }

    public List<PostDTO> getUsersPosts(String id) {
        User user = getEntityById(id);
        return user.getPosts().stream()
                .map(PostDTO::new).toList();
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());

    }


}
