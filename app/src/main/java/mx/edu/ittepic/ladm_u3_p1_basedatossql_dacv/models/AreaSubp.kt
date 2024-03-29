package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos.BaseDatos
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils

class AreaSubp (context: Context){
    /************************************
     * DANIEL ALEJANDRO CALDERÓN VIGREN *
     ************************************/

    var idArea = ""
    var descripcion = ""
    var division = ""
    var cantidadEmpleados = 0
    var idSubdepto = ""
    var idEdificio = ""
    var piso = 0

    private var context = context
    private var err = ""

    override fun toString(): String {
        return "idArea " + idArea +" ,"+"descripcion " + descripcion +" ,"+"division " +
                division  +" ,"+"idSubdepto " + idSubdepto +" ,"+"idEdificio " + idEdificio +" ,"+"piso " + piso
    }

    fun obtenerSubdepto() : ArrayList<AreaSubp>{
        val arreglo = ArrayList<AreaSubp>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try{
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "${Utils.SUBDEPARTAMENTO} S "+
                    "INNER JOIN ${Utils.AREA} A "+
                    "ON S.${Utils.IDAREA} = A.${Utils.IDAREA}"

            val cursor = tabla.query(SQL_SELECT,null,null,
                null,null,null,null)

            if(cursor.moveToFirst()){
                do {
                    val subDep = AreaSubp(context)
                    subDep.idSubdepto = cursor.getString(0)
                    subDep.idEdificio = cursor.getString(1)
                    subDep.piso = cursor.getInt(2)
                    subDep.idArea = cursor.getString(3)
                    subDep.descripcion = cursor.getString(5)
                    subDep.division = cursor.getString(6)
                    subDep.cantidadEmpleados = cursor.getInt(7)

                    arreglo.add(subDep)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message.toString()
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

    fun obtenerSubdepto(opc: String, valor: String): ArrayList<AreaSubp>{
        val arreglo = ArrayList<AreaSubp>()
        val baseDatos = BaseDatos(context, Utils.BD_NAME, null, 1)

        err = ""
        try{
            val tabla = baseDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM ${Utils.SUBDEPARTAMENTO} S "+
                    "INNER JOIN ${Utils.AREA} A "+
                    "ON S.${Utils.IDAREA} = A.${Utils.IDAREA} " +
                    "WHERE ${opc}=?"

            Log.i("INNER JOIN",SQL_SELECT+" "+valor)
            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(valor))

            if(cursor.moveToFirst()){
                do {
                    val subDep = AreaSubp(context)
                    subDep.idSubdepto = cursor.getString(0)
                    subDep.idEdificio = cursor.getString(1)
                    subDep.piso = cursor.getInt(2)
                    subDep.idArea = cursor.getString(3)
                    subDep.descripcion = cursor.getString(5)
                    subDep.division = cursor.getString(6)
                    subDep.cantidadEmpleados = cursor.getInt(7)

                    arreglo.add(subDep)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message.toString()
        }finally {
            baseDatos.close()
        }
        return arreglo
    }
}