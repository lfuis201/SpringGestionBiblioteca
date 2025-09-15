package com.example.GestionBibliotecaUsuarios.controller;

import com.example.GestionBibliotecaUsuarios.dto.ReservationDTO;
import com.example.GestionBibliotecaUsuarios.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationDTO> getReservations(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return reservationService.getReservations(active, from, to);
    }

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<ReservationDTO> createReservation(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.ok(reservationService.createReservation(userId, bookId));
    }

    @PutMapping("/return/{reservationId}")
    public ResponseEntity<ReservationDTO> returnBook(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.returnBook(reservationId));
    }
}