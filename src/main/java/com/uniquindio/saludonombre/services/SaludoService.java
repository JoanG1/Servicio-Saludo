package com.uniquindio.saludonombre.services;

import com.uniquindio.saludonombre.dto.MessageDTO;
import com.uniquindio.saludonombre.dto.NameDTO;

public interface SaludoService {

    MessageDTO<String> getSaludo(NameDTO dto);
}
