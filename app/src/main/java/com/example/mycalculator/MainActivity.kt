package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt


private var tvInput: TextView ? = null

var lastNumeric: Boolean? = false
var lastDot: Boolean? = false
var lastFactorial: Boolean? = false
var mathError: Boolean? = false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tv_input)
    }

    fun onDigit(view: View){
        if((lastFactorial!=true) && (mathError == false)){
            tvInput?.append((view as Button).text)
            lastNumeric = true
            lastDot = false
        }
    }



    fun onClear(view: View){
        tvInput?.text = ""
        lastFactorial = false;
        mathError = false;
    }


    fun onDot(view: View){
        val valueOnScreen =  tvInput?.text.toString()
        if( (lastNumeric == true && lastDot == false) && (valueOnScreen.contains(".") != true)){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true

        }
        else{
            Toast.makeText(this, "This is not allowed because it will not be a number", Toast.LENGTH_SHORT ).show()
        }
    }

    fun onFactorial(view: View){
        val valueOnScreen =  tvInput?.text.toString()
        if( (lastNumeric == true && lastDot == false) && ( (valueOnScreen.endsWith("!") != true) && (valueOnScreen.contains(".") != true) && (valueOnScreen.startsWith("-") != true)  ) ){
            tvInput?.append((view as Button).text)
            lastFactorial = true;
            lastNumeric = false;
        }
        else{
            Toast.makeText(this, "This is not allowed because it will not be a number", Toast.LENGTH_SHORT ).show()
        }
    }


    fun onOperator(view: View){
        tvInput?.text.let {
            if (lastNumeric == true && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric =false
                lastDot = false
            }
        }
    }

    private fun deleteDotZero(result: String):String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0, result.length-2)
        }
       return  value
    }


    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }
        else{
            value.contains("+")||
                    value.contains("-") ||
                    value.contains("*")||
                    value.contains("-")
        }
    }



    fun onEqual(view: View){

        if (lastNumeric == true ||  lastFactorial == true){
            var tvValue = tvInput?.text.toString().trim()
            var prefix = ""
            try {


                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0];
                    val two = splitValue [1]

                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }

                    val result = one.toDouble() - two.toDouble()
                    tvInput?.text = deleteDotZero(result.toString())
                }
                else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0];
                    val two = splitValue [1]

                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }

                    val result = one.toDouble() + two.toDouble()
                    tvInput?.text = deleteDotZero(result.toString())
                }
                else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0];
                    val two = splitValue [1]

                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }

                    val result = one.toDouble() / two.toDouble()
                    tvInput?.text = deleteDotZero(result.toString())
                }

                else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0];
                    val two = splitValue [1]

                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }

                    val result = one.toDouble() * two.toDouble()
                    tvInput?.text = deleteDotZero(result.toString())
                }



                else if (tvValue.endsWith('!')){

                    var one = tvValue.substringBefore("!").toInt()

                    one += 1
                    var result = 1L;
                    if(one >= 0 && one <= 21){


                        for(i in 1 until one){
                            result = result * i;
                        }
                        tvInput?.text = deleteDotZero(result.toString())
                        lastNumeric = true;
                        lastFactorial = false;
                    }
                    else{
                        tvInput?.text = "Math Error";
                        lastNumeric =false;
                        mathError = true;

                    }




                }


            }catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }
}



