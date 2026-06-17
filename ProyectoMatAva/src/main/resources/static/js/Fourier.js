function calcular()
{
    document.getElementById("Calcular").click(function(event){
        event.preventDefault();
    })

    const peticionDatos = {
        function : document.getElementById('Funcion').value,
        periodoInicial : document.getElementById('PeriodoInicial').value,
        periodoFinal : document.getElementById('PeriodoFinal').value,
        armonicos : document.getElementById('Armonicos').value
    };

    $.ajax({
        url : 'http://localhost:8080/Fourier/Api/Fourier',
        method : 'POST',
        contentType : 'application/json',
        data : JSON.stringify(peticionDatos),

        success : function(resultado)
        {
            console.log("Respuesta",resultado);

            if(resultado.mensaje)
            {
                alert(resultado.mensaje);
            }
        }
    })


    
}