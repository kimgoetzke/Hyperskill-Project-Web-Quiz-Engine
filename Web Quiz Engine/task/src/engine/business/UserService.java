package engine.business;

import engine.business.exceptions.UserAlreadyExistsException;
import engine.business.exceptions.UserNotFoundException;
import engine.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean add(User user) {
        if (userRepository.existsById(user.getEmail())) {
            System.out.println("User exists: " + userRepository.findById(user.getEmail()) + ".");
            throw new UserAlreadyExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("Added: " + userRepository.findById(user.getEmail())
                + " - total number of users is now " + userRepository.count() + ".");
        return true;
    }

    public User get(String email) {
        return userRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
    }
}
