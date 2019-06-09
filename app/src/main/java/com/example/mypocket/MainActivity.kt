package com.example.mypocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.jsonBody
import java.util.*


var configHost: String = ""
var configUser: String = ""
var configPasswd: String = ""
val configName: String = "configure"


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val props = Properties()
        props.load(assets.open(configName))
        configUser = props.getProperty("user")
        println(configUser)
        configPasswd = props.getProperty("passwd")
        println(configPasswd)
        configHost = props.getProperty("host")
        println(configHost)
    }

    fun addBtnClick(view: View) {
        val editText = findViewById<EditText>(R.id.editTextInput)
        val message = editText.text.toString()
        println(message)
        process("add", message, message)
    }

    fun delBtnClick(view: View) {
        val editText = findViewById<EditText>(R.id.editTextInput)
        val message = editText.text.toString()
        println(message)
        process("del", message, message)
    }

    fun process(action: String, title: String, url: String) {
        println(action + title + url)
        val textResult = findViewById<TextView>(R.id.textViewResult)
        textResult.text = ""
        val hostUrl = configHost + "/" + action
        Fuel.post(hostUrl).jsonBody(BookmarkNode(title, url)).response { request, response, result ->
            val (data, error) = result
            if (error != null) {
                println(error)
                textResult.text = error.toString()
            } else {
                textResult.text = response.responseMessage
            }
        }
    }
}

data class BookmarkNode(val title: String, val url: String) {
    val user: String = configUser
    val passwd: String = configPasswd
}
