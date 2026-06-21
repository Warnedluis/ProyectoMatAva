package com.UPIIZ.ProyectoMatAva.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.UPIIZ.ProyectoMatAva.Entities.FourierRequest;

@Controller
public class FourierController {

    @GetMapping("/getCalculadoraFourier")
    public String getCalculadoraFourier() {
        return "CalculadoraFourier";
    }

    @PostMapping("/Fourier/api/Fourier")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> calcularSerie(@RequestBody FourierRequest request) {
        String urlPython = "https://proyectomatava-fourier.onrender.com/calcularFourier";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> payload = new HashMap<>();
        payload.put("Funcion", request.getFuncion());
        payload.put("PeriodoInicial", request.getPeriodoInicial());
        payload.put("PeriodoFinal", request.getPeriodoFinal());
        payload.put("Armonicos", request.getArmonicos());

        try {
            ResponseEntity<Map> respuestaPython = restTemplate.postForEntity(urlPython, payload, Map.class);

            if (respuestaPython.getStatusCode().is2xxSuccessful() && respuestaPython.getBody() != null) {
                return ResponseEntity.ok(respuestaPython.getBody());
            }

            return ResponseEntity.status(respuestaPython.getStatusCode()).body(respuestaPython.getBody());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Fallo al conectar con el servicio de Fourier"));
        }
    }
}
