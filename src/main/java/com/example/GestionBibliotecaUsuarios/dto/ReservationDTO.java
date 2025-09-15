package com.example.GestionBibliotecaUsuarios.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private String userName;   // solo el nombre del usuario
    private String bookTitle;  // solo el título del libro
    private LocalDate reservationDate;
    private LocalDate returnDate;
    private boolean active;
}