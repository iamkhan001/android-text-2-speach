package com.nstudio.voicetest

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dividerItemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        rvList.addItemDecoration(dividerItemDecoration)

        val textToSpeech = RobotSpeech(this, object : UtteranceProgressListener() {
            override fun onDone(p0: String?) {

            }

            override fun onError(p0: String?) {


            }

            override fun onStart(p0: String?) {


            }

        })

        val defText = "Hello Good Morning! My name is amy"
        etText.setText(defText)

        val mList = MutableLiveData<ArrayList<Voice>>()

        val onVoiceSelectedListener = object : VoiceAdapter.OnVoiceSelectedListener {
            override fun onVoiceSelected(voice: Voice) {
                var text = etText.text.toString().trim()
                if (text.isEmpty()) {
                    text = defText
                }

                textToSpeech.setVoice(voice, text)
            }
        }

        mList.observe(this, Observer {
            if (it.isEmpty()) {
                tvMessage.text = "No Voices Found"
                return@Observer
            }
            tvMessage.visibility = View.GONE
            rvList.visibility = View.VISIBLE

            val adapter = VoiceAdapter(it, onVoiceSelectedListener)
            rvList.adapter = adapter
        })


        textToSpeech.getVoices(mList)

    }
}