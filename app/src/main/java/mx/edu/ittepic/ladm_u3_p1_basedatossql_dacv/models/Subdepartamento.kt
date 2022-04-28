package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos.BaseDatos

class Subdepartamento(context: Context) {
    var idSubdepto = ""
    var idEdificio = ""
    var piso = 0
    var idArea = 0

    private var context = context
    private var err = ""


    override fun toString(): String {
        return idSubdepto +", "+idEdificio+", "+piso+", "+idArea
    }


    fun insertar(): Boolean {
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS", null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            val datos = ContentValues()

            datos.put("IDEDIFICIO", idEdificio)
            datos.put("PISO", piso)
            datos.put("IDAREA", idArea)

            val resultado = tabla.insert("SUBDEPARTAMENTO", null, datos)
            if (resultado == -1L)

                return false
        } catch (err: SQLiteException) {

            this.err = err.message.toString()
            return false
        } finally {
            baseDatos.close()
        }

        return true
    }

    fun obtenerSubdepto() : ArrayList<Subdepartamento>{
        val arreglo = ArrayList<Subdepartamento>()
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)

        err = ""
        try{
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM SUBDEPARTAMENTO"

            val cursor = tabla.rawQuery(SQL_SELECT,null)

            if(cursor.moveToFirst()){
                do {
                    val subDep = Subdepartamento(context)

                    Log.i("Select: ",cursor.toString())
                    subDep.idSubdepto = cursor.getString(0)
                    subDep.idEdificio = cursor.getString(1)
                    subDep.idArea = cursor.getInt(2)
                    subDep.piso = cursor.getInt(3)

                    arreglo.add(subDep)
                }while (cursor.moveToNext())
            }
        }catch (err:SQLiteException){
            this.err = err.message.toString()
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

}