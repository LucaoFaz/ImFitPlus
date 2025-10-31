package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityIdealWeightBinding
import kotlin.math.abs

class IdealWeightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIdealWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdealWeightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentRecebido = intent
        val alturaM = intentRecebido.getDoubleExtra("EXTRA_ALTURA_M", 0.0)
        val pesoAtual = intentRecebido.getDoubleExtra("EXTRA_PESO_ATUAL", 0.0)

        val pesoIdeal = 22 * (alturaM * alturaM)
        val diferenca = pesoAtual - pesoIdeal
        val decimalFormat = "%.2f"

        binding.realWeightResultTextView.text =
            "Seu peso atual é:\n${decimalFormat.format(pesoAtual)} kg"

        binding.idealWeightResultTextView.text =
            "Seu peso ideal é:\n${decimalFormat.format(pesoIdeal)} kg"

        if (diferenca > 0) {
            //acima do peso ideal
            binding.weightDifferenceTextView.text =
                "Você precisa perder:\n${decimalFormat.format(diferenca)} kg"
        } else if (diferenca < 0) {
            //abaixo do peso ideal
            binding.weightDifferenceTextView.text =
                "Você precisa ganhar:\n${decimalFormat.format(abs(diferenca))} kg"
        } else {
            //peso ideal!
            binding.weightDifferenceTextView.text = "Parabéns! Você está no seu peso ideal!"
        }


        binding.voltarInicioButton.setOnClickListener {
            //voltar para a primeira tela (WelcomeActivity)
            val intentParaInicio = Intent(this, WelcomeActivity::class.java)

            // flags para limpar o histórico qndo o user clicar em voltar
            intentParaInicio.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intentParaInicio)
            finish()
        }
    }
}