package com.example.Auth_Service.Controller;

import com.example.Auth_Service.DTO.ApiResponseDTO;
import com.example.Auth_Service.DTO.LoginRequestDTO;
import com.example.Auth_Service.DTO.LoginResponseDTO;
import com.example.Auth_Service.DTO.RegisterRequestDTO;
import com.example.Auth_Service.Domain.Customers;
import com.example.Auth_Service.Infra.SecurityConfig.TokenService;
import com.example.Auth_Service.Repositories.CustomerRepository;
import com.example.Auth_Service.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint for user registration.
     *
     * @param requestDTO Contains user registration details.
     * @return ResponseEntity indicating success or failure of the registration process.
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequestDTO requestDTO) {
        String body = "Your registration was a success";

        // Check if the email already exists in the database
        if (this.repository.findByEmail(requestDTO.email()) != null)
            return ResponseEntity.badRequest().body(new ApiResponseDTO(false, "Email already exists"));

        // Encrypt the user's password before saving it
        String encryptedPassword = new BCryptPasswordEncoder().encode(requestDTO.password());
        Customers customer = new Customers(requestDTO);
        customer.setPassword(encryptedPassword);


        this.repository.save(customer);

        // Send a welcome notification to the customer
        authService.registerNotification(customer.getEmail(), "Welcome", body);
        return ResponseEntity.ok(new ApiResponseDTO(true, "Registration Successful"));
    }

    /**
     * Endpoint for user login.
     *
     * @param requestDTO Contains user login credentials.
     * @return ResponseEntity containing the authentication token if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        try {
            // Create authentication token with email and password
            var emailPassword = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.password());

            var auth = this.authenticationManager.authenticate(emailPassword);

            var token = this.tokenService.generateToken((Customers) auth.getPrincipal());
            return ResponseEntity.ok(new ApiResponseDTO(true, "Login Successful", new LoginResponseDTO(token)));
        } catch (RuntimeException exception) {

            return ResponseEntity.badRequest().body(new ApiResponseDTO(false, "Credentials do not match"));
        }
    }

    /**
     * Endpoint to validate the provided JWT token.
     *
     * @param token The authorization token sent in the request header.
     * @return ResponseEntity containing the user's email if the token is valid, otherwise an error message.
     */
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Validate the token and retrieve the associated email
            String email = tokenService.validadeToken(token.replace("Bearer ", ""));
            return ResponseEntity.ok(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
