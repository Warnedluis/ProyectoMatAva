const pantalla = document.getElementById('pantalla');

function agregarValor(valor)
{
    if (valor == '.')
    {
        const ecuacion = pantalla.value;

        const partes = ecuacion.split(/[\+\-\*\/\(\)]/);

        const numeroActual = partes[partes.length-1];

        if(numeroActual.includes('.'))
        {
            return;
        }
    }
    pantalla.value += valor;
}

function limpiarPantalla()
{
    pantalla.value = '';
}

function borrarUltimo()
{
    pantalla.value = pantalla.value.slice(0,-1);
}

function calcular()
{

    if(!pantalla.value) return;

    const peticionDatos={
        pantalla : pantalla.value
    };

    $.ajax({
        url : "http://localhost:8080/Complejos/api/Complejos",
        method : "POST",
        contentType : "application/json",
        data : JSON.stringify(peticionDatos),

        success : function(respuesta)
        {
            console.log("Respuestas del servido:" , respuesta);

            if(respuesta.resultado)
            {
                pantalla.value = respuesta.resultado;
            }
        },
        error: function(xhr)
        {
            console.error("Fallo la peticion", xhr.responseText);

            document.getElementById("pantalla").value = "Error";
            
        }
    })
}

