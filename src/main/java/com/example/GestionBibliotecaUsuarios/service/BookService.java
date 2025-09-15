package com.example.GestionBibliotecaUsuarios.service;

import com.example.GestionBibliotecaUsuarios.dto.BookDTO;
import com.example.GestionBibliotecaUsuarios.exception.BadRequestException;
import com.example.GestionBibliotecaUsuarios.exception.ResourceNotFoundException;
import com.example.GestionBibliotecaUsuarios.mapper.BookMapper;
import com.example.GestionBibliotecaUsuarios.model.Book;
import com.example.GestionBibliotecaUsuarios.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = bookRepository.findAll()
                .stream()
                .filter(Book::isActive) // only active books
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books available");
        }

        return books;
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        if (!book.isActive()) {
            throw new ResourceNotFoundException("Book with id " + id + " is inactive");
        }

        return bookMapper.toDTO(book);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        if (bookDTO.getTitle() == null || bookDTO.getTitle().isBlank()) {
            throw new BadRequestException("Invalid book data: title is required");
        }
        if (bookDTO.getUnits() < 0) {
            throw new BadRequestException("Invalid book data: units cannot be negative");
        }

        Book book = bookMapper.toEntity(bookDTO);
        book.setActive(true);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        if (!book.isActive()) {
            throw new BadRequestException("Cannot update an inactive book with id " + id);
        }

        if (bookDTO.getTitle() == null || bookDTO.getTitle().isBlank()) {
            throw new BadRequestException("Invalid book data: title is required");
        }
        if (bookDTO.getUnits() < 0) {
            throw new BadRequestException("Invalid book data: units cannot be negative");
        }

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setUnits(bookDTO.getUnits());

        return bookMapper.toDTO(bookRepository.save(book));
    }

    // Physical delete
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        bookRepository.delete(book);
    }

    // Soft delete
    public void deactivateBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        if (!book.isActive()) {
            throw new BadRequestException("Book with id " + id + " is already inactive");
        }

        book.setActive(false);
        bookRepository.save(book);
    }
}