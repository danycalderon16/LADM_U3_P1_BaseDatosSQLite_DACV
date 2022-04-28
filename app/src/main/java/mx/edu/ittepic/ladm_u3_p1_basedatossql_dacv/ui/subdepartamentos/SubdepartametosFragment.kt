package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.subdepartamentos

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.databinding.FragmentSubdepartamentosBinding
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Area
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.AreaSubp
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Subdepartamento
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.list.ListViewModel
import java.util.ArrayList

class SubdepartametosFragment : Fragment() {
    private var array = arrayListOf<String>()

    private var _binding: FragmentSubdepartamentosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var areaSubp = ArrayList<AreaSubp>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)

        _binding = FragmentSubdepartamentosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = binding.spinnerSubdep

        array.add("Seleccione una opción")
        array.add("Edificio")
        array.add("Area")
        array.add("Divisón")

        val aa = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, array)
        aa.setNotifyOnChange(true)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa

        val rv = binding.rvList

        areaSubp = AreaSubp(requireContext()).obtenerSubdepto()
        val adapter = mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.adapters.ListAdapter(areaSubp)

        areaSubp.forEach {
            Log.i("SELECT INNER",it.toString())
        }

        val area =  Area(requireContext()).obtenerAreas()
        val dep =  Subdepartamento(requireContext()).obtenerSubdepto()
        area.forEach {
            Log.i("SELECT * FROM area",it.toString())
        }
        dep.forEach {
            Log.i("SELECT * FROM subde",it.toString())
        }


        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        return root
    }

    fun obtenerAreaSubd():ArrayList<AreaSubp>{
        var areaSubp = ArrayList<AreaSubp>()

        return areaSubp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}