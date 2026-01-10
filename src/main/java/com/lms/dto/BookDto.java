package com.lms.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BookDto {
	private int bookId;
	@NotNull
	@NotBlank
	private String title;
	@Size(min=3)
	private String author;
	private boolean borrowed;
	private LocalDateTime borrowedTime;
	private LocalDateTime returnTime;
}
