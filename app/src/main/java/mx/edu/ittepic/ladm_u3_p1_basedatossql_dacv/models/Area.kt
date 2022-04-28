package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos.BaseDatos

/**
 * RECLUTAMIENTO RH
 * CAPACITACION RH
 * COBRANZA FNZ
 * NOMINA FNZ
 *
 * */
class Area (context: Context){
    var idArea = ""
    var descripcion = ""
    var division = ""
    var cantidadEmpleados = 0

    private var context = context
    private var err = ""

    override fun toString(): String {
        return idArea +", "+descripcion+", "+division+", "+cantidadEmpleados
    }

    fun insertar(): Boolean {
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)
        err = ""
        try {
            val tabla = baseDatos.writableDatabase

            val datos = ContentValues()

            datos.put("DESCRIPCION",descripcion)
            datos.put("DIVISION",division)
            datos.put("CANTIDAD_EMPLEADOS",cantidadEmpleados)

            val resultado = tabla.insert("AREA",null,datos)

            if (resultado == -1L)
                return false

        }catch (err:SQLiteException){
            this.err = err.message.toString()
            return false
        }finally {
            baseDatos.close()
        }

        return true
    }

    fun obtenerAreas(): ArrayList<Area> {
        val arreglo = ArrayList<Area>()
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS", null, 1)

        err = ""
        try {
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA"

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

     fun obtenerDepartamentos() : ArrayList<String>{
            val areas = ArrayList<String>()
            val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)

            err = ""
            try{
                val tabla = baseDatos.readableDatabase
                val SQL_SELECT = "SELECT DESCRIPCION FROM AREA"

                val cursor = tabla.rawQuery(SQL_SELECT,null)

                if(cursor.moveToFirst()){
                    do {
                        areas.add(cursor.getString(0))
                    }while (cursor.moveToNext())
                }
            }catch (err:SQLiteException){
                this.err = err.message.toString()
            }finally {
                baseDatos.close()
            }
            return areas
        }

    fun obtenerArea(idArea:Int) : Area{
        val area = Area(context)
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)

        err = ""
        try{
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM AREA WHERE IDAREA=? "

            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(idArea.toString()))

            if(cursor.moveToFirst()){
                area.idArea = cursor.getString(0)
                area.descripcion = cursor.getString(1)
                area.division = cursor.getString(2)
                area.cantidadEmpleados = cursor.getInt(3)
            }
        }catch (err:SQLiteException){
            this.err = err.message.toString()
        }finally {
            baseDatos.close()
        }
        return area
    }

    fun obtenerIdArea(descripcionRec:String) : Int{
        var idArea = -1
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)

        err = ""
        try{
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT IDAREA FROM AREA WHERE DESCRIPCION=? "

            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(descripcionRec))

            if(cursor.moveToFirst()){
                idArea = cursor.getInt(0)
            }
        }catch (err:SQLiteException){
            this.err = err.message.toString()
        }finally {
            baseDatos.close()
        }
        return idArea
    }

    fun actualizar() : Boolean{
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datosActualizados = ContentValues()

            datosActualizados.put("DESCRIPCION", descripcion)
            datosActualizados.put("DIVISION", division)
            datosActualizados.put("CANTIDAD_EMPLEADOS", cantidadEmpleados)

            val respuesta = tabla.update("AREA",datosActualizados,
                "IDAREA=?", arrayOf(idArea))

            if(respuesta==0){
                return false
            }

        }catch (err:SQLiteException){
            this.err = err.message.toString()
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun eliminar() : Boolean{
        val baseDatos = BaseDatos(context, "MAPEO_EMPRESAS",null,1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var resultado = tabla.delete("AREA","IDAREA=?", arrayOf(idArea))

            if(resultado == 0)
                return false
        }catch (err:SQLiteException){
            this.err = err.message.toString()
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

}