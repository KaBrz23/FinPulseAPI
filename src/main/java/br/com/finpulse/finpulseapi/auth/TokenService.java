package br.com.finpulse.finpulseapi.auth;

import br.com.finpulse.finpulseapi.cliente.Cliente;
import br.com.finpulse.finpulseapi.cliente.ClienteRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private final ClienteRepository clienteRepository;
    private Algorithm algorithm;

    public TokenService(ClienteRepository clienteRepository, @Value("${jwt.secret}") String secret) {
        this.clienteRepository = clienteRepository;
        algorithm = Algorithm.HMAC256(secret);
    }

    public Token create(Cliente cliente){
        var expiresAt = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.ofHours(-3));

        String token = JWT.create()
                .withIssuer("finpulse")
                .withSubject(cliente.getEmail())
                .withClaim("role", "admin")
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new Token(token);
    }

    public Cliente getUserFromToken(String token) {
        var email =JWT.require(algorithm)
                .withIssuer("finpulse")
                .build()
                .verify(token)
                .getSubject();

        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
}
