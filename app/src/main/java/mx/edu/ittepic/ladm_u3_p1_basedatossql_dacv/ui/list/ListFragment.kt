package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.list

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.adapters.AreasAdapter
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.adapters.SubdepAdapter
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.databinding.FragmentListBinding
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Area
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Subdepartamento
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils
import java.util.ArrayList

class ListFragment : Fragment() {
    private var array = arrayListOf<String>()

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var areas = ArrayList<Area>()
    lateinit var adapter : AreasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = binding.spinnerAreas

        array.clear()

        array.add("Seleccione una opción")
        array.add("Descripción")
        array.add("División")

        val aa = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, array)
        aa.setNotifyOnChange(true)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa

        val rv = binding.rvList

        areas = Area(requireContext()).obtenerAreas()
        adapter = AreasAdapter(areas, object : AreasAdapter.onItemClickListenr{
            override fun onItemClick(area: Area, i: Int) {
                if (i==0)
                    editArea(area)
                else
                    deleteArea(area)
            }
        })

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        binding.btnBuscar.setOnClickListener {
            if (spinner.selectedItemPosition==0){
                Toast.makeText(requireContext(), "Seleccione una opción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var opc = spinner.selectedItem.toString().trim()
            if (opc=="División") opc = Utils.DIVISION
            if (opc=="Descripción") opc = Utils.DESCRIPCION
            val valor = binding.campoBuscar.text.toString()
            var areasWhere : ArrayList<Area>
            if(valor.equals("")){
                Toast.makeText(requireContext(), "Ingrese un valor", Toast.LENGTH_SHORT).show()
                areasWhere =  Area(requireContext()).obtenerAreas()
            }else
                areasWhere =  Area(requireContext()).obtenerAreasWhere(opc,valor)
            areas.clear()
            areasWhere.forEach {
                areas.add(it)
            }
            adapter.notifyDataSetChanged()
        }
        return root
    }

    private fun deleteArea(area: Area) {
        val i = Subdepartamento(requireContext()).obtenerSubdepWhere(Utils.IDAREA,area.idArea).size
        if (i>0) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("No se pueden eliminar áreas si hay subdepartamentos asociados a ella.")
                .setPositiveButton("Cerrar",{d,i->})
                .show()
            return
        }
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Área")
            .setMessage("¿Está seguro de elimnar el área ${area.descripcion}?")
            .setPositiveButton("Sí") { d, i ->
                area.eliminar()
                val areasWhere = Area(requireContext()).obtenerAreas()
                areas.clear()
                areasWhere.forEach {
                    areas.add(it)
                }
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("No",{d,i->d.dismiss()})
            .show()

    }

    private fun editArea(area: Area) {
        val builder = AlertDialog.Builder(requireContext())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater;
        val v = inflater.inflate(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.layout.dialog_edit_area,null)
        var ed_descr = v.findViewById<EditText>(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.id.ed_descripcion_di)
        var ed_div = v.findViewById<EditText>(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.id.ed_division_di)
        var ed_canEm = v.findViewById<EditText>(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.id.ed_cant_empleados_di)

        ed_descr.setText(area.descripcion)
        ed_div.setText(area.division)
        ed_canEm.setText(area.cantidadEmpleados.toString())

        builder.setView(v)
            .setPositiveButton("Editar",
            DialogInterface.OnClickListener { dialog, id ->
                area.descripcion = ed_descr.text.toString()
                area.division = ed_div.text.toString()
                area.cantidadEmpleados = ed_canEm.text.toString().toInt()
                if(area.actualizar()) {
                    Toast.makeText(requireContext(), "Se actualizó con éxito", Toast.LENGTH_SHORT)
                        .show()
                    var areasWhere = Area(requireContext()).obtenerAreas()
                    areas.clear()
                    areasWhere.forEach {
                        areas.add(it)
                    }
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()

                }
                else{
                    AlertDialog.Builder(requireContext())
                        .setTitle("Atención")
                        .setMessage("No se pudo actualizar")
                        .show()
                }
            })
            .setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        builder.create()
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}