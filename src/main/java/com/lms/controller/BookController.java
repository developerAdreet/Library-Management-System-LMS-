package com.lms.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.BookDto;
import com.lms.entity.Book;
import com.lms.entity.Library;
import com.lms.service.BookService;
import com.lms.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book")
@Tag(name="BookController",description="has rest end points of Book")
public class BookController {

	@Autowired
    BookService bookService;
	
	
	@Operation(operationId="createBook",summary="adding book",description="this"
			+ " rest end point is used to add a book and map with a library id",
			parameters = { @Parameter(name="libraryId",required=true,
			description="pass library id to which the book will be saved")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="201",
					     description="creates and returns the saved book",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Book.class))),
			@ApiResponse(responseCode = "404",
			description="book cannot be saved to this libraryId")
	}) 
	@PostMapping("/{libraryId}")
	public ResponseEntity<ResponseStructure<Book>>saveBook(@RequestBody BookDto bookDto,@PathVariable int libraryId){
		return bookService.saveBook(bookDto, libraryId);
	}
	
	
	
	
	
	
	@Operation(operationId="getting book by id",summary="getting one book by id",description="this"
			+ " rest end point is used to return a book by id",
			parameters= {@Parameter(name="bookId",required=true,
			description="pass book id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns one book",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Book.class))),
			@ApiResponse(responseCode = "404",
			description="book not found for this id")
	})
	 @GetMapping("/{bookId}")
	    public ResponseEntity<ResponseStructure<Book>> getBookById(@PathVariable int bookId) {
	        return bookService.getBookById(bookId);
	    }
	
	
	@Operation(operationId="getting all book ",summary="getting all books",description="this"
			+ " rest end point is used to return all books")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns all books",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Book.class))),
	})
	 
	 @GetMapping
	    public ResponseEntity<ResponseStructure<List<Book>>> getAllBooks() {
	        return bookService.getAllBooks();
	    }
	 
	
	
	@Operation(operationId="updating book by id",summary="updating one book by id",description="this"
			+ " rest end point is used to update a book by id",
			parameters= {@Parameter(name="bookId",required=true,
			description="pass book id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="updates one book",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Book.class))),
			@ApiResponse(responseCode = "404",
			description="book not found for this id to update")
	})
	 @PutMapping("/{bookId}")
	    public ResponseEntity<ResponseStructure<Book>> updateBook(@PathVariable int bookId, @RequestBody BookDto bookDto) {
	        return bookService.updateBook(bookId, bookDto);
	    }
	
	
	@Operation(operationId="deleting book by id",summary="deleting one book by id",description="this"
			+ " rest end point is used to delete a book by id",
			parameters= {@Parameter(name="bookId",required=true,
			description="pass book id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="deletes one book",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Book.class))),
			@ApiResponse(responseCode = "404",
			description="book not found for this id to delete")
	})
	 
	 @DeleteMapping("/{bookId}")
	    public ResponseEntity<ResponseStructure<String>> deleteBook(@PathVariable int bookId) {
	        return bookService.deleteBook(bookId);
	    }
	
	@Operation(operationId="borrowing book by userid and bookid",summary="borrowing one book by userid and bookid",
			description="this"
			+ " rest end point is used to borrow a book by userid and bookid",
			parameters= {@Parameter(name="userId",required=true,
			description="pass user id"),
@Parameter(name="bookId",required=true,description="pass book id")})	
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="borrow  one book from library",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Library.class))),
			@ApiResponse(responseCode = "404",
			description="book not found for this id to borrow")
	})
	 
	 @PutMapping("/{userId}/{bookId}")
	 public ResponseEntity<ResponseStructure<Book>> borrowBook(@PathVariable int userId,
	                                                           @PathVariable int bookId) {
	     return bookService.borrowBook(userId, bookId);
	 }
	
	
	
	
	@Operation(
		    operationId = "returning a borrowed book",
		    summary = "return a borrowed book by bookId",
		    description = "this rest endpoint is used to return a borrowed book by passing the bookId",
		    parameters = {
		        @Parameter(name = "bookId",  required = true, description = "enter id of the book")
		    }
		)
		@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Book returned successfully",
		        content = @Content(mediaType = "application/json",
		                           schema = @Schema(implementation = Book.class))
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "book not found or book is not borrowed"
		    )
		})
	
	
	 @PutMapping("/{bookId}/return")
	 public ResponseEntity<ResponseStructure<Book>> returnBook(@PathVariable int bookId) {
	     return bookService.returnBook(bookId);
	 }
}
