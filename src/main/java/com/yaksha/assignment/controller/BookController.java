//package com.yaksha.assignment.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yaksha.assignment.entity.Book;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/books")
//@Validated
//public class BookController {
//
//	// Sample hard-coded list of books
//	private List<Book> books = List.of(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald"),
//			new Book(2L, "1984", "George Orwell"), new Book(3L, "To Kill a Mockingbird", "Harper Lee"));
//
//	// Endpoint to fetch all books
//	@GetMapping
//	public List<Book> getBooks() {
//		return books;
//	}
//
//	// Endpoint to fetch a book by its ID
//	@GetMapping("/{id}")
//	public Book getBookById(@PathVariable Long id) {
//		Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();
//		return book.orElse(null);
//	}
//
//	// Endpoint to create a new book with validation
//	@PostMapping
//	public ResponseEntity<?> createBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//		}
//		books.add(book);
//		return ResponseEntity.ok(book);
//	}
//
//	// Endpoint to update an existing book with validation
//	@PutMapping("/{id}")
//	public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody Book book,
//			BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
//		}
//
//		for (int i = 0; i < books.size(); i++) {
//			if (books.get(i).getId().equals(id)) {
//				books.set(i, book);
//				return ResponseEntity.ok(book);
//			}
//		}
//		return ResponseEntity.notFound().build(); // If book not found
//	}
//
//	// Endpoint to delete a book by its ID
//	@DeleteMapping("/{id}")
//	public void deleteBook(@PathVariable Long id) {
//		books.removeIf(book -> book.getId().equals(id));
//	}
//}

package com.yaksha.assignment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.entity.Book;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
@Validated
public class BookController {

	// Sample mutable list of books
	private List<Book> books = new ArrayList<>(List.of(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald"),
			new Book(2L, "1984", "George Orwell"), new Book(3L, "To Kill a Mockingbird", "Harper Lee")));

	// Endpoint to fetch all books
	@GetMapping
	public List<Book> getBooks() {
		return books;
	}

	// Endpoint to fetch a book by its ID
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Long id) {
		Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();
		return book.orElse(null);
	}

	// Endpoint to create a new book with validation
	@PostMapping
	public ResponseEntity<?> createBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}
		books.add(book);
		return ResponseEntity.ok(book);
	}

	// Endpoint to update an existing book with validation
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody Book book,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
		}

		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId().equals(id)) {
				books.set(i, book); // Now allowed since the list is mutable
				return ResponseEntity.ok(book);
			}
		}
		return ResponseEntity.notFound().build(); // If book not found
	}

	// Endpoint to delete a book by its ID
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable Long id) {
		books.removeIf(book -> book.getId().equals(id));
	}
}
