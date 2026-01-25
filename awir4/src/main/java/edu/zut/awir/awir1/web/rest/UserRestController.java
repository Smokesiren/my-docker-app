package edu.zut.awir.awir1.web.rest;

import edu.zut.awir.awir1.model.User;
import edu.zut.awir.awir1.web.mapper.UserMapper;
import edu.zut.awir.awir1.web.dto.UserDto;
import edu.zut.awir.awir1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService service;
    private final UserMapper mapper;

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
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto payload) {
        var entity = mapper.toEntity(payload);
        entity.setId(null);
        var saved = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto payload) {
        var existing = service.findById(id);
        if (existing == null) throw new NotFoundException("User", id);
        var entity = mapper.toEntity(payload);
        entity.setId(id);
        return mapper.toDto(service.save(entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        var existing = service.findById(id);
        if (existing == null) throw new NotFoundException("User", id);
        service.delete(id);
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String what, Object id) {
            super(what + " " + id + " not found");
        }
    }
}
