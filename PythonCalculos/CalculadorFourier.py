from flask import Flask, request, jsonify
import os

app = Flask(__name__)

@app.route('/calcular', methods =['POST'])

def calcular():
    
    datos = request.json

    function = datos.get('funcion')
    periodo_inicial = datos.get('periodoInicial')
    periodo_final = datos.get('periodoFInal')
    armonicos = datos.get('armonicos');

    datos_calculados = [0.0,1.0,3.0]

    respuesta ={
       "estatis" : "exito",
       "mensaje" : "Calculo realizado",
       "resultados" : datos_calculados 
    }

    return jsonify(respuesta)

if __name__ == '__main__':
    puerto = int(os.environ.get('PORT',5001))

    app.run(host = '0.0.0.0', port = puerto)