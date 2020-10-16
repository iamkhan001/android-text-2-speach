package com.nstudio.voicetest

import android.speech.tts.Voice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VoiceAdapter (private val list: ArrayList<Voice>, private val onVoiceSelectedListener: OnVoiceSelectedListener): RecyclerView.Adapter<VoiceAdapter.MyViewHolder>() {


    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvInfo: TextView = view.findViewById(R.id.tvInfo)
        val imgPlay: ImageView = view.findViewById(R.id.imgPlay)

    }


    interface OnVoiceSelectedListener{
        fun onVoiceSelected(voice: Voice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_voice, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val voice = list[position]

        holder.tvName.text = voice.name

        val online = if (voice.isNetworkConnectionRequired) {
            "Online"
        }else {
            "Offline"
        }

        val info = voice.locale.displayName+ " | " +online
        holder.tvInfo.text = info

        holder.imgPlay.setOnClickListener {
            onVoiceSelectedListener.onVoiceSelected(voice)
        }

    }

}