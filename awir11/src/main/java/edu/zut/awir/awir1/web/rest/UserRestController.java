package edu.zut.awir.awir1.web.rest;

import edu.zut.awir.awir1.service.UserService;
import edu.zut.awir.awir1.web.dto.UserDto;
import edu.zut.awir.awir1.web.mapper.UserMapper;
import edu.zut.awir.awir1.messaging.UserJmsProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService service;
    private final UserMapper mapper;
    private final UserJmsProducer producer;

    @GetMapping
    public List<UserDto> list() {
        return service.findAll().stream().map(mapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        var u = service.findById(id);
        if (u == null) throw new NotFoundException("User", id);
        return mapper.toDto(u);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto payload) throws AccessDeniedException {
        if (!isAdmin()) {
            throw new AccessDeniedException("You do not have permission to create a user");
        }
        var entity = mapper.toEntity(payload);
        entity.setId(null);
        var saved = service.save(entity);
        producer.sendUserCreated(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto payload) throws AccessDeniedException {
        if (!isAdmin()) {
            throw new AccessDeniedException("You do not have permission to update a user");
        }
        var existing = service.findById(id);
        if (existing == null) throw new NotFoundException("User", id);
        var entity = mapper.toEntity(payload);
        entity.setId(id);
        var saved = service.save(entity);
        producer.sendUserUpdated(saved);
        return mapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws AccessDeniedException {
        if (!isAdmin()) {
            throw new AccessDeniedException("You do not have permission to delete a user");
        }
        var existing = service.findById(id);
        if (existing == null) throw new NotFoundException("User", id);
        service.delete(id);
    }

    private boolean isAdmin() {
        return org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String what, Object id) {
            super(what + " " + id + " not found");
        }
    }
}