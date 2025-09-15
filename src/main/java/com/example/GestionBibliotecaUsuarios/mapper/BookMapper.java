package com.example.GestionBibliotecaUsuarios.mapper;

import com.example.GestionBibliotecaUsuarios.dto.BookDTO;
import com.example.GestionBibliotecaUsuarios.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}