package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos.BaseDatos
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils

/**
 * RECLUTAMIENTO RH
 * CAPACITACION RH
 * COBRANZA FNZ
 * NOMINA FNZ
 *
 * */
class Area(context: Context) {
    /************************************
     * DANIEL ALEJANDRO CALDERÓN VIGREN *
     ************************************/
    var idArea = ""
    var descripcion = ""
    var division = ""
    var cantidadEmpleados = 0

    private var context = context
    private var err = ""

    override fun toString(): String {
        return "idArea: "+idArea + ", " +"DEscrp: "+ descripcion + ", " +"div: "+ division + ", " + "cantEmp: "+cantidadEmpleados
    }

    fun insertar(): Boolean {
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            val datos = ContentValues()

            datos.put(Utils.DESCRIPCION, descripcion)
            datos.put(Utils.DIVISION, division)
            datos.put(Utils.CANTIDAD_EMPLEADOS, cantidadEmpleados)

            val resultado = tabla.insert(Utils.AREA, null, datos)

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

    fun obtenerAreas(): ArrayList<Area> {
        val arreglo = ArrayList<Area>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ${Utils.AREA}"

            val cursor = tabla.rawQuery(SQL_SELECT, null)

            if (cursor.moveToFirst()) {
                do {
                    val area = Area(context)
                    area.idArea = cursor.getString(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidadEmpleados = cursor.getInt(3)

                    arreglo.add(area)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun obtenerAreasWhere(opc: String, valor: String): ArrayList<Area> {
        val arreglo = ArrayList<Area>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ${Utils.AREA} WHERE ${opc}=?"

            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(valor))

            if (cursor.moveToFirst()) {
                do {
                    val area = Area(context)
                    area.idArea = cursor.getString(0)
                    area.descripcion = cursor.getString(1)
                    area.division = cursor.getString(2)
                    area.cantidadEmpleados = cursor.getInt(3)

                    arreglo.add(area)
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun obtenerDepartamentos(): ArrayList<String> {
        val areas = ArrayList<String>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT ${Utils.DESCRIPCION} FROM AREA"

            val cursor = tabla.rawQuery(SQL_SELECT, null)

            if (cursor.moveToFirst()) {
                do {
                    areas.add(cursor.getString(0))
                } while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return areas
    }

    fun obtenerIdArea(descripcionRec: String): Int {
        var idArea = -1
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT ${Utils.IDAREA} FROM ${Utils.AREA} WHERE ${Utils.DESCRIPCION}=? "

            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(descripcionRec))

            if (cursor.moveToFirst()) {
                idArea = cursor.getInt(0)
            }
        } catch (err: SQLiteException) {
            this.err = err.message.toString()
        } finally {
            baseDatos.close()
        }
        return idArea
    }

    fun actualizar(): Boolean {
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datosActualizados = ContentValues()

            datosActualizados.put(Utils.DESCRIPCION, descripcion)
            datosActualizados.put(Utils.DIVISION, division)
            datosActualizados.put(Utils.CANTIDAD_EMPLEADOS, cantidadEmpleados)

            val respuesta = tabla.update(
                Utils.AREA, datosActualizados,
                "${Utils.IDAREA}=?", arrayOf(idArea)
            )

            if (respuesta == 0) {
                return false
            }

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
            var resultado = tabla.delete(Utils.AREA, "${Utils.IDAREA}=?", arrayOf(idArea))

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