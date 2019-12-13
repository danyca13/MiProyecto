package dany.caro.miproyecto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.targeta3.view.*


class LugarAdapter (lugares:ArrayList<Lugar>,context:Context): RecyclerView.Adapter<LugarAdapter.ViewHoder>() {
    var lugares:ArrayList<Lugar>?=null
    var context:Context?=null

    init {
        this.lugares = lugares
        this.context =context

    }

    override fun getItemCount(): Int {
        return this.lugares!!.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoder {
        val vistalugar:View = LayoutInflater.from(context).inflate(R.layout.mi_targeta,parent,false)
        val lugarViewHolder = ViewHoder(vistalugar)
        vistalugar.tag =lugarViewHolder
        return lugarViewHolder
    }

    override fun onBindViewHolder(p0: LugarAdapter.ViewHoder, p1: Int) {

        p0.nombre!!.text = lugares!![p1].nombre
        p0.descripcion!!.text = lugares!![p1].descripicion
        Picasso.get().load(lugares!![p1].foto)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(p0.imagen)
    }

    class ViewHoder(vista:View):RecyclerView.ViewHolder(vista) {

        var imagen: ImageView?=null
        var nombre: TextView?=null
        var descripcion:TextView?=null


        init {
            imagen = vista.ivPersona
            nombre = vista.tvNombre
            descripcion = vista.tvGenero
        }


    }


}