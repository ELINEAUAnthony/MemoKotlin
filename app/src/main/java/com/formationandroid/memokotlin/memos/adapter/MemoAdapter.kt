package com.formationandroid.memokotlin.memos.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.formationandroid.memokotlin.R
import com.formationandroid.memokotlin.activities.DetailActivity
import com.formationandroid.memokotlin.fragment.DetailFragment
import com.formationandroid.memokotlin.memos.dao.MemoDTO
import com.formationandroid.memokotlin.memos.adapter.MemoAdapter.MemoViewHolder
import com.formationandroid.memokotlin.room.AppDatabase

import java.util.*

class MemoAdapter(listeMemoDTO: List<MemoDTO>, private val activity: AppCompatActivity) : RecyclerView.Adapter<MemoViewHolder>() {

    private var listeMemoDTO: MutableList<MemoDTO> = listeMemoDTO as MutableList<MemoDTO>



    //    Changement de la position d'un item dans la liste
    fun onItemMove(debut: Int, fin: Int): Boolean
    {
        Collections.swap(listeMemoDTO, debut, fin)
        notifyItemMoved(debut, fin)
        return true
    }

    //    Suppression d'un item
    fun onItemDismiss(position: Int)
    {
        if (position > -1) {
            Toast.makeText(activity, " Memo is delete !", Toast.LENGTH_SHORT).show()
            AppDatabase.getInstance(activity).MemoDAO().delete(listeMemoDTO.get(position))
            listeMemoDTO.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun ajouterMemo(memo: MemoDTO)
    {
        AppDatabase.getInstance(activity).MemoDAO().insert(memo)
        listeMemoDTO.add(0, memo)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder
    {
        val viewMemo = LayoutInflater.from(parent.context).inflate(R.layout.memo_item_liste, parent, false)
        return MemoViewHolder(viewMemo, activity)
    }

    override fun onBindViewHolder( holder: MemoViewHolder,position: Int
    ) {
        holder.textViewIntituleMemo!!.text = listeMemoDTO[position].intitule
    }

    override fun getItemCount(): Int
    {
            return listeMemoDTO.size
    }

    inner class MemoViewHolder(itemView: View, activity: AppCompatActivity) : ViewHolder(itemView)
    {
        var textViewIntituleMemo: TextView? = null

        init {

            textViewIntituleMemo = itemView.findViewById(R.id.libelle_memo)
            itemView.setOnClickListener { v ->
                val memoDTO: MemoDTO = listeMemoDTO[adapterPosition]
                val preferences:
                //                        Sauvegarde de la position
                        SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(v.context)
                val editor = preferences.edit()
                editor.putInt("positionMemo", adapterPosition)
                editor.apply()
                Toast.makeText(itemView.context, itemView.context.getString(R.string.main_message_position, adapterPosition + 1), Toast.LENGTH_LONG).show()

                if (activity.findViewById<FrameLayout>(R.id.fragment_detail)  == null) {
                    val intent = Intent(v.context, DetailActivity::class.java)
                    intent.putExtra("memo", memoDTO.intitule)
                    v.context.startActivity(intent)
                } else {
                    //                            Fragment
                    val fragment = DetailFragment()
                    val bundle = Bundle()
                    //                              Passage de parametre dans le fragment
                    bundle.putString("memo", memoDTO.intitule)
                    fragment.arguments = bundle

                    //                              Fragment Manager
                    val fragmentManager =
                        activity.supportFragmentManager

                    //                              Transaction
                    val fragmentTransaction =
                        fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_detail, fragment, "detail")
                    fragmentTransaction.commit()
                }
            }
        }
    }
}