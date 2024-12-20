package com.rest.catalogolibri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.rest.catalogolibri.dto.LibroDto;
import com.rest.catalogolibri.model.User;
import com.rest.catalogolibri.repository.IUserRepository;
import com.rest.catalogolibri.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ThymeleafController {

    @Autowired
    private final RestTemplate rt;

    @Autowired
    private final JwtUtil ju;  // Se hai un utility JWT per gestire il token nel client

    @Autowired
    private final IUserRepository ur;

    @Autowired
    public ThymeleafController(RestTemplate rt, JwtUtil ju, IUserRepository ur) {
        this.rt = rt;
        this.ju = ju;
        this.ur = ur;
    }

    @GetMapping("/login.html")
    public String loginForm() {
        return "login"; // Nome del tuo template Thymeleaf per il login
    }

    @PostMapping("/autentica")
    public String autentica(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model) {
        try {
            // Crea l'oggetto User per l'autenticazione
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            // Invia la richiesta di login al backend
            ResponseEntity<String> response = rt.postForEntity("http://localhost:8080/ruolo/login", user, String.class);

            // Verifica la risposta
            if (response.getStatusCode().is2xxSuccessful()) {
                String token = response.getBody(); // Ottieni il token JWT

                // Log per verificare il token
                System.out.println("Token ricevuto: " + token);

                // Controlla se il token è null o vuoto
                if (token == null || token.isEmpty()) {
                    model.addAttribute("errorMessage", "Errore: token JWT non ricevuto.");
                    return "login";
                }

                // Salva il token nella sessione
                request.getSession().setAttribute("jwtToken", token);

                return "redirect:/home"; // Reindirizza alla home page dopo il login
            } else {
                // Gestione dell'errore di autenticazione
                model.addAttribute("errorMessage", "Credenziali non valide");
                return "login";
            }
        } catch (Exception e) {
            // Gestione di altri errori
            model.addAttribute("errorMessage", "Errore durante l'autenticazione: " + e.getMessage());
            return "login";
        }
    }


    // Altri metodi del controller che richiedono autenticazione
    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        String jwtToken = (String) request.getSession().getAttribute("jwtToken");
        System.out.println("ecco il token" + jwtToken);
        if (request.getSession(false) != null && request.getSession(false).getAttribute("jwtToken") !=null) {
            // Aggiungi il token al model per poterlo usare nelle chiamate AJAX
            model.addAttribute("jwtToken", jwtToken);
            return "index"; // Nome del tuo template Thymeleaf per la home page
        } else {
            return "redirect:/login.html"; // Reindirizza al login se non autenticato
        }
    }
    
    @GetMapping("/listaLibri")
    public String listaLibri(HttpServletRequest request, Model model) {
        String jwtToken = (String) request.getSession().getAttribute("jwtToken");

        // Verifica che il token sia presente nella sessione
        if (jwtToken != null) {
            System.out.println("Token JWT recuperato dalla sessione: " + jwtToken);

            try {
                // Aggiungi l'intestazione Authorization con il token JWT
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + jwtToken);

                HttpEntity<String> entity = new HttpEntity<>(headers);

                // Effettua la richiesta al backend
                ResponseEntity<LibroDto[]> response = rt.exchange(
                    "http://localhost:8080/api/libro/lista",
                    HttpMethod.GET,
                    entity,
                    LibroDto[].class
                );

                // Converti la risposta in lista
                LibroDto[] libri = response.getBody();
                List<LibroDto> listaLibri = List.of(libri);

                // Aggiungi la lista al modello
                model.addAttribute("lista", listaLibri);
                return "listaLibri"; // Nome del template Thymeleaf
            } catch (Exception e) {
                System.err.println("Errore durante il recupero della lista libri: " + e.getMessage());
                model.addAttribute("errorMessage", "Errore durante il recupero della lista: " + e.getMessage());
                return "error";
            }
        } else {
            System.out.println("Nessun token JWT trovato nella sessione. Reindirizzo al login.");
            return "redirect:/login.html";
        }
    }





}

