package com.lms.service;

import com.lms.entity.Book;
import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.lms.controller.UserController;
import com.lms.dto.BookDto;
import com.lms.entity.Book;
import com.lms.entity.Library;
import com.lms.entity.User;
import com.lms.exception.BookIdNotFoundException;
import com.lms.exception.BookUnableToBorrowException;
import com.lms.exception.LibraryIdNotFoundException;
import com.lms.repository.BookRepository;
import com.lms.repository.LibraryRepository;
import com.lms.repository.UserRepository;
import com.lms.util.ResponseStructure;

@Service
public class BookService {

    
    
@Autowired
ModelMapper mapper;

@Autowired
BookRepository bookRepository;
	
@Autowired
LibraryRepository libraryRepository;

    @Autowired
UserRepository userRepository;

   
	public ResponseEntity<ResponseStructure<Book>> saveBook(BookDto bookDto,int libraryId){
		Book book = mapper.map(bookDto,Book.class);
		bookRepository.save(book);
		Optional<Library> optional= libraryRepository.findById(libraryId);
		if(optional.isPresent()) {
			Library library = optional.get();
			List<Book> listOfBooks = library.getBooks();
			if(listOfBooks==null) {
				listOfBooks = new ArrayList<Book>();
			}
			listOfBooks.add(book);
			libraryRepository.save(library);
			
			ResponseStructure<Book> responseStructure = new ResponseStructure<>();
			responseStructure.setData(book);
			responseStructure.setMessage("Book saved and added to library successfully!");
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Book>>(responseStructure,HttpStatus.CREATED);
		}
		else{
			throw new LibraryIdNotFoundException("Library of this id is not available");
		}
	}
	
	 public ResponseEntity<ResponseStructure<Book>> getBookById(int bookId) {
	        Optional<Book> optional = bookRepository.findById(bookId);
	        if (optional.isPresent()) {
	            ResponseStructure<Book> responseStructure = new ResponseStructure<>();
	            responseStructure.setData(optional.get());
	            responseStructure.setMessage("Book fetched successfully!");
	            responseStructure.setStatusCode(HttpStatus.OK.value());
	            return new ResponseEntity<ResponseStructure<Book>>(responseStructure, HttpStatus.OK);
	        } else {
	            throw new BookIdNotFoundException("Book of this id is not available");
	        }
	    }
	 
	 public ResponseEntity<ResponseStructure<List<Book>>> getAllBooks() {
	        List<Book> books = bookRepository.findAll();

	        ResponseStructure<List<Book>> responseStructure = new ResponseStructure<>();
	        responseStructure.setData(books);
	        responseStructure.setMessage("All books fetched successfully!");
	        responseStructure.setStatusCode(HttpStatus.OK.value());

	        return new ResponseEntity<ResponseStructure<List<Book>>>(responseStructure, HttpStatus.OK);
	    }
	 
	 public ResponseEntity<ResponseStructure<Book>> updateBook(int bookId, BookDto bookDto) {
	        Optional<Book> optional = bookRepository.findById(bookId);
	        if (optional.isPresent()) {
	            Book existingBook = optional.get();

	            
	            mapper.map(bookDto, existingBook);

	            Book updatedBook = bookRepository.save(existingBook);

	            ResponseStructure<Book> responseStructure = new ResponseStructure<>();
	            responseStructure.setData(updatedBook);
	            responseStructure.setMessage("Book updated successfully!");
	            responseStructure.setStatusCode(HttpStatus.OK.value());

	            return new ResponseEntity<ResponseStructure<Book>>(responseStructure, HttpStatus.OK);
	        } else {
	            throw new BookIdNotFoundException("Book of this id is not available for update");
	        }
	    }

	
	 
	 
	 
	 public ResponseEntity<ResponseStructure<String>> deleteBook(int bookId) {
		    Optional<Book> optional = bookRepository.findById(bookId);
		    if (optional.isPresent()) {
		        Book book = optional.get();
		        
		        
		        List<Library> libraries = libraryRepository.findAll();
		        for (Library lib : libraries) {
		            if (lib.getBooks() != null && lib.getBooks().contains(book)) {
		                lib.getBooks().remove(book);
		                libraryRepository.save(lib); 
		            }
		        }

		       
		        bookRepository.delete(book);

		        
		        ResponseStructure<String> responseStructure = new ResponseStructure<>();
		        responseStructure.setData("Book deleted successfully!");
		        responseStructure.setMessage("Deletion successful");
		        responseStructure.setStatusCode(HttpStatus.OK.value());

		        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		    } else {
		        throw new BookIdNotFoundException("Book of this id is not available for deletion");
		    }
		}

	 
	 public ResponseEntity<ResponseStructure<Book>> borrowBook(int userId,int bookId){
		 Optional<User>optionalUser=userRepository.findById(userId);
		 Optional<Book>optionalBook =bookRepository.findById(bookId);
		 
		 Book book =optionalBook.get();
		 User user=optionalUser.get();
		 
		 if(book!=null && user!=null && !book.isBorrowed()) {
			 book.setUser(user);
			 book.setBorrowedTime(LocalDateTime.now());
			 book.setBorrowed(true);
			 
			 bookRepository.save(book);
			 ResponseStructure<Book> responseStructure= new ResponseStructure<>();
			 responseStructure.setData(book);
			 responseStructure.setMessage("Book Borrowed successfull!");
			 return new ResponseEntity<ResponseStructure<Book>>(responseStructure,HttpStatus.OK);
		 }
		 else {
			 throw new BookIdNotFoundException("User or book id is not availaible or book is already borrowed");
		 }
	 }
	 
	 public ResponseEntity<ResponseStructure<Book>> returnBook(int bookId) {
		    Optional<Book> optionalBook = bookRepository.findById(bookId);

		    if (optionalBook.isPresent()) {
		        Book book = optionalBook.get();

		        if (!book.isBorrowed()) {
		            
		            throw new BookUnableToBorrowException("This book is not borrowed, cannot return!");
		        }

		       
		        book.setUser(null);
		        book.setBorrowed(false);
		        book.setReturnTime(LocalDateTime.now());

		        Book updatedBook = bookRepository.save(book);

		        ResponseStructure<Book> responseStructure = new ResponseStructure<>();
		        responseStructure.setData(updatedBook);
		        responseStructure.setMessage("Book returned successfully!");
		        responseStructure.setStatusCode(HttpStatus.OK.value());

		        return new ResponseEntity<ResponseStructure<Book>>(responseStructure, HttpStatus.OK);

		    } else {
		        
		        throw new BookIdNotFoundException("Book with id " + bookId + " not found!");
		    }
		}

}
