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

    // ✅ List with filters (active, date range)
    @GetMapping
    public List<ReservationDTO> getReservations(
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return reservationService.getReservations(active, from, to);
    }

    // ✅ Get reservation by id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    // ✅ Create reservation
    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<ReservationDTO> createReservation(
            @PathVariable Long userId,
            @PathVariable Long bookId,
            @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(userId, bookId, reservationDTO));
    }


    // ✅ Update reservation (ej. cambiar fecha de devolución manualmente)
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id,
                                                            @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationDTO));
    }

    // ✅ Return book (close reservation)
    @PutMapping("/return/{reservationId}")
    public ResponseEntity<ReservationDTO> returnBook(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.returnBook(reservationId));
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.deactivateReservation(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.deleteReservation(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }


}