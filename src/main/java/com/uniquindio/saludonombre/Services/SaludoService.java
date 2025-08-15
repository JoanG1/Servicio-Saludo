package com.uniquindio.saludonombre.Services;

import com.uniquindio.saludonombre.dto.MessageDTO;
import com.uniquindio.saludonombre.dto.NameDTO;

public interface SaludoService {

    MessageDTO<String> getSaludo(NameDTO dto);
}
