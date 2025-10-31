package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityHealthResumeBinding
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityIdealWeightBinding

class HealthResumeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthResumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentRecebido = intent
        val nome = intentRecebido.getStringExtra("EXTRA_NOME") ?: ""
        val imc = intentRecebido.getDoubleExtra("EXTRA_IMC", 0.0)
        val categoria = intentRecebido.getStringExtra("EXTRA_CATEGORIA") ?: ""
        val gcd = intentRecebido.getDoubleExtra("EXTRA_GASTO_CALORICO", 0.0)

        val pesoIdeal = intentRecebido.getDoubleExtra("EXTRA_PESO_IDEAL", 0.0)
        val pesoAtual = intentRecebido.getDoubleExtra("EXTRA_PESO_ATUAL", 0.0)

        val alturaCm = intentRecebido.getIntExtra("EXTRA_ALTURA_CM", 0)



        binding.nomeHealthResumeTextView.
        val decimalFormat = "%.2f"

        binding.nomeHealthResumeTextView.text = nome

        binding.idealWeightResultTextView.text =
            "Seu peso ideal é:\n${decimalFormat.format(pesoIdeal)} kg"


        binding.categoriaPreMensagemImcResultTextView.text = categoria

        binding.dailyExpenditureTextView.text = "GCD: ${gcd}"

        val agua = pesoAtual *100 / 350

        binding.aguaTv.text =
            "Qtd de agua ideal: ${agua}"


    }
}