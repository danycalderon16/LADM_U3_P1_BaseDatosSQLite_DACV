package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.ui.modify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.databinding.FragmentModifyBinding

class ModifyFragment : Fragment() {
    /************************************
     * DANIEL ALEJANDRO CALDERÓN VIGREN *
     ************************************/

    private var _binding: FragmentModifyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ModifyViewModel::class.java)

        _binding = FragmentModifyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}