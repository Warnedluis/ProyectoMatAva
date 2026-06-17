package com.UPIIZ.ProyectoMatAva.Controllers;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.UPIIZ.ProyectoMatAva.Entities.FourierRequest;

@Controller
@CrossOrigin(origins = "*") // Permite la conexión desde tu HTML
public class FourierController {

    @GetMapping("/getCalculadoraFourier")
    public String getCalculadoraFourier()
    {
        return "CalculadoraFourier";
    }

    @PostMapping("/Fourier/api/Fourier")
    public ResponseEntity<Map<String,Object>> calcularSerie(@RequestBody FourierRequest request)
    {
        String urlPython = "http://localhost:5000/calcular";
        RestTemplate restTemplate = new RestTemplate();

        try
        {
            ResponseEntity<Map> respuestaPython = restTemplate.postForEntity(urlPython, request, Map.class);

            return ResponseEntity.ok(respuestaPython.getBody());
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(Map.of("error", "Fallo al conectar"));
        }
    }



    
}
