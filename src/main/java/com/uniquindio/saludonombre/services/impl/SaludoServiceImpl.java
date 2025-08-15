package com.uniquindio.saludonombre.services.impl;

import com.uniquindio.saludonombre.dto.MessageDTO;
import com.uniquindio.saludonombre.dto.NameDTO;
import com.uniquindio.saludonombre.services.SaludoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaludoServiceImpl implements SaludoService {

    @Override
    public MessageDTO<String> getSaludo(NameDTO dto) {
        if(!dto.getName().isEmpty()){
            return new MessageDTO<>(false,"Hola "+dto.getName());
        }else{
            return new MessageDTO<>(true,"El nombre no puede estar vacío");
        }
    }
}
