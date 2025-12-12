package br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.semantics.setText
//import androidx.compose.ui.semantics.text
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data.AppDatabase
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data.User
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.data.UserDao
import br.edu.ifsp.scl.ads.prdm.sc3039277.ImFitPlus.databinding.ActivityPersonalDataBinding
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class PersonalDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataBinding
    fun calculateAge(birthDate: LocalDate): Int{
        //quero pegar a data de nascimento e calcular a idade usando isso...
        val currentDate = LocalDate.now()
        return Period.between(birthDate, currentDate).years
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDao = AppDatabase.getDatabase(this).userDao()
//
//        val dataNasc = binding.nascTextInputEditText.text
//        val age = calculateAge(LocalDate.parse(dataNasc))

        lifecycleScope.launch {
            val userSalvo = userDao.getUser()
            if (userSalvo != null) {
                binding.nomeTextInputEditText.setText(userSalvo.nome)
                binding.nascTextInputEditText.setText(userSalvo.dataNasc)
                binding.alturaTextInputEditText.setText(userSalvo.altura.toString())
                binding.pesoTextInputEditText.setText(userSalvo.peso.toString())

                if (userSalvo.sexo == "Feminino"){
                    binding.sexoRadioGroup.check(R.id.feminino_radioButton)
                }else{
                    binding.sexoRadioGroup.check(R.id.masculino_radioButton)
                }

                val niveis = resources.getStringArray(R.array.niveis_atividade)
                val posicao = niveis.indexOf(userSalvo.atividade)
                if (posicao >= 0) {
                    binding.nivelAtividadeSpinner.setSelection(posicao)
                }
                Toast.makeText(this@PersonalDataActivity, "Dados recuperados!", Toast.LENGTH_SHORT).show()
            }
        }

        val adapterAtividade = ArrayAdapter.createFromResource(
            this,
            R.array.niveis_atividade,
            android.R.layout.simple_spinner_item
        )

        adapterAtividade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.nivelAtividadeSpinner.adapter = adapterAtividade


        val calcularImcButton = binding.calcularImcButton

        calcularImcButton.setOnClickListener {
            val alturaString = binding.alturaTextInputEditText.text.toString()
            val pesoString = binding.pesoTextInputEditText.text.toString()
            val nomeString = binding.nomeTextInputEditText.text.toString()
            val dataString = binding.nascTextInputEditText.text.toString()

            if (alturaString.isEmpty() || pesoString.isEmpty() || nomeString.isEmpty() || dataString.isEmpty()){
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.termosCheckbox.isChecked) {
                Toast.makeText(this, "Você precisa de aceitar os termos de uso", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idade: Int
            try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val dataNasc = LocalDate.parse(dataString, formatter)
                idade = calculateAge(dataNasc)
            } catch (e: DateTimeParseException) {
                Toast.makeText(this, "formato de data invalido. Use dd/mm/yyyy.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val alturaCmDouble = alturaString.replace(',', '.').toDouble()
            val peso = pesoString.replace(',', '.').toDouble()

            val alturaCm = alturaCmDouble.toInt()

            val altura = alturaCm/100.0
            val atividade = binding.nivelAtividadeSpinner.selectedItem.toString()
            val sexoId = binding.sexoRadioGroup.checkedRadioButtonId
            var sexo = "Masculino"
            if (sexoId == R.id.feminino_radioButton)
                sexo = "Feminino"
            val imc = peso / (altura * altura)

            //salvando no banco de dados
            val userParaSalvar = User(
                nome = nomeString,
                idade = idade,
                dataNasc = dataString,
                sexo = sexo,
                altura = alturaCmDouble,
                peso = peso,
                atividade = atividade
            )

            lifecycleScope.launch {
                userDao.insertUser(userParaSalvar)
            }

            val intent = Intent(this, ImcResultActivity::class.java)

            intent.putExtra("EXTRA_NOME", nomeString)
            intent.putExtra("EXTRA_PESO", peso)
            intent.putExtra("EXTRA_ALTURA_CM", alturaCm)
            intent.putExtra("EXTRA_IDADE", idade)
            intent.putExtra("EXTRA_IMC", imc)
            intent.putExtra("EXTRA_SEXO", sexo)
            intent.putExtra("EXTRA_ATIVIDADE", atividade)

            startActivity(intent)
        }
    }
}
