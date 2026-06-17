package com.UPIIZ.ProyectoMatAva.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.UPIIZ.ProyectoMatAva.Entities.usuarioEntity;
import com.UPIIZ.ProyectoMatAva.Services.UsuarioServiceImpl;

@RequestMapping("/auth")
@Controller
public class authController {


    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;
    //CRUD

    //C Create user 

    //Mandar traer la pagina con un bosquejo para el registro 

    @GetMapping("/getInicioSesion")
    public String getInicio()
    {
        return "authInicioSesion";
    }

    
    @GetMapping("/getRegistro")
    public String getRegistro(Model model)
    {
        usuarioEntity usuario = new usuarioEntity();
        model.addAttribute("usuario",usuario);
        return "authRegistroUsuario";

    }

    //Mandar los datos a la base de datos
    @PostMapping("/Registro")
    public String postRegistro(@ModelAttribute usuarioEntity usuario)
    {
        usuarioServiceImpl.RegistrarUsuario(usuario);
        return "authInicioSesion";
    }

    //Read Leer al usuario creado con el respectivo login

    
    
}
