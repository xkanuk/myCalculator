package com.xkanuk.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false
    var lastDot : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric &&  !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {

            var tvValue = tvInput.text.toString()
            var prefix = ""

            if (tvValue.startsWith("-")) {
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            try {
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var a = prefix + splitValue[0]
                    var b = splitValue[1]

                    tvInput.text = (a.toDouble() - b.toDouble()).toString()
                }
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var a = prefix + splitValue[0]
                    var b = splitValue[1]

                    tvInput.text = (a.toDouble() + b.toDouble()).toString()
                }
                else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var a = prefix + splitValue[0]
                    var b = splitValue[1]

                    tvInput.text = (a.toDouble() * b.toDouble()).toString()
                }
                else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var a = prefix + splitValue[0]
                    var b = splitValue[1]

                    tvInput.text = (a.toDouble() / b.toDouble()).toString()
                }
      //          tvInput.text = removeTrailingZeros(tvInput.text.toString())
                tvInput.text = removeZeroAfterDot(tvInput.text.toString())

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }

    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastDot = false
            lastNumeric = false
        }
    }


    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        if (result.endsWith(".0")) {
            return result.substring(0,result.length-2)
        }
        else return result

    }

    private fun removeTrailingZeros(result: String) : String {
        var value = result
        while (value.contains(".") && value.endsWith("0")) {
            value = value.substring(0, -1)
        }
        return value
    }
}