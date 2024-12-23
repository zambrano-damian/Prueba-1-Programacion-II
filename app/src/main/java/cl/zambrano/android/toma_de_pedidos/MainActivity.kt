package cl.zambrano.android.toma_de_pedidos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import modelo.CuentaMesa
import modelo.ItemMenu
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var quantityPastel: EditText
    private lateinit var quantityCazuela: EditText
    private lateinit var switchTip: Switch
    private lateinit var totalFood: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView

    private val cuentaMesa = CuentaMesa()
    private val pastel = ItemMenu("Pastel de Choclo", 12000)
    private val cazuela = ItemMenu("Cazuela", 10000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        setupListeners()
    }

    private fun bindViews() {
        quantityPastel = findViewById(R.id.quantity_pastel)
        quantityCazuela = findViewById(R.id.quantity_cazuela)
        switchTip = findViewById(R.id.switch_tip)
        totalFood = findViewById(R.id.total_food)
        tipAmount = findViewById(R.id.tip_amount)
        totalAmount = findViewById(R.id.total_amount)
    }

    private fun setupListeners() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                actualizarTotales()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        quantityPastel.addTextChangedListener(textWatcher)
        quantityCazuela.addTextChangedListener(textWatcher)
        switchTip.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }
    }

    private fun actualizarTotales() {
        val pastelCantidad = quantityPastel.text.toString().toIntOrNull() ?: 0
        val cazuelaCantidad = quantityCazuela.text.toString().toIntOrNull() ?: 0

        cuentaMesa.agregarItem(pastel, pastelCantidad)
        cuentaMesa.agregarItem(cazuela, cazuelaCantidad)

        val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
        val propina = cuentaMesa.calcularPropina()
        val totalConPropina = cuentaMesa.calcularTotalConPropina()

        val formatoPesos = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        totalFood.text = formatoPesos.format(totalSinPropina)
        tipAmount.text = formatoPesos.format(propina)
        totalAmount.text = formatoPesos.format(totalConPropina)
    }
}