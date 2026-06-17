package com.UPIIZ.ProyectoMatAva.Controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class ComplejosController
{

    @GetMapping("/getCalculadoraComplejos")
    public String getCalculadoraComplejos()
    {
        return "CalculadoraComplejos";
    }


    @PostMapping("/Complejos/api/Complejos")
    @ResponseBody
    public ResponseEntity<Map> calcularComplejos(@RequestBody Map<String, String> request)
    {
        String urlPython = "https://proyectomatava.onrender.com/calcularComplejos";
        RestTemplate restTemplate = new RestTemplate();

        try
        {
            ResponseEntity<Map> respuestaPython = restTemplate.postForEntity(urlPython, request, Map.class);

            return ResponseEntity.ok(respuestaPython.getBody());
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(Map.of("error", "fallo al conectarse"));
            }
        }
}
