package com.kindsu.myapplication

class Pastillas (){
    var lstPastillas: MutableMap<String, MutableList<String>> = inicializarLista()

    fun inicializarLista() : MutableMap<String, MutableList<String>>{
        return mutableMapOf(
            "14/11/2024" to mutableListOf("Tirodrill", "Furosemida", "Ferbisol"),
            "10/11/2024" to mutableListOf("Omeprazol", "Trajenta", "Apixaban"),
            "11/11/2024" to mutableListOf("Bisoprolol", "Loxartan", "Alodipino")
        )

    }

    fun addNuevaPastilla(fecha: String, nombrePasti: String) {
        if (lstPastillas.containsKey(fecha)) {
            lstPastillas[fecha]?.add(nombrePasti)
        } else {
            lstPastillas[fecha] = mutableListOf(nombrePasti)
        }
    }
}