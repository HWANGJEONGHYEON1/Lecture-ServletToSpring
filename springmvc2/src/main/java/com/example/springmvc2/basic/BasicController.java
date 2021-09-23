package com.example.springmvc2.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "hello-spring");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textBasicUnescaped(Model model) {
        model.addAttribute("data", "<h1>hello-spring</h1>git ");
        return "basic/text-unescaped";
    }
}
