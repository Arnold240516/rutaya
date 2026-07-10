package com.rutaya.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    // /admin -> sirve admin.html con URL limpia compartible
    @GetMapping("/admin")
    public String admin() { return "forward:/admin.html"; }

    @GetMapping("/terminos")
    public String terminos() { return "forward:/terminos.html"; }

    @GetMapping("/privacidad")
    public String privacidad() { return "forward:/privacidad.html"; }
}
