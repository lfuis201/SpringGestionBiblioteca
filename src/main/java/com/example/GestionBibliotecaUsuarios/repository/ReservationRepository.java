package com.example.GestionBibliotecaUsuarios.repository;

import com.example.GestionBibliotecaUsuarios.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}