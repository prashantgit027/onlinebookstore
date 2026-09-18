package com.example.onlinebookstore;

import com.example.onlinebookstore.domain.Book;
import com.example.onlinebookstore.domain.Cart;
import com.example.onlinebookstore.domain.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import com.example.onlinebookstore.repository.UserRepository;
import com.example.onlinebookstore.service.CartService;
import com.example.onlinebookstore.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void cannotCheckoutEmptyCart() {
        User u = userService.register("testuser","pass");
        try {
            cartService.checkout(u);
            Assert.fail("Expected exception");
        } catch (IllegalStateException ex) {
            Assert.assertTrue(ex.getMessage().contains("empty"));
        }
    }

    @Test
    public void addItemAndCheckout() {
        User u = userService.register("user2","pass");
        Book b = bookRepository.findAll().get(0);
        cartService.addItem(u,b.getId(),1);
        Cart cart = cartService.getCartForUser(u);
        Assert.assertFalse(cart.getItems().isEmpty());
    }
}
