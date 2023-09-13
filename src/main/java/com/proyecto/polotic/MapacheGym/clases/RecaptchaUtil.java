package com.proyecto.polotic.MapacheGym.clases;

import java.util.*;



public class RecaptchaUtil {
    public static final Map<String, String> RECAPTCHA_ERROR_CODE = new HashMap<>();

    static{
        RECAPTCHA_ERROR_CODE.put("missing-input-secret", "Falta el parametro secret");
        RECAPTCHA_ERROR_CODE.put("invalid-input-secret", "El parametro secret no es valido o no esta bien formado");
        RECAPTCHA_ERROR_CODE.put("missing-input-response", "Falta la respuesta");
        RECAPTCHA_ERROR_CODE.put("invalid-input-response", "La respuesta no es valida o no esta bien formada");
        RECAPTCHA_ERROR_CODE.put("bad-request","La peticion no es valida o no esta bien formada");


    }



}



