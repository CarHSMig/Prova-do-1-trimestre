package com.example.RodrigosTravelEdition

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AlignmentSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import android.text.Layout.Alignment

class MainActivity : AppCompatActivity() {
    private val travels = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        val paisInput = findViewById<EditText>(R.id.pais)
        val cidadeInput = findViewById<EditText>(R.id.cidade)
        val gastoInput = findViewById<EditText>(R.id.gasto)
        val valorInput = findViewById<EditText>(R.id.valor)
        val resultView = findViewById<TextView>(R.id.result)
        val countView = findViewById<TextView>(R.id.contadoralunos)
        val insertButton = findViewById<Button>(R.id.btn)
        val resetButton = findViewById<Button>(R.id.zerar)
        var divida = findViewById<TextView>(R.id.totalGasto)
        var total = 0.00

        insertButton.setOnClickListener {
            val localPais = paisInput.text.toString()
            val localCidade = cidadeInput.text.toString()
            val gastoName = gastoInput.text.toString()
            val valorCust = valorInput.text.toString().toDoubleOrNull()

            if (localPais.isNotEmpty() && localCidade.isNotEmpty() && gastoName.isNotEmpty() && valorCust != null) {
                val currentDate = getCurrentDate()
                total += valorCust

                val travelData = "$localPais - $localCidade - $currentDate - $gastoName - $valorCust"
                travels.add(travelData)

                updateTravelList(resultView, countView)

                paisInput.text.clear()
                cidadeInput.text.clear()
                gastoInput.text.clear()
                valorInput.text.clear()
                divida.text = "Gastos Totais: R$%.2f".format(total)
            } else {
                resultView.text = "Por favor, preencha todos os campos corretamente. O valor deve ser numérico."
            }
        }

        resetButton.setOnClickListener {
            travels.clear()
            updateTravelList(resultView, countView)
            total = 0.00
            divida.text = "Gastos Totais: R$00,00"
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun updateTravelList(resultView: TextView, countView: TextView) {
        val builder = SpannableStringBuilder()

        // Percorre a lista de viagens
        for (travel in travels) {
            val parts = travel.split(" - ")

            if (parts.size == 5) {
                val localInfo = "${parts[0]} - ${parts[1]}"
                val date = parts[2]
                val gasto = "${parts[3]}: ${parts[4]}R$"

                val localSpan = SpannableString(localInfo)
                val dateSpan = SpannableString(date)
                dateSpan.setSpan(
                    AlignmentSpan.Standard(Alignment.ALIGN_OPPOSITE),
                    0, dateSpan.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                builder.append(localSpan).append("\n").append(dateSpan).append("\n").append(gasto).append("\n\n")
            }
        }

        resultView.text = builder
        countView.text = travels.size.toString()
    }
}
