package com.example.GestionBibliotecaUsuarios.service;


import com.example.GestionBibliotecaUsuarios.dto.ReservationDTO;
import com.example.GestionBibliotecaUsuarios.exception.BadRequestException;
import com.example.GestionBibliotecaUsuarios.exception.ResourceNotFoundException;
import com.example.GestionBibliotecaUsuarios.mapper.ReservationMapper;
import com.example.GestionBibliotecaUsuarios.model.Book;
import com.example.GestionBibliotecaUsuarios.model.Reservation;
import com.example.GestionBibliotecaUsuarios.model.User;
import com.example.GestionBibliotecaUsuarios.repository.BookRepository;
import com.example.GestionBibliotecaUsuarios.repository.ReservationRepository;
import com.example.GestionBibliotecaUsuarios.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    public List<ReservationDTO> getReservations(Boolean active, LocalDate fromDate, LocalDate toDate) {
        List<ReservationDTO> reservations = reservationRepository.findAll()
                .stream()
                .filter(res -> active == null || res.isActive() == active)
                .filter(res -> fromDate == null || !res.getReservationDate().isBefore(fromDate))
                .filter(res -> toDate == null || !res.getReservationDate().isAfter(toDate))
                .map(reservationMapper::toDTO)
                .collect(Collectors.toList());

        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("No reservations found with given filters");
        }

        return reservations;
    }

    public ReservationDTO createReservation(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));

        if (!book.isActive()) {
            throw new BadRequestException("Cannot reserve an inactive book with id " + bookId);
        }

        if (book.getUnits() <= 0) {
            throw new BadRequestException("No units available for this book");
        }

        book.setUnits(book.getUnits() - 1);
        bookRepository.save(book);

        Reservation reservation = Reservation.builder()
                .user(user)
                .book(book)
                .reservationDate(LocalDate.now())
                .active(true)
                .build();

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }

    public ReservationDTO returnBook(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id " + reservationId));

        if (!reservation.isActive()) {
            throw new BadRequestException("Reservation with id " + reservationId + " is already closed");
        }

        reservation.setActive(false);
        reservation.setReturnDate(LocalDate.now());

        Book book = reservation.getBook();
        book.setUnits(book.getUnits() + 1);
        bookRepository.save(book);

        return reservationMapper.toDTO(reservationRepository.save(reservation));
    }
}