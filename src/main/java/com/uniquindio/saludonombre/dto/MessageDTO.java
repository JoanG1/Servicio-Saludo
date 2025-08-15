package com.uniquindio.saludonombre.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO<T> {
    private boolean error;
    private T message;
}
