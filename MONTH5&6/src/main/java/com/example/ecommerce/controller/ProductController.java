package com.example.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.Wishlist;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.repository.WishlistRepository;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    // 🔹 1. ALL PRODUCTS (PUBLIC)
    @GetMapping
    public String viewAllProducts(Model model, HttpSession session) {

        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());

        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute(
                "wishlistIds",
                wishlistRepository.findByUserId(user.getId())
                    .stream()
                    .map(w -> w.getProduct().getId())
                    .toList()
            );
        }

        return "products";
    }

    // 🔹 2. PRODUCTS BY CATEGORY (PUBLIC) ✅ REQUIRED FIX
    @GetMapping("/category/{id}")
    public String viewByCategory(@PathVariable Long id,
                                 Model model,
                                 HttpSession session) {

        // 🔹 Electronics → show ALL products
        if (id == 1) {
            model.addAttribute("products", productRepository.findAll());
        }
        // 🔹 Other categories → show filtered products
        else {
            model.addAttribute("products",
                    productRepository.findByCategoryId(id));
        }

        model.addAttribute("categories", categoryRepository.findAll());

        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute(
                "wishlistIds",
                wishlistRepository.findByUserId(user.getId())
                    .stream()
                    .map(w -> w.getProduct().getId())
                    .toList()
            );
        }

        return "products";
    }


    // 🔹 3. PRODUCT DETAILS (PUBLIC)
    @GetMapping("/view/{id}")
    public String productDetails(@PathVariable Long id,
                                 Model model,
                                 HttpSession session) {

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }

        model.addAttribute("product", product);
        model.addAttribute("reviews",
                reviewRepository.findByProductId(id));

        // keep category menu visible
        model.addAttribute("categories", categoryRepository.findAll());

        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute(
                "wishlistIds",
                wishlistRepository.findByUserId(user.getId())
                    .stream()
                    .map(w -> w.getProduct().getId())
                    .toList()
            );
        }

        return "product-details";
    }

    // 🔹 4. ADD TO WISHLIST (GUEST + LOGGED USER)
    @PostMapping("/wishlist/{id}")
    public String addToWishlist(@PathVariable Long id, HttpSession session) {

        User user = (User) session.getAttribute("user");

        // ✅ GUEST USER → store in session
        if (user == null) {

            List<Long> guestWishlist =
                (List<Long>) session.getAttribute("guestWishlist");

            if (guestWishlist == null) {
                guestWishlist = new ArrayList<>();
            }

            if (!guestWishlist.contains(id)) {
                guestWishlist.add(id);
            }

            session.setAttribute("guestWishlist", guestWishlist);
            return "redirect:/products/view/" + id;
        }

        // ✅ LOGGED-IN USER → save in DB
        if (!wishlistRepository
                .existsByUserIdAndProductId(user.getId(), id)) {

            Wishlist w = new Wishlist();
            w.setUser(user);
            w.setProduct(productRepository.findById(id).get());
            wishlistRepository.save(w);
        }

        return "redirect:/products/view/" + id;
    }
}
