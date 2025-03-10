package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.MasterData.asJsonString;
import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yaksha.assignment.controller.BookController;
import com.yaksha.assignment.entity.Book;
import com.yaksha.assignment.utils.JavaParserUtils;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test to check if the 'BookController' class has @RestController annotation
	@Test
	public void testRestControllerAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/BookController.java";
		boolean result1 = JavaParserUtils.checkClassAnnotation(filePath, "RestController");
		yakshaAssert(currentTest(), result1, businessTestFile);
	}

	// Test to check if 'getBooks' method has @GetMapping annotation
	@Test
	public void testGetBooksAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/BookController.java";
		boolean result = JavaParserUtils.checkMethodAnnotation(filePath, "getBooks", "GetMapping");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check if 'getBookById' method has @GetMapping annotation
	@Test
	public void testGetBookByIdAnnotation() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/BookController.java";
		boolean result = JavaParserUtils.checkMethodAnnotation(filePath, "getBookById", "GetMapping");
		yakshaAssert(currentTest(), result, businessTestFile);
	}

	// Test to check POST request to create a book with valid data
	@Test
	public void testCreateBook() throws Exception {
		Book book = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(book));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 200 ? "true" : "false"), businessTestFile);
	}

	// Test to check PUT request to update a book with valid data
	@Test
	public void testUpdateBook() throws Exception {
		Book updatedBook = new Book(1L, "The Great Gatsby - Updated", "F. Scott Fitzgerald");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/books/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(updatedBook));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 200 ? "true" : "false"), businessTestFile);
	}

	// Test to check PUT request to update a book with invalid ID (not found)
	@Test
	public void testUpdateBookWithInvalidId() throws Exception {
		Book updatedBook = new Book(99L, "Nonexistent Book", "Unknown Author");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/books/99").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(updatedBook));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 404 ? "true" : "false"), businessTestFile);
	}

	// Test to check DELETE request for a book
	@Test
	public void testDeleteBook() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/books/1")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 200 ? "true" : "false"), businessTestFile);
	}

	// Test to check GET request for all books
	@Test
	public void testGetBooks() throws Exception {
		List<Book> books = List.of(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald"),
				new Book(2L, "1984", "George Orwell"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// Check if the response body is not empty
		String responseContent = result.getResponse().getContentAsString();
		yakshaAssert(currentTest(), (responseContent != null && !responseContent.isEmpty() ? "true" : "false"),
				businessTestFile);
	}

	// Test to check GET request for a book by its ID
	@Test
	public void testGetBookById() throws Exception {
		Book book = new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/books/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String responseContent = result.getResponse().getContentAsString();
		yakshaAssert(currentTest(), (responseContent != null && !responseContent.isEmpty() ? "true" : "false"),
				businessTestFile);
	}
}
