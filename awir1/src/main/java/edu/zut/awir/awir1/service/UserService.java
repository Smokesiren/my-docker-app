package edu.zut.awir.awir1.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import edu.zut.awir.awir1.model.User;
import edu.zut.awir.awir1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final MailService mailService;
    @Transactional
    public User save(User user)
    {
        boolean isNew = (user.getId() == null);
        User saved = repository.save(user);
        if(isNew)
            mailService.sendUserCreated(saved);
        else
            mailService.sendUserUpdated(saved);

        return saved;
    }
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}