package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos.BaseDatos
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils.*

class Subdepartamento(context: Context) {
    var idSubdepto = ""
    var idEdificio = ""
    var piso = 0
    var idArea = 0

    private var context = context
    private var err = ""

    override fun toString(): String {
        return "IDsubd: "+ idSubdepto + ", " +"IDEdif: "+ idEdificio +
                ", " +"Piso: "+ piso + ", " + "IDarea: "+idArea
    }

    fun insertar(): Boolean {
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            val datos = ContentValues()

            datos.put(Utils.IDEDIFICIO, idEdificio)
            datos.put(Utils.PISO, piso)
            datos.put(Utils.IDAREA, idArea)

            val resultado = tabla.insert(Utils.SUBDEPARTAMENTO, null, datos)
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

    fun obtenerSubdepto(): ArrayList<Subdepartamento> {
        val arreglo = ArrayList<Subdepartamento>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ${Utils.SUBDEPARTAMENTO}"

            val cursor = tabla.rawQuery(SQL_SELECT, null)

            if (cursor.moveToFirst()) {
                do {
                    val subDep = Subdepartamento(context)
                    subDep.idSubdepto = cursor.getString(0)
                    subDep.idEdificio = cursor.getString(1)
                    subDep.piso = cursor.getInt(2)
                    subDep.idArea = cursor.getInt(3)

                    arreglo.add(subDep)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun obtenerSubdepto(id: String): Subdepartamento {
        val subDep = Subdepartamento(context)
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ${Utils.SUBDEPARTAMENTO} " +
                    "WHERE ${Utils.IDSUBDEPTO}=?"

            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(id))

            if (cursor.moveToFirst()) {
                subDep.idSubdepto = cursor.getString(0)
                subDep.idEdificio = cursor.getString(1)
                subDep.piso = cursor.getInt(2)
                subDep.idArea = cursor.getInt(3)
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return subDep
    }

    fun obtenerSubdepWhere(opc: String, valor: String): ArrayList<Subdepartamento> {
        val arreglo = ArrayList<Subdepartamento>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ${Utils.SUBDEPARTAMENTO} WHERE ${opc}=?"

            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(valor))

            if (cursor.moveToFirst()) {
                do {
                    val subd = Subdepartamento(context)
                    subd.idSubdepto = cursor.getString(0)
                    subd.idEdificio = cursor.getString(1)
                    subd.piso = cursor.getInt(2)
                    subd.idArea = cursor.getInt(3)

                    arreglo.add(subd)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun actualizar(): Boolean {
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datosActualizados = ContentValues()

            datosActualizados.put(Utils.IDEDIFICIO, idEdificio)
            datosActualizados.put(Utils.PISO, piso)
            datosActualizados.put(Utils.IDAREA, idArea)

            val respuesta = tabla.update(
                Utils.SUBDEPARTAMENTO, datosActualizados,
                "${Utils.IDSUBDEPTO}=?", arrayOf(idSubdepto)
            )

            if (respuesta == 0)
                return false

        } catch (err: SQLiteException) {
            this.err = err.message.toString()
            return false
        } finally {
            baseDatos.close()
        }
        return true
    }

    fun eliminar(): Boolean {
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var resultado =
                tabla.delete(Utils.SUBDEPARTAMENTO, "${Utils.IDSUBDEPTO}=?", arrayOf(idSubdepto))

            if (resultado == 0)
                return false
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
            return false
        } finally {
            baseDatos.close()
        }
        return true
    }

}