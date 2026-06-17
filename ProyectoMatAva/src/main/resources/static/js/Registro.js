function Validar()
{
    var password1 = document.getElementById("Password").value;
    var password2 = document.getElementById("Password2").value;

    if(password1 == password2)
    {
        alert("Contrasenia funcionan correctamente, pasa amigo");
    }
    else
    {
        alert("Las contraseñas no son las mismas, coloque la misma contraseña por favor");
    }
}