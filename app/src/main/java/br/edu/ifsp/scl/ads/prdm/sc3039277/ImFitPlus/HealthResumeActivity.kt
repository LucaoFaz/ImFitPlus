package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data.AppDatabase
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data.Calc
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityHealthResumeBinding
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class HealthResumeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthResumeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Receber todos os dados
        val intentRecebido = intent
        val nome = intentRecebido.getStringExtra("EXTRA_NOME") ?: ""
        val imc = intentRecebido.getDoubleExtra("EXTRA_IMC", 0.0)
        val categoria = intentRecebido.getStringExtra("EXTRA_CATEGORIA") ?: ""
        val gcd = intentRecebido.getDoubleExtra("EXTRA_GASTO_CALORICO", 0.0)
        val tmb = intentRecebido.getDoubleExtra("EXTRA_TMB", 0.0)
        val pesoIdeal = intentRecebido.getDoubleExtra("EXTRA_PESO_IDEAL", 0.0)
        val pesoAtual = intentRecebido.getDoubleExtra("EXTRA_PESO_ATUAL", 0.0)


        val df = DecimalFormat("#.##")

        binding.nomeHealthResumeTextView.text = "Resumo para: $nome"
        binding.imcHealthResumeTextView.text = "IMC: ${df.format(imc)}"
        binding.categoriaPreMensagemImcResultTextView.text = "Categoria: $categoria"
        binding.idealWeightResultTextView.text = "Peso Ideal: ${df.format(pesoIdeal)} kg"
        binding.dailyExpenditureTextView.text = "Gasto Calórico Diário: ${df.format(gcd)} kcal"


        val agua = pesoAtual * 1000.0 /350
        binding.aguaTv.text = "Meta de Água: ${df.format(agua)} Litros/dia"


        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val novoCalculo = Calc(
                tipo = "Geral",
                imc = imc,
                categoriaImc = categoria,
                tmb = tmb, //se não tiver TMB no intent, vai salvar 0.0
                pesoIdeal = pesoIdeal
            )
            db.calcDao().insertCalc(novoCalculo)
            Toast.makeText(this@HealthResumeActivity, "Histórico salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }

        //botao finalizar
        binding.finalizarButton.setOnClickListener {
            val intentInicio = Intent(this, WelcomeActivity::class.java)
            // Limpar a pilha para não voltar para trás
            intentInicio.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentInicio)
            finish()
        }
    }
}