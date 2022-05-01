package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils

class BaseDatos(context: Context?,
                name: String?,
                factory: SQLiteDatabase.CursorFactory?,
                version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    /************************************
     * DANIEL ALEJANDRO CALDERÓN VIGREN *
     ************************************/
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ${Utils.AREA}(" +
                "${Utils.IDAREA} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Utils.DESCRIPCION} VARCHAR(200), " +
                "${Utils.DIVISION} VARCHAR(50)," +
                "${Utils.CANTIDAD_EMPLEADOS} INTEGER)")
        db.execSQL("CREATE TABLE ${Utils.SUBDEPARTAMENTO}(" +
                "${Utils.IDSUBDEPTO} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Utils.IDEDIFICIO} VARCHAR(20), " +
                "${Utils.PISO} VARCHAR(50)," +
                "${Utils.IDAREA} INTEGER," +
                "FOREIGN KEY (${Utils.IDAREA}) REFERENCES ${Utils.AREA} (${Utils.IDAREA}))")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}