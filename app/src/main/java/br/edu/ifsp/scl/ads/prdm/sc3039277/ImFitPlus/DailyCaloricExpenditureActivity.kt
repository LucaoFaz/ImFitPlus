package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityDailyCaloricExpenditureBinding

class DailyCaloricExpenditureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyCaloricExpenditureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyCaloricExpenditureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentRecebido = intent
        val idade = intentRecebido.getIntExtra("EXTRA_IDADE", 0)
        val peso = intentRecebido.getDoubleExtra("EXTRA_PESO", 0.0)
        val alturaCm = intentRecebido.getIntExtra("EXTRA_ALTURA_CM", 0)
        val sexo = intentRecebido.getStringExtra("EXTRA_SEXO") ?: ""
        val atividade = intentRecebido.getStringExtra("EXTRA_ATIVIDADE") ?: ""

        val tmb = if (sexo == "Masculino") {
            //homens
            66 + (13.7 * peso) + (5 * alturaCm) - (6.8 * idade)
        } else {
            //mulheres
            655 + (9.6 * peso) + (1.8 * alturaCm) - (4.7 * idade)
        }

        val fatorAtividade = when (atividade) {
            "Sedentário" -> 1.2
            "Leve" -> 1.375
            "Moderado" -> 1.55
            "Intenso" -> 1.725
            else -> 1.0 // valor padrao
        }
        val decimalFormat = "%.2f"

        val gcd = tmb * fatorAtividade
        binding.dailyExpenditureResultDailyCaloricTextView.text =
            "Seu Gasto Calórico Diário (GCD) é:\n${decimalFormat.format(gcd)} kcal"


        binding.tmbResultDailyCaloricTextView.text =
            "Sua Taxa Metabólica Basal (TMB) é:\n${decimalFormat.format(tmb)} kcal"

        binding.voltarDailyCaloricButton.setOnClickListener {
            finish()
        }

        binding.calcularPesoIdealDailyCaloricButton.setOnClickListener {
            val intent = Intent(this, IdealWeightActivity::class.java)

            // tela 5 precisa da altura (em metros) e do peso atual
            val alturaM = alturaCm / 100.0 // Convertemos aqui
            intent.putExtra("EXTRA_ALTURA_M", alturaM)
            intent.putExtra("EXTRA_PESO_ATUAL", peso)

            startActivity(intent)
        }

    }
}