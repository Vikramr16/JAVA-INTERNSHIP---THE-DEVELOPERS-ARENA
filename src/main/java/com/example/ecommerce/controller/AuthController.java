package com.example.ecommerce.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.Wishlist;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.WishlistRepository;
import com.example.ecommerce.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        try {
            User user = userService.login(username, password);
            session.setAttribute("user", user);

            // 🔹 MERGE GUEST WISHLIST
            List<Long> guestWishlist =
                (List<Long>) session.getAttribute("guestWishlist");

            if (guestWishlist != null) {
                for (Long pid : guestWishlist) {
                    if (!wishlistRepository
                            .existsByUserIdAndProductId(user.getId(), pid)) {

                        Wishlist w = new Wishlist();
                        w.setUser(user);
                        w.setProduct(productRepository.findById(pid).get());
                        wishlistRepository.save(w);
                    }
                }
                session.removeAttribute("guestWishlist");
            }

            return "redirect:/profile";

        } catch (Exception e) {
            model.addAttribute("loginError", "Invalid credentials");
            return "login";
        }
        
    }
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String contactNo,
            Model model
    ) {
        try {
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setContactNo(contactNo);

            userService.register(user);

            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

}
