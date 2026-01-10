package com.example.ecommerce.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.WishlistRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.repository.ProductRepository;

@Controller
public class ProfileController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // USER INFO
        model.addAttribute("user", user);

        // WISHLIST ITEMS
        model.addAttribute(
            "wishlist",
            wishlistRepository.findByUserId(user.getId())
        );

        // USER REVIEWS
        model.addAttribute(
            "reviews",
            reviewRepository.findByUserId(user.getId())
        );

        return "profile";
    }
}
