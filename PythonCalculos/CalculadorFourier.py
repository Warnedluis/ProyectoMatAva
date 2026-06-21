import numpy as np
from flask import Flask, request, jsonify
import os
import sympy as sp
import traceback

app = Flask(__name__)

@app.route('/calcularFourier', methods=['POST'])
def obtencionDatos():
    datos = request.json or {}

    function_str = datos.get('funcion', datos.get('Funcion', ''))
    str_a = str(datos.get('periodoInicial', datos.get('PeriodoInicial', '-2')))
    str_b = str(datos.get('periodoFinal', datos.get('PeriodoFinal', '2')))
    str_n = str(datos.get('armonicos', datos.get('Armonicos', '10')))

    try:

        a = sp.nsimplify(sp.sympify(str_a))
        b = sp.nsimplify(sp.sympify(str_b))
        n_terms = int(str_n)
        
    except Exception:
        return jsonify({"error": "Los valores numéricos enviados son inválidos"}), 400

    T = sp.simplify(b - a)
    if T == 0:
        return jsonify({"error": "El periodo inicial y final no pueden ser iguales"}), 400

    L = sp.simplify(T / 2)
    
    # 1. Definimos nuestra 't' maestra
    t = sp.Symbol('t', real=True)

    try:

        f_sym = sp.sympify(function_str, locals={'t': t})

    except Exception:
        return jsonify({"error": "Error en la sintaxis de la función"}), 400

    # Escudo protector: Evitar que usen letras que no sean 't' (como 'x' o 'y')
    if f_sym.free_symbols - {t}:
        return jsonify({"error": "La función debe estar escrita únicamente usando la letra 't'"}), 400

    try:

        a0_sym = sp.integrate(f_sym, (t, a, b)) / L
        val_a0_exacto = str(sp.simplify(a0_sym))

        coef_an_exactos = []
        coef_bn_exactos = []
        coef_an_num = []
        coef_bn_num = []

        for n in range(1, n_terms + 1):
            
            an_sym = sp.integrate(f_sym * sp.cos(n * sp.pi * t / L), (t, a, b)) / L
            bn_sym = sp.integrate(f_sym * sp.sin(n * sp.pi * t / L), (t, a, b)) / L

            an_simp = sp.simplify(an_sym)
            bn_simp = sp.simplify(bn_sym)

            coef_an_exactos.append(str(an_simp))
            coef_bn_exactos.append(str(bn_simp))
            
            coef_an_num.append(float(sp.re(an_simp.evalf())))
            coef_bn_num.append(float(sp.re(bn_simp.evalf())))


        val_a0_num = float(sp.re(a0_sym.evalf()))
        a_num = float(a.evalf())
        b_num = float(b.evalf())
        L_num = float(L.evalf())

        t_vals = np.linspace(a_num, b_num, 100).tolist()
        y_vals = []

        for t_val in t_vals:
            suma = val_a0_num / 2.0
            for i in range(n_terms):
                n_actual = i + 1
                suma += coef_an_num[i] * np.cos(n_actual * np.pi * t_val / L_num) + coef_bn_num[i] * np.sin(n_actual * np.pi * t_val / L_num)
            y_vals.append(float(suma))

        resultado = {

            "a0": val_a0_exacto,
            "an": coef_an_exactos,
            "bn": coef_bn_exactos,
            "t_grafica": t_vals,
            "y_grafica": y_vals

        }

        return jsonify({"resultado": resultado}), 200

    except Exception as e:
        print("\n--- ERROR DETALLADO DE SYMPY ---")
        traceback.print_exc()
        print("--------------------------------\n")
        return jsonify({"error": f"No se pudo evaluar matemáticamente: {str(e)}"}), 500

if __name__ == "__main__":
    puerto = int(os.environ.get('PORT', 5002))
    app.run(host='0.0.0.0', port=puerto, debug=True)