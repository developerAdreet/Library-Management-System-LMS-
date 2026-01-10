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

import com.lms.dto.LibraryDto;
import com.lms.entity.Library;
import com.lms.service.LibraryService;
import com.lms.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/library")
@Tag(name="LibraryController",description="has rest end points of library")
public class LibraryController {

  

 @Autowired
  LibraryService libraryService;

 
 
 @Operation(operationId="createLibrary",summary="adding library",description="this"
			+ " rest end point is used to add a library and map with an address",
			parameters = { @Parameter(name="addressId",required=true,
			description="pass address id to which the library will be linked")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="201",
					     description="creates and returns the saved library",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Library.class))),
			@ApiResponse(responseCode = "404",
			description="library cannot be saved to this adressId")
	}) 
	
 @PostMapping("/{addressId}")
	public ResponseEntity<ResponseStructure<Library>> saveLibrary(@RequestBody LibraryDto libraryDto, @PathVariable int addressId){
		return libraryService.saveLibrary(libraryDto, addressId);
	}
 
 
 
 
 @Operation(operationId="getting library by id",summary="getting one library by id",description="this"
			+ " rest end point is used to return a library by id",
			parameters= {@Parameter(name="libraryId",required=true,
			description="pass library id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns one library",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Library.class))),
			@ApiResponse(responseCode = "404",
			description="library not found for this id")
	})
 
 @GetMapping("/{libraryId}")
 public ResponseEntity<ResponseStructure<Library>> findLibraryById(@PathVariable int libraryId){
	 return libraryService.findLibraryById(libraryId);
 }
 
 
 
 
 
 @Operation(operationId="getting all libraries ",summary="getting all libraries",description="this"
			+ " rest end point is used to return all libraries")
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="returns all libraries",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Library.class))),
	})
 @GetMapping
 public ResponseEntity<ResponseStructure<List<Library>>> findAllLibrary(){
	 return libraryService.findAllLibrary();
 }
 
 
 
 
 
 @Operation(operationId="updating library by id",summary="updating one library by id",description="this"
			+ " rest end point is used to update a library by id",
			parameters= {@Parameter(name="libraryId",required=true,
			description="pass library id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="updates one library",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Library.class))),
			@ApiResponse(responseCode = "404",
			description="library not found for this id to update")
	})
 @PutMapping("/{libraryId}")
 public ResponseEntity<ResponseStructure<Library>> updateLibrary( @RequestBody LibraryDto libraryDto,@PathVariable int libraryId){
	 return libraryService.updateLibrary(libraryDto, libraryId);
 }
 
 
 
 
 
 @Operation(operationId="deleting library by id",summary="deleting one library by id",description="this"
			+ " rest end point is used to delete a library by id",
			parameters= {@Parameter(name="libraryId",required=true,
			description="pass library id")})
	@ApiResponses(value= {
			@ApiResponse(responseCode="200",
					     description="deletes one library",
					     content= @Content(mediaType="application/json",
					     schema =@Schema(implementation=Library.class))),
			@ApiResponse(responseCode = "404",
			description="library not found for this id to delete")
	})
 
 @DeleteMapping("/{libraryId}")
 public ResponseEntity<ResponseStructure<String>> deleteLibrary(@PathVariable int libraryId){
	 return libraryService.deleteLibrary(libraryId);
 }
}
