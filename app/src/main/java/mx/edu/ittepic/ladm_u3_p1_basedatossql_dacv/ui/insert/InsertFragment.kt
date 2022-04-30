package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.insert

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.databinding.FragmentInsertBinding
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Area
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.Subdepartamento

class InsertFragment : Fragment() {

    private var _binding: FragmentInsertBinding? = null

    private var array = arrayListOf<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(InsertViewModel::class.java)

        _binding = FragmentInsertBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val spinner: Spinner = binding.spinner

        array.add("Seleccione un área")
        Area(requireContext()).obtenerDepartamentos().forEach {
            array.add(it)
        }
        val aa = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, array)
        aa.setNotifyOnChange(true)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = aa

        binding.btnInsertarArea.setOnClickListener {
            val area = Area(requireContext())

            val desc = binding.edDescripcion.text.toString().trim()
            val division = binding.edDivision.text.toString().trim()
            val cantEmpl = binding.edCantEmpleados.text.toString().trim()

            if (desc.isEmpty() || division.isEmpty() || cantEmpl.isEmpty()){
                Toast.makeText(requireContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            area.descripcion = desc
            area.division = division
            area.cantidadEmpleados = cantEmpl.toInt()

            val resultado = area.insertar()

            if (resultado) {
                Toast.makeText(requireContext(), "SE INSERTO AREA CON EXITO", Toast.LENGTH_SHORT).show()
                binding.edDescripcion.setText("")
                binding.edDivision.setText("")
                binding.edCantEmpleados.setText("")
                array = area.obtenerDepartamentos()
                aa.setNotifyOnChange(true)

            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("No se pudo insertar")
                    .show()
            }
        }

        binding.btnInsertarSubdepto.setOnClickListener {
            val subdepartamento = Subdepartamento(requireContext())

            val edificio = binding.edIdEdificio.text.toString().trim()
            val piso = binding.edPiso.text.toString().trim()

            var id = spinner.selectedItem.toString()
            val idArea = Area(requireContext()).obtenerIdArea(id)

            if (edificio.isEmpty() || piso.isEmpty() || idArea == -1){
                Toast.makeText(requireContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            subdepartamento.idEdificio = edificio
            subdepartamento.piso = piso.toInt()
            subdepartamento.idArea = idArea

            val resultado = subdepartamento.insertar()

            if (resultado) {
                Toast.makeText(requireContext(), "SE INSERTO SUBDEPARTAMENTO CON EXITO", Toast.LENGTH_SHORT).show()
                binding.edIdEdificio.setText("")
                binding.edPiso.setText("")
                spinner.setSelection(0)
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("No se pudo insertar")
                    .show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}