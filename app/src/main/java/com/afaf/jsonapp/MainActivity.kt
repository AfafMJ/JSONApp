package com.afaf.jsonapp


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var curencyDetails: currencies? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userinp = findViewById<View>(R.id.etUserInput) as EditText
        val convrt = findViewById<View>(R.id.btconvert) as Button
        val spinner = findViewById<View>(R.id.spr) as Spinner

        val cur = arrayListOf("ada", "aed", "afn", "all", "amd")

        var selected: Int = 0

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, cur
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    selected = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
        convrt.setOnClickListener {

            var sel = userinp.text.toString()
            var currency: Double = sel.toDouble()

            getCurrency(onResult = {
                curencyDetails = it
                //"ada", "aed", "afn", "all", "amd"

                when (selected) {
                    0 -> display(calc(curencyDetails?.eur?.ada?.toDouble(), currency));
                    1 -> display(calc(curencyDetails?.eur?.aed?.toDouble(), currency));
                    2 -> display(calc(curencyDetails?.eur?.afn?.toDouble(), currency));
                    3 -> display(calc(curencyDetails?.eur?.all?.toDouble(), currency));
                    4 -> display(calc(curencyDetails?.eur?.amd?.toDouble(), currency));

                }
            })
        }
    }


    private fun display(calc: Double) {

        val responseText = findViewById<View>(R.id.tvShow) as TextView

        responseText.text = "result " + calc
    }

    private fun calc(i: Double?, sel: Double): Double {
        var s = 0.0
        if (i != null) {
            s = (i * sel)
        }
        return s
    }

    private fun getCurrency(onResult: (currencies?) -> Unit) {
        val apiInterface = APICllient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.getCurrency()?.enqueue(object : Callback<currencies> {
                override fun onResponse(
                    call: Call<currencies>,
                    response: Response<currencies>
                ) {
                    onResult(response.body())

                }

                override fun onFailure(call: Call<currencies>, t: Throwable) {
                    onResult(null)
                    Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show();
                }

            })
        }
    }
}
