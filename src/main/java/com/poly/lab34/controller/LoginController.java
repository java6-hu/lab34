package com.poly.lab34.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // Hiển thị form login
    @GetMapping("/login/form")
    public String form(Model model) {
        model.addAttribute("loginMessage", "Vui lòng đăng nhập");
        return "login"; // 🔥 vẫn là login.html
    }

    // Sau khi login thành công
    @GetMapping("/login/success")
    public String success(RedirectAttributes redirect) {
        redirect.addFlashAttribute("loginMessage", "Đăng nhập thành công");
        return "redirect:/";
    }

    // Login thất bại
    @GetMapping("/login/failure")
    public String failure(RedirectAttributes redirect) {
        redirect.addFlashAttribute("loginMessage", "Sai thông tin đăng nhập");
        return "redirect:/";
    }

    // Logout
    @GetMapping("/login/exit")
    public String exit(RedirectAttributes redirect) {
        redirect.addFlashAttribute("loginMessage", "Đăng xuất thành công");
        return "redirect:/";
    }
}