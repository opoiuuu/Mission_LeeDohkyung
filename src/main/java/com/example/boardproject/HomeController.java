package com.example.boardproject;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("home/view")
    public String homeCatagory()
    {
        return "homeview";

    }
}
