package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.config.SecurityConfig;
import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.security.CustomUserDetailsService;
import com.example.onlinebookstore.security.JwtUtil;
import com.example.onlinebookstore.service.BookServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    BookServicePort bookService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    JwtUtil jwtUtil;

    @Test
    void listReturnsPage() throws Exception {
        Book b = new Book("Title","Author", BigDecimal.ONE, 3);
        when(bookService.listAll(any(PageRequest.class))).thenReturn(new PageImpl<>(Collections.singletonList(b)));

        mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Title"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createInvalidPayloadReturnsBadRequest() throws Exception {
        BookDto dto = new BookDto(); // missing required fields
        String body = mapper.writeValueAsString(dto);

        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest());
    }
}
