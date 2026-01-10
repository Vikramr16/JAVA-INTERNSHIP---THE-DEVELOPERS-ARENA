package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.Wishlist;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.WishlistRepository;

@Controller
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/wishlist/toggle/{productId}")
    public String toggleWishlist(@PathVariable Long productId,
                                 HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Wishlist existing =
            wishlistRepository.findByUserIdAndProductId(
                user.getId(), productId);

        if (existing != null) {
            wishlistRepository.delete(existing);
        } else {
            Product product =
                productRepository.findById(productId).orElse(null);

            if (product == null) {
                return "redirect:/products";
            }

            Wishlist w = new Wishlist();
            w.setUser(user);
            w.setProduct(product);
            wishlistRepository.save(w);
        }

        return "redirect:/products";
    }
}
