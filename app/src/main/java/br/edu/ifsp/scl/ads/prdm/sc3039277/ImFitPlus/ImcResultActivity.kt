package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityImcResultBinding

class ImcResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImcResultBinding

    private var nome = ""
    private var idade = 0
    private var peso = 0.0
    private var alturaCm = 0
    private var atividade = ""
    private var sexo = ""
    private var imc = 0.0
    private var categoria = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImcResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //intent da activity anterior
        val intentRecebido = intent

        nome = intentRecebido.getStringExtra("EXTRA_NOME") ?: ""
        imc = intentRecebido.getDoubleExtra("EXTRA_IMC", 0.0)
        idade = intentRecebido.getIntExtra("EXTRA_IDADE", 0)
        peso = intentRecebido.getDoubleExtra("EXTRA_PESO", 0.0)
        alturaCm = intentRecebido.getIntExtra("EXTRA_ALTURA_CM", 0)
        sexo = intentRecebido.getStringExtra("EXTRA_SEXO") ?: ""
        atividade = intentRecebido.getStringExtra("EXTRA_ATIVIDADE") ?: ""


        binding.nomeImcResultTextView.text = nome

        val decimalFormat = "%.2f"
        binding.imcImcResultTextView.text = decimalFormat.format(imc)

        //calcular categoria...
        categoria = calcularCategoriaImc(imc)
        binding.categoriaImcResultTextView.text = categoria
        val cor = when (categoria) {
            "Abaixo do peso" -> android.graphics.Color.BLUE
            "Peso normal" -> android.graphics.Color.GREEN
            "Sobrepeso" -> android.graphics.Color.YELLOW
            else -> android.graphics.Color.RED
        }
        binding.categoriaImcResultImageView.setBackgroundColor(cor)

        binding.voltarImcResultButton.setOnClickListener {
            finish()
        }

        binding.calcularGastoCaloricoImcResultButton.setOnClickListener {
            //intent
            val intent = Intent(this, DailyCaloricExpenditureActivity::class.java)
            intent.putExtra("EXTRA_NOME", nome)
            intent.putExtra("EXTRA_PESO", peso)
            intent.putExtra("EXTRA_ALTURA_CM", alturaCm)
            intent.putExtra("EXTRA_IDADE", idade)
            intent.putExtra("EXTRA_IMC", imc)
            intent.putExtra("EXTRA_SEXO", sexo)
            intent.putExtra("EXTRA_ATIVIDADE", atividade)
            intent.putExtra("EXTRA_CATEGORIA", categoria)

            startActivity(intent)
        }
    }

    private fun calcularCategoriaImc(imc: Double): String {
        val categoria = when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 25 -> "Peso normal"
            imc < 30 -> "Sobrepeso"
            else -> "Obesidade"
        }
        return categoria
    }
}