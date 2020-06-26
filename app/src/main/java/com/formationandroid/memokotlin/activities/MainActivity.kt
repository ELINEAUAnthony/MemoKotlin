package com.formationandroid.memokotlin.activities


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.formationandroid.memokotlin.R
import com.formationandroid.memokotlin.helpers.ItemTouchHelperCallback
import com.formationandroid.memokotlin.memos.adapter.MemoAdapter
import com.formationandroid.memokotlin.memos.dao.MemoDTO
import com.formationandroid.memokotlin.room.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var memoAdapter: MemoAdapter? = null
    private lateinit var listeMemos: List<MemoDTO>

    //    private var listMemoDTO: MutableList<MemoDTO>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listeMemos = AppDatabase.getInstance(this).MemoDAO().getMemo()

        // à ajouter pour de meilleures performances :
        liste_memo.setHasFixedSize(true)
        // layout manager, décrivant comment les items sont disposés :
        val layoutManager = LinearLayoutManager(this)
        liste_memo.setLayoutManager(layoutManager)
        // contenu d'exemple :
        // adapter :
        memoAdapter = MemoAdapter(listeMemos, this)
        liste_memo.setAdapter(memoAdapter)

        val itemTouchHelper = ItemTouchHelper(
            ItemTouchHelperCallback(memoAdapter!!)
        )
        itemTouchHelper.attachToRecyclerView(liste_memo)
    }

        fun onClick(view: View?) {
            memoAdapter!!.ajouterMemo(MemoDTO(edit_memo!!.text.toString() ))
            liste_memo!!.smoothScrollToPosition(0)
            edit_memo!!.setText("")
        }

}