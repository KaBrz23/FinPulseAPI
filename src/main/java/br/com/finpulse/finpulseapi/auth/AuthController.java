package br.com.finpulse.finpulseapi.auth;

import br.com.finpulse.finpulseapi.cliente.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){

        var user = clienteRepository.findByEmail(credentials.email())
                .orElseThrow(() -> new RuntimeException("Access Denied"));

        if ( !passwordEncoder.matches(credentials.senha(), user.getSenha()) )
            throw new RuntimeException("Access Denied");

        return tokenService.create(user);
    }

}
