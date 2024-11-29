package appsempresariales.gestortareas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AterrizajeController {

    @GetMapping("/")
    public String aterrizaje() {
        return "PaginaAterrizaje";
    }

}