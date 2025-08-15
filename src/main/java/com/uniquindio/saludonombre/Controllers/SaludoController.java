package com.uniquindio.saludonombre.Controllers;

import com.uniquindio.saludonombre.dto.MessageDTO;
import com.uniquindio.saludonombre.dto.NameDTO;
import com.uniquindio.saludonombre.Services.SaludoService;
import com.uniquindio.saludonombre.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SaludoController {

    private final SaludoService saludoService;
    private final JwtUtil jwtUtil;

    @GetMapping("/saludo")
    public ResponseEntity<MessageDTO<String>> generarSaludo(
            @Valid @ModelAttribute NameDTO dto,
            BindingResult bindingResult,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // 1. Validar cabecera
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageDTO<>(true, "Token de autorización requerido"));
        }

        String token = authHeader.substring(7);
        Claims claims;

        try {
            claims = jwtUtil.validateToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageDTO<>(true, "Token inválido: " + e.getMessage()));
        }

        // 2. Expiración
        if (jwtUtil.isTokenExpired(claims)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageDTO<>(true, "Token expirado"));
        }

        // 3. Nombre coincidente
        String tokenName = claims.getSubject();
        if (!dto.getName().equals(tokenName)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDTO<>(true, "El nombre no coincide con el token"));
        }

        // 4. Validación de campos
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageDTO<>(true, "La solicitud es inválida, nombre requerido"));
        }

        // ✅ Todo OK
        return ResponseEntity.ok(saludoService.getSaludo(dto));
    }
}
