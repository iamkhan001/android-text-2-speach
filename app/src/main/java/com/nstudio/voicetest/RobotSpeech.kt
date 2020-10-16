package com.nstudio.voicetest

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import androidx.lifecycle.MutableLiveData
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class RobotSpeech(
    private val context: Context,
    private val onSpeakListener: UtteranceProgressListener
) {

    private var selectedVoice: Voice? = null

    companion object {
        private const val TAG = "RobotSpeech"
    }

    private var text2Speech: TextToSpeech? = null

    fun setVoice(voice: Voice, text: String) {
        text2Speech?.stop()

        if (selectedVoice != null) {
            if (selectedVoice!!.name == voice.name) {
                speak(text)
                return
            }
        }
        selectedVoice = voice

        text2Speech = TextToSpeech(context,
            TextToSpeech.OnInitListener {

                if(it != TextToSpeech.ERROR) {
                    text2Speech?.setOnUtteranceProgressListener(onSpeakListener)

                    val features = HashSet<String>()
                    features.add("female")
                    text2Speech?.voice = voice

                    speak(text)

                }

            })
    }

    fun getVoices(mData: MutableLiveData<ArrayList<Voice>>)  {

        text2Speech = TextToSpeech(context, TextToSpeech.OnInitListener {

            if (it != TextToSpeech.ERROR) {

                val features = HashSet<String>()
                features.add("female")

                val vList = ArrayList<Voice>()

                try {
                    val list = text2Speech?.voices

                    if (list != null && list.size > 0) {
                        for (lang in list) {
                            vList.add(lang)
                        }
                    }
                }catch (e: Exception) {
                    e.printStackTrace()
                }

                mData.postValue(vList)
            }
        })
    }

    fun speak(text: String, id: String) {
        text2Speech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, id)
    }

    fun stop(){
        text2Speech?.stop()
    }

    fun speakAdd(text: String) {
        text2Speech?.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    fun speak(text: String) {
        text2Speech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}