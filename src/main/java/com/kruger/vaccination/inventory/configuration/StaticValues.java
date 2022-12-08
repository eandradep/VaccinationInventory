package com.kruger.vaccination.inventory.configuration;

public class StaticValues {

    //    RESPONSE MESSAGE TITTLES
    public static final String MESSAGE_VALUE = "message";
    public static final String ERROR_VALUE = "error";
    public static final String RESPONSE_VALUE = "response";
    //    SUCCESS MESSAGES
    public static final String MESSAGE_VALUE_DATA_FOUND = "Los datos solicitados han sido encontrados en la base de datos.";
    public static final String MESSAGE_VALUE_DATA_SAVE = "La información proporcionada ha sido almacenada correctamente.";
    //    ERRORS MESSAGES
    public static final String MESSAGE_VALUE_INTERNAL_DUPLICATE_KEY = "Error, la cédula del empleado ya esta registrado en el sistema";
    public static final String MESSAGE_VALUE_INTERNAL_SERVER_ERROR = "Error en el servidor, contactar con soporte técnico.";
    public static final String MESSAGE_VALUE_DATA_NOT_FOUND = "No se han encontrado registros en la base de datos";
    public static final String MESSAGE_VALUE_BAD_REQUEST = "Se han encontrado errores en el objeto, por favor considera las siguientes validaciones";

    private StaticValues() {
    }

}
