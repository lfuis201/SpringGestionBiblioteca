package com.example.GestionBibliotecaUsuarios.mapper;

import com.example.GestionBibliotecaUsuarios.dto.ReservationDTO;
import com.example.GestionBibliotecaUsuarios.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "book.title", target = "bookTitle")
    ReservationDTO toDTO(Reservation reservation);

    // Para crear reservas desde DTO no necesitamos todos los datos,
    // por lo que podrías no mapearlo en ambos sentidos.
}