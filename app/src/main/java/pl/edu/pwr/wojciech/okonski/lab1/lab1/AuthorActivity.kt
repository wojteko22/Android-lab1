package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_author.*

class AuthorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)
        setSupportActionBar(childToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
