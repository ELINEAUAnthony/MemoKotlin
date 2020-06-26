package com.formationandroid.memokotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.formationandroid.memokotlin.R
import com.formationandroid.memokotlin.fragment.DetailFragment

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val memo = intent.getStringExtra("memo")

//        Fragment
        val fragment = DetailFragment()
        val bundle = Bundle()
        //        Passage de parametre dans le fragment
        bundle.putString("memo", memo)
        fragment.arguments = bundle

//        Fragment Manager
        val fragmentManager = supportFragmentManager

//        Transaction
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.conteneur_detail, fragment, "detail")
        fragmentTransaction.commit()
    }
}