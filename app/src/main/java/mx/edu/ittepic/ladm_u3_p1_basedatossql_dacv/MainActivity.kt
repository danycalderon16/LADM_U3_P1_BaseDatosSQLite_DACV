package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.basedatos.BaseDatos
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.databinding.ActivityMainBinding
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView



        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_list, R.id.navigation_subdepartamentos, R.id.navigation_insert
                //,R.id.navigation_modify
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun insertar(): Boolean {
        val baseDatos = BaseDatos(this, Utils.BD_NAME, null, 1)
        var err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datosActualizados = ContentValues()

           var resul = tabla.execSQL("UPDATE ${Utils.SUBDEPARTAMENTO} SET ${Utils.IDAREA} = 1" +
                   " WHERE ${Utils.IDSUBDEPTO} = 1" )

           resul
        } catch (er: SQLiteException) {
            err = er.message.toString()
            return false
        } finally {
            baseDatos.close()
        }
        return true
    }
}