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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
class BookAdminSecurityTest {

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
    @WithMockUser(roles = "USER")
    void nonAdminCannotCreateBook() throws Exception {
        BookDto dto = new BookDto();
        dto.setTitle("X"); dto.setAuthor("A"); dto.setPrice(BigDecimal.ONE); dto.setStock(1);
        String body = mapper.writeValueAsString(dto);

        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminCanCreateBook() throws Exception {
        BookDto dto = new BookDto();
        dto.setTitle("X"); dto.setAuthor("A"); dto.setPrice(BigDecimal.ONE); dto.setStock(1);
        String body = mapper.writeValueAsString(dto);
        when(bookService.create(any())).thenReturn(new Book("X","A",BigDecimal.ONE,1));

        mvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }
}
