package com.example.GestionBibliotecaUsuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    // Cantidad de unidades disponibles en la biblioteca
    @Column(nullable = false)
    private int units;

    private boolean available = true;

    private boolean active = true;
}