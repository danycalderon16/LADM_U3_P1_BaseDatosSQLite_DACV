package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.subdepartamentos

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.adapters.SubdepAdapter
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.databinding.FragmentSubdepartamentosBinding
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Area
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.AreaSubp
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Subdepartamento
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.list.ListViewModel
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.utils.Utils
import java.util.ArrayList

class SubdepartametosFragment : Fragment() {
    private var array = arrayListOf<String>()

    private var _binding: FragmentSubdepartamentosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var areaSubList = ArrayList<AreaSubp>()
    lateinit var adapter : SubdepAdapter

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
        array.add("Área")
        array.add("División")

        val aa = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, array)
        aa.setNotifyOnChange(true)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner.adapter = aa

        val rv = binding.rvList

        areaSubList = AreaSubp(requireContext()).obtenerSubdepto()
        adapter = SubdepAdapter(areaSubList, object : SubdepAdapter.onItemClickListenr{
            override fun onItemClick(areaSub: AreaSubp, i: Int) {
                if (i==0)
                    editAreaSub(areaSub)
                else
                    deleteAreaSub(areaSub)
            }

        })

        areaSubList.forEach {
            Log.i("SELECT INNER 76",it.toString())
        }

        val area =  Area(requireContext()).obtenerAreas()
        val dep =  Subdepartamento(requireContext()).obtenerSubdepto()
        dep.forEach {
            Log.i("SELECT * FROM subde 82",it.toString())
        }
        area.forEach {
            Log.i("SELECT * FROM area 85",it.toString())
        }


        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        binding.btnBuscar.setOnClickListener {
            if (spinner.selectedItemPosition==0){
                Toast.makeText(requireContext(), "Seleccione una opción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var opc = spinner.selectedItem.toString().trim()
            if (opc=="Edificio") opc = Utils.IDEDIFICIO
            if (opc=="Área") opc = Utils.DESCRIPCION
            if (opc=="División") opc = Utils.DIVISION
            val valor = binding.campoBuscar.text.toString().trim()
            var areaSubWhere : ArrayList<AreaSubp>
            if(valor.equals("")){
                Toast.makeText(requireContext(), "Ingrese un valor", Toast.LENGTH_SHORT).show()
                areaSubWhere =  AreaSubp(requireContext()).obtenerSubdepto()
            }else
                areaSubWhere =  AreaSubp(requireContext()).obtenerSubdepto(opc,valor)
            areaSubList.clear()
            areaSubWhere.forEach {
                areaSubList.add(it)
            }
            adapter.notifyDataSetChanged()
        }

        return root
    }

    private fun deleteAreaSub(areaSub: AreaSubp) {
        val subdep = Subdepartamento(requireContext()).obtenerSubdepto(areaSub.idSubdepto)
        Log.i("Select delete",""+subdep.toString())
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Subdepartamento")
            .setMessage("¿Está seguro de elimnar el área ${areaSub.descripcion} " +
                    "en el edificio ${subdep.idEdificio} en el piso ${subdep.piso}?")
            .setPositiveButton("Sí",{d,i->subdep.eliminar()})
            .setNegativeButton("No",{d,i->d.dismiss()})
            .show()
        adapter.notifyDataSetChanged()
    }

    private fun editAreaSub(areaSub: AreaSubp) {
        val subdep = Subdepartamento(requireContext()).obtenerSubdepto(areaSub.idSubdepto)
        val builder = AlertDialog.Builder(requireContext())
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater;
        val v = inflater.inflate(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.layout.dialog_edit_subdep,null)
        var ed_edif = v.findViewById<EditText>(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.id.ed_id_edificio_edit)
        var ed_piso = v.findViewById<EditText>(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.id.ed_piso_edit)
        var spinnerEd = v.findViewById<Spinner>(mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R.id.spinner_ed)

        var areas = arrayListOf<String>()
        var index = 0
        areas.add("Seleccione un Área")
        var deps = Area(requireContext()).obtenerDepartamentos()

        (0..deps.size-1).forEach {
            if(deps[it].equals(areaSub.descripcion))
                index = it+1
            areas.add(deps[it])
        }
        val aa = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, areas)
        aa.setNotifyOnChange(true)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerEd.adapter = aa

        Log.i("index",""+index)

        spinnerEd.setSelection(index)

        ed_edif.setText(subdep.idEdificio)
        ed_piso.setText(""+subdep.piso)

        builder.setView(v)
            .setPositiveButton("Editar",
                DialogInterface.OnClickListener { dialog, id ->
                    subdep.idEdificio = ed_edif.text.toString()
                    subdep.piso = ed_piso.text.toString().toInt()
                    val id = spinnerEd.selectedItem.toString()
                    val idArea = Area(requireContext()).obtenerIdArea(id)
                    subdep.idArea = idArea
                    if(subdep.actualizar()) {
                        Toast.makeText(requireContext(), "Se actualizó con éxito", Toast.LENGTH_SHORT)
                            .show()
                        var areasUpdate = AreaSubp(requireContext()).obtenerSubdepto()
                        areaSubList.clear()
                        areasUpdate.forEach {
                            Log.i("Select 140",it.toString())
                            areaSubList.add(it)
                        }
                        Log.i("Select subdep up 148",subdep.toString())
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

    fun obtenerAreaSubd():ArrayList<AreaSubp>{
        var areaSubp = ArrayList<AreaSubp>()

        return areaSubp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}