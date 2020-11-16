package br.com.iesb.felipe.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        historico.movementMethod = ScrollingMovementMethod()

        //Eventos de click
        numero_0.setOnClickListener{AcrescentarUmaExpressao("0", true)}
        numero_1.setOnClickListener{AcrescentarUmaExpressao("1", true)}
        numero_2.setOnClickListener{AcrescentarUmaExpressao("2", true)}
        numero_3.setOnClickListener{AcrescentarUmaExpressao("3", true)}
        numero_4.setOnClickListener{AcrescentarUmaExpressao("4", true)}
        numero_5.setOnClickListener{AcrescentarUmaExpressao("5", true)}
        numero_6.setOnClickListener{AcrescentarUmaExpressao("6", true)}
        numero_7.setOnClickListener{AcrescentarUmaExpressao("7", true)}
        numero_8.setOnClickListener{AcrescentarUmaExpressao("8", true)}
        numero_9.setOnClickListener{AcrescentarUmaExpressao("9", true)}
        ponto.setOnClickListener{AcrescentarUmaExpressao(".", false)}
        parentese_abrir.setOnClickListener{AcrescentarUmaExpressao("(", false)}
        parentese_fechar.setOnClickListener{AcrescentarUmaExpressao(")", false)}

        //operadores
        operador_soma.setOnClickListener{AcrescentarUmaExpressao("+", false)}
        operador_subtracao.setOnClickListener{AcrescentarUmaExpressao("-", false)}
        operador_mutiplicacao.setOnClickListener{AcrescentarUmaExpressao("*", false)}
        operador_divisao.setOnClickListener{AcrescentarUmaExpressao("/", false)}
        operador_raiz.setOnClickListener{AcrescentarUmaExpressao("√", false)}
        operador_mod.setOnClickListener{AcrescentarUmaExpressao("mod", false)}
        operador_fatorial.setOnClickListener{AcrescentarUmaExpressao("!", false)}
        operador_potencia.setOnClickListener{AcrescentarUmaExpressao("^", false)}
        operador_quadrado.setOnClickListener{AcrescentarUmaExpressao("^²", false)}
        operador_log.setOnClickListener{AcrescentarUmaExpressao("log_", false)}
        operador_seno.setOnClickListener{AcrescentarUmaExpressao("sen_", false)}
        operador_coseno.setOnClickListener{AcrescentarUmaExpressao("cos_", false)}
        operador_tangente.setOnClickListener{AcrescentarUmaExpressao("tan_", false)}
        operador_inverso.setOnClickListener{inversa(expressao.text.toString())}
        operador_seno_inverso.setOnClickListener{AcrescentarUmaExpressao("arc_sen", false)}
        operador_coseno_inverso.setOnClickListener{AcrescentarUmaExpressao("arc_cos", false)}
        Operador_tangente_inverso.setOnClickListener{AcrescentarUmaExpressao("arc_tan", false)}
        operador_lognat.setOnClickListener{AcrescentarUmaExpressao("log", false)}

        trocar_sinal.setOnClickListener{
            if(expressao.text.isNotBlank()){
                System.out.println("isNotBlank->")
                if(expressao.text.contains("-")){
                    System.out.println("contais->")
                    expressao.text =  expressao.text.toString().replace("-", "")
                } else {
                    System.out.println("else contais->")
                    expressao.text =  "-"+expressao.text.toString()
                }
            }
        }


        //apagar valores
        deletar.setOnClickListener{
            expressao.text = ""
            resultado.text = ""
        }

        backspace.setOnClickListener{
            val limparString = expressao.text.toString()

            if(limparString.isNotBlank()){
                expressao.text = limparString.substring(0, limparString.length-1)
            }
            resultado.text = ""
        }

        operador_igual.setOnClickListener{

            if(expressao.text.toString().contains("!")){
                fatorial(expressao.text.toString())
            } else {
                calcularExpressoesbiblioteca(expressao.text.toString())
            }

        }

    }



    fun AcrescentarUmaExpressao(valor: String, limpar_dados: Boolean){

        if(resultado.text.isNotEmpty()){
            expressao.text = "";
        }

        if(limpar_dados){
            resultado.text = ""
            expressao.append(valor)
        } else {
            expressao.append(resultado.text)
            expressao.append(valor)
            resultado.text = ""
        }

    }

    fun calcularExpressoesbiblioteca(valor: String){

        val valorFormatado = AlterarFormatacaTela(valor)
        System.out.println("valorFormatado->"+valorFormatado)
        try {

            val calculoExpressao = ExpressionBuilder(valorFormatado).build()

            val calculoResultado = calculoExpressao.evaluate()

            val longResultado = calculoResultado.toLong()

            if(calculoResultado == longResultado.toDouble()) {
                resultado.text = longResultado.toString()
            } else {
                resultado.text = calculoResultado.toString()
            }

            historico.append(valor+"="+resultado.text+"\n")
            //historico.addTextChangedListener {  }
            //historico.text.lines().

            System.out.println("historico-leh->"+historico.length())
            System.out.println("historico-->"+historico.text)

        }catch (e: Exception){

        }

        expressao.text = AlterarFormatacaTela(valorFormatado)
    }

    fun inversa(valorExpressao: String){
        if(valorExpressao.isNotBlank()){
            calcularExpressoesbiblioteca("1/"+valorExpressao)
        }
    }

    fun fatorial(expressao: String){
        var expressaoFormatada = expressao.replace("!", "")
        var factorial: Long = 1
        for (i in 1..expressaoFormatada.toInt()) {
            factorial *= i.toLong()
        }
        resultado.text = factorial.toString()
    }

    fun  AlterarFormatacaTela(expressao: String): String {

        // raiz quadradra
        if(expressao.contains("√")){
            return   expressao.replace("√", "sqrt")
        } else if(expressao.contains("sqrt")){
            return   expressao.replace("sqrt", "√")
        }

        //mod
        if(expressao.contains("mod")){
            return   expressao.replace("mod", "abs")
        } else if(expressao.contains("abs")) {
            return   expressao.replace("abs", "mod")
        }

        //quadrado
        if(expressao.contains("^²")){
            return   expressao.replace("^²", "^2")
        } else if(expressao.contains("^2")) {
            return   expressao.replace("^2", "^²")
        }

        //log base 10
        if(expressao.contains("log_")){
           return   expressao.replace("log_", "log10")
        }else
            if(expressao.contains("log10")) {
            return   expressao.replace("log10", "log_")
       }

        //seno
        if(expressao.contains("sen_")){
            return   expressao.replace("sen_", "sin")
        }else
            if(expressao.contains("sin") && !expressao.contains("asin")) {
                return   expressao.replace("sin", "sen_")
        }

        //cosseno
        if(expressao.contains("cos_") && !expressao.contains("arc_cos")){
            return   expressao.replace("cos_", "cos")
        }else
            if(expressao.contains("cos") && !expressao.contains("arc_cos")) {
                return   expressao.replace("cos", "cos_")
        }

        //tangente
        if(expressao.contains("tan_") && !expressao.contains("arc_tan")){
            return   expressao.replace("tan_", "tan")
        }else
            if(expressao.contains("tan")  && !expressao.contains("arc_tan")
                &&  !expressao.contains("atan")) {
                System.out.println("arc_tan - IF")
                return   expressao.replace("tan", "tan_")
        }


        //seno inverso
        if(expressao.contains("arc_sen") && !expressao.contains("arc_cos")){
            return   expressao.replace("arc_sen", "asin")
        }else
            if(expressao.contains("asin")) {
                return   expressao.replace("asin", "arc_sen")
         }

        // coseno inverso
        if(expressao.contains("arc_cos")){
            System.out.println("arc_cos - IF")
            return   expressao.replace("arc_cos", "acos")
        }else
            if(expressao.contains("acos")) {
                System.out.println("arc_cos - ELSE")
                return   expressao.replace("acos", "arc_cos")
         }

        // tangente inverso
        if(expressao.contains("arc_tan")){
            System.out.println("arc_tan - IF")
            return   expressao.replace("arc_tan", "atan")
        }else
            if(expressao.contains("atan")) {
                System.out.println("arc_tan - ELSE")
                return   expressao.replace("atan", "arc_tan")
            }





            return expressao
    }




}