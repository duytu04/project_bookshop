package t2406e_group1.bookshopspringboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class ControllerUser {

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public ResponseEntity<List<EntityUser>> getAllUsers() {
        List<EntityUser> users = serviceUser.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityUser> getUserById(@PathVariable int id) {
        Optional<EntityUser> user = serviceUser.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<EntityUser>> getUsersByRole(@PathVariable String role) {
        List<EntityUser> users = serviceUser.findByRolesContaining(role);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<EntityUser> createUser(@RequestBody EntityUser user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null ||
                user.getPhoneNumber() == null || user.getAddress() == null || user.getBirthDay() == null ||
                user.getRoles() == null || user.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            EntityUser createdUser = serviceUser.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityUser> updateUser(@PathVariable int id, @RequestBody EntityUser user) {
        EntityUser updatedUser = serviceUser.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean deleted = serviceUser.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}