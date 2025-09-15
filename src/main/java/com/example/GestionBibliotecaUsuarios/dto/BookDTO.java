package com.example.GestionBibliotecaUsuarios.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private int units;
    private boolean active;
    private boolean available;
}