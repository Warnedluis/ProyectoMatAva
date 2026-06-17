from flask import Flask, request, jsonify
import sys
import os


app = Flask(__name__)

@app.route('/calcularComplejos', methods = ['POST'])

def obtencionDatos():

    datos = request.json

    ecuacion = datos.get('pantalla', '')

    resultado, error = calcular_expresion(ecuacion)

    if error: 
        return jsonify({"error":error}), 400
    else:
        return jsonify({"resultado": resultado}), 200
    


#Funcionamiento de la logica matematica
def calcular_expresion(expresion):
    
    try :
        
        exp_python = expresion.replace('i','j')


        ent_seg = {"__builtins__": {}}
        variables = {"j":1j}

        resultado = eval(exp_python, ent_seg, variables)

        res = str(resultado).replace('j', 'i')

        res = res.strip('()')

        return res,None
    except ZeroDivisionError :
        return None, "Error por division entre 0"
    except Exception as e:
        return None, "Error matematico"


if __name__ == "__main__":
    puerto = int(os.environ.get('PORT', 5001))

    app.run(host = '0.0.0.0',port = puerto)
