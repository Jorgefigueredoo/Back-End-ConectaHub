package com.conectahub.conectahub_api.model;

// Este Enum será usado tanto no Envio quanto no Histórico
public enum StatusEnvio {
    CRIADO,
    EM_TRANSITO,
    ENTREGUE,
    CONFIRMADO // (Confirmado pelo agricultor no app)
}