const Funcion = document.getElementById('Funcion');
const PeriodoInicial = document.getElementById('PeriodoInicial');
const PeriodoFinal = document.getElementById('PeriodoFinal');
const Armonicos = document.getElementById('Armonicos');
const resultadoContainer = document.getElementById('resultadoContainer');
const resultadoMensaje = document.getElementById('resultadoMensaje');
const resultadoTablaBody = document.getElementById('resultadoTablaBody');

// Variable para controlar la gráfica
let miGraficaFourier = null;

function mostrarMensaje(tipo, mensaje) {
    resultadoContainer.classList.remove('d-none');
    resultadoMensaje.className = `alert alert-${tipo === 'error' ? 'danger' : 'success'}`;
    resultadoMensaje.textContent = mensaje;
}

function limpiarResultado() {
    resultadoTablaBody.innerHTML = '';
}

// NUEVA FUNCIÓN: Dibuja la gráfica con los datos de Python
function dibujarGrafica(t_datos, y_datos) {
    const ctx = document.getElementById('graficaFourier').getContext('2d');

    if (miGraficaFourier) {
        miGraficaFourier.destroy(); // Borra la anterior si existe
    }

    miGraficaFourier = new Chart(ctx, {
        type: 'line',
        data: {
            labels: t_datos.map(t => parseFloat(t).toFixed(2)), 
            datasets: [{
                label: 'Serie de Fourier f(t)',
                data: y_datos,
                borderColor: '#28a745',
                backgroundColor: 'rgba(40, 167, 69, 0.2)',
                borderWidth: 2,
                pointRadius: 0,
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            scales: {
                x: { title: { display: true, text: 'Tiempo (t)' } },
                y: { title: { display: true, text: 'Amplitud' } }
            }
        }
    });
}

function mostrarResultado(data) {
    limpiarResultado();
    resultadoContainer.classList.remove('d-none');
    resultadoMensaje.className = 'alert alert-success';
    resultadoMensaje.textContent = 'Cálculo realizado correctamente.';

    const resultado = data.resultado || data;

    // Llenamos la tabla SIN usar .toFixed() para respetar el texto exacto (ej. -2/pi)
    if (resultado.a0 !== undefined) {
        const filaA0 = document.createElement('tr');
        filaA0.innerHTML = `<td>a₀</td><td>${resultado.a0}</td>`;
        resultadoTablaBody.appendChild(filaA0);
    }

    if (Array.isArray(resultado.an) && Array.isArray(resultado.bn)) {
        const max = Math.max(resultado.an.length, resultado.bn.length);
        for (let i = 0; i < max; i++) {
            const fila = document.createElement('tr');
            const n = i + 1;
            fila.innerHTML = `
                <td>a${n} / b${n}</td>
                <td>${resultado.an[i] !== undefined ? resultado.an[i] : '—'} / ${resultado.bn[i] !== undefined ? resultado.bn[i] : '—'}</td>
            `;
            resultadoTablaBody.appendChild(fila);
        }
    }

    // Dibujamos la gráfica
    if (resultado.t_grafica && resultado.y_grafica) {
        dibujarGrafica(resultado.t_grafica, resultado.y_grafica);
    }
}

function calcular() {
    const funcion = Funcion.value.trim();
    const periodoInicial = PeriodoInicial.value.trim();
    const periodoFinal = PeriodoFinal.value.trim();
    const armonicos = Armonicos.value.trim();

    if (!funcion || !periodoInicial || !periodoFinal || !armonicos) {
        mostrarMensaje('error', 'Completa todos los campos para calcular la serie.');
        return;
    }

    // Nombres en minúscula para que coincidan con tu backend en Java
    const peticionDatos = {
        funcion: funcion,
        periodoInicial: parseFloat(periodoInicial),
        periodoFinal: parseFloat(periodoFinal),
        armonicos: parseInt(armonicos, 10)
    };

    if (Number.isNaN(peticionDatos.periodoInicial) || Number.isNaN(peticionDatos.periodoFinal) || Number.isNaN(peticionDatos.armonicos)) {
        mostrarMensaje('error', 'Los campos numéricos deben ser válidos.');
        return;
    }

    $.ajax({
        url: "/Fourier/api/Fourier",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(peticionDatos),
        success: function (respuesta) {
            if (respuesta && respuesta.error) {
                mostrarMensaje('error', respuesta.error);
                return;
            }
            if (respuesta && respuesta.resultado) {
                mostrarResultado(respuesta);
            } else {
                mostrarMensaje('error', 'La respuesta del servidor no tiene el formato esperado.');
            }
        },
        error: function (xhr) {
            const mensaje = xhr.responseJSON && xhr.responseJSON.error
                ? xhr.responseJSON.error
                : 'No se pudo completar la petición.';
            mostrarMensaje('error', mensaje);
        }
    });
}