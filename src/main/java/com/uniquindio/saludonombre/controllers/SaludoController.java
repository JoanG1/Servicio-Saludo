package com.uniquindio.saludonombre.controllers;


import com.uniquindio.saludonombre.dto.MessageDTO;
import com.uniquindio.saludonombre.dto.NameDTO;
import com.uniquindio.saludonombre.services.SaludoService;
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

    @GetMapping("/saludo")
    public ResponseEntity<MessageDTO<String>> generarSaludo(@Valid @ModelAttribute NameDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageDTO<>(true, "La solicitud es inválida, nombre requerido"));
        }

        return ResponseEntity.ok(saludoService.getSaludo(dto));
    }

}
