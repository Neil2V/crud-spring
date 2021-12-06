package com.neil.app.controller;

import com.neil.app.entity.User;
import com.neil.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Long userId){
        Optional<User> oUser = userService.findById(userId);

        if(!oUser.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(oUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User userDetails,
                                    @PathVariable (value = "id") Long userId){
        Optional<User> user = userService.findById(userId);

        if(!user.isPresent())
            return ResponseEntity.notFound().build();

        user.get().setName(userDetails.getName());
        user.get().setSurname(userDetails.getSurname());
        user.get().setEmail(userDetails.getEmail());
        user.get().setEnabled(userDetails.getEnabled());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId){
        if(!userService.findById(userId).isPresent())
            return ResponseEntity.notFound().build();

        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> readAll (){
        return ResponseEntity.ok(userService.findAll());
    }
}
