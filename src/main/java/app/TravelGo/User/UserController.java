package app.TravelGo.User;

import app.TravelGo.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

   @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder builder) {
        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phone_number(request.getPhone_number())
                .privileges(request.getPrivileges())
                .build();
        user = userService.createUser(user);
        return ResponseEntity.created(builder.pathSegment("api", "users", "{id}")
                .buildAndExpand(user.getId()).toUri()).build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetUserResponse> getUser(@PathVariable(name = "id") Long id) {
        Optional<User> response = userService.getUser(id);
        if (response.isPresent()) {
            User user = response.get();
            return ResponseEntity.ok(GetUserResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .email(user.getEmail())
                    .phone_number(user.getPhone_number())
                    .privileges(user.getPrivileges())
                    .build());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            userService.deleteUser(user.get().getId());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //TODO
    //login()

    //moze tez getUsers()??
}
