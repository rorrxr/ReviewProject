package com.minju.reviewproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @GetMapping("/products")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/products/{productId}/review")
    @ResponseBody
    public String products(@RequestParam String productId,
                           @RequestParam int cursor,
                           @RequestParam int size,
                           Model model) {
        return "products";
    }

    @PostMapping("/products/{productId}/reviews")
    public String review(@RequestParam String productId){
        return "redirect:/products/"+productId;
    }
}
