package com.example.GestionBibliotecaUsuarios.controller;

import com.example.GestionBibliotecaUsuarios.dto.UserDTO;
import com.example.GestionBibliotecaUsuarios.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET user by id
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id); // lanza ResourceNotFoundException si no existe
    }

    // POST create new user
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO); // lanza BadRequestException si email ya existe
    }

    // PUT update existing user
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id,
                              @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO); // lanza ResourceNotFoundException o BadRequestException
    }

    // DELETE user by id
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // lanza ResourceNotFoundException si no existe
    }
}
