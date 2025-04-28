package t2406e_group1.bookshopspringboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceUser {

    @Autowired
    private JpaUser jpaUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<EntityUser> findByEmail(String email) {
        return jpaUser.findByEmail(email);
    }

    public List<EntityUser> findByRolesContaining(String role) {
        return jpaUser.findByRolesContaining(role);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public EntityUser createUser(EntityUser user) {
        if (jpaUser.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return jpaUser.save(user);
    }

    public List<EntityUser> getAllUsers() {
        return jpaUser.findAll();
    }

    public Optional<EntityUser> getUserById(int id) {
        return jpaUser.findById(id);
    }

    public EntityUser updateUser(int id, EntityUser updatedUser) {
        Optional<EntityUser> existingUser = jpaUser.findById(id);
        if (existingUser.isPresent()) {
            EntityUser user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setAddress(updatedUser.getAddress());
            user.setBirthDay(updatedUser.getBirthDay());
            user.setGender(updatedUser.isGender());
            user.setAvata(updatedUser.getAvata());
            user.setRoles(updatedUser.getRoles());
            return jpaUser.save(user);
        }
        return null;
    }

    public boolean deleteUser(int id) {
        if (jpaUser.existsById(id)) {
            jpaUser.deleteById(id);
            return true;
        }
        return false;
    }
}