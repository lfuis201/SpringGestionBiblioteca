package com.example.GestionBibliotecaUsuarios.service;


import com.example.GestionBibliotecaUsuarios.dto.UserDTO;
import com.example.GestionBibliotecaUsuarios.exception.BadRequestException;
import com.example.GestionBibliotecaUsuarios.exception.ResourceNotFoundException;
import com.example.GestionBibliotecaUsuarios.model.User;
import com.example.GestionBibliotecaUsuarios.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private User mapToEntity(UserDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public UserDTO saveUser(UserDTO userDTO) {
        // Validar si email ya existe
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Email already in use: " + userDTO.getEmail());
        }

        User user = mapToEntity(userDTO);
        return mapToDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        // Validar email duplicado si se cambió
        if (!existing.getEmail().equals(userDTO.getEmail()) &&
                userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Email already in use: " + userDTO.getEmail());
        }

        existing.setName(userDTO.getName());
        existing.setEmail(userDTO.getEmail());
        return mapToDTO(userRepository.save(existing));
    }

    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(existing);
    }
}
