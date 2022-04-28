package mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.R
import mx.edu.ittepic.ladm_u3_p1_basedatossql_dacv.models.AreaSubp

class ListAdapter (private val list:ArrayList<AreaSubp>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_list,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.descr.setText(list[position].descripcion)
        holder.canEmp.setText(list[position].cantidadEmpleados.toString())
        holder.edif.setText(list[position].idEdificio)
        holder.piso.setText(list[position].piso.toString())
        holder.div.setText(list[position].division)

        //holder.dep.text = list[position].subdepartamento.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(item: View) :RecyclerView.ViewHolder(item){
        var descr : TextView = item.findViewById(R.id.tv_descrp)
        var canEmp : TextView = item.findViewById(R.id.tv_cant_emp)
        var edif : TextView = item.findViewById(R.id.tv_id_edif)
        var piso : TextView = item.findViewById(R.id.tv_piso)
        var div : TextView = item.findViewById(R.id.tv_division)

    }
}