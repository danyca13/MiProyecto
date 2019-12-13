package dany.caro.miproyecto

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var personas:ArrayList<Lugar>?= null
    var adapter: LugarAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        myRecycler.layoutManager=GridLayoutManager(applicationContext, 1)!!
        myRecycler.setHasFixedSize(true)
        personas = ArrayList()
        adapter= LugarAdapter(personas!!, this)
        myRecycler.adapter= adapter

        val cache= DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())

        val requesQueue = RequestQueue(cache,network).apply {
            start()
        }
        val url = "https://randomuser.me/api/?results=10"

        val jsonObjectPersonas = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val resultadoJSON=response.getJSONArray("results")
                for (indice in 0..resultadoJSON.length()-1){
                    val personaJSON = resultadoJSON.getJSONObject(indice)
                    val genero = personaJSON.getString("gender")
                    val nombreJSON =personaJSON.getJSONObject("name")
                    val nombrePersona ="${nombreJSON.getString("title")} ${nombreJSON.getString("first")} ${nombreJSON.getString("last")}"
                    val fotoJASON = personaJSON.getJSONObject("picture")
                    val foto= fotoJASON.getString("large")
                    val locationJSON= personaJSON.getJSONObject("location")
                    val coordJSON= locationJSON.getJSONObject("coordinates")
                    val latitud = coordJSON.getString("latitude").toDouble()
                    val longitud = coordJSON.getString("longitude").toDouble()


                    personas!!.add(Lugar(nombrePersona,foto,longitud,latitud,genero))


                }

                adapter!!.notifyDataSetChanged()

            },Response.ErrorListener { error ->
                Log.wtf("error volley",error.localizedMessage)
            })

        requesQueue.add(jsonObjectPersonas)
    }
}

