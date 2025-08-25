package com.wangzs.module.ai.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wangzs.lib.base.mvvm.viewmodel.BaseViewModel
import com.wangzs.lib.log.KLog
import com.wangzs.module.ai.bean.GenerateRequest
import com.wangzs.module.ai.repository.AiDataRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AiViewModel(state: SavedStateHandle) : BaseViewModel() {
    private val repository = AiDataRepository()
    val inputText = MutableLiveData<String>("你是谁")
    private val _responseText = MutableLiveData<String>().apply { value = "" }
    val responseText: LiveData<String> = _responseText

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _mText10 = MutableLiveData<String>().apply { value = "" }
    val mText10: LiveData<String> = _mText10

    fun onSubmitClick() {
        inputText.value?.takeIf { it.isNotBlank() }?.let { text ->
            generateText(text)
        }
    }

    fun generateText(prompt: String) {
        _responseText.value = ""
        _isLoading.value = true

        val requestModel = GenerateRequest(
            model = "qwen3:4b", prompt = prompt, stream = true
        )
        var currentSegmentIndex = 0
        viewModelScope.launch {
            repository.generateStream(requestModel).onEach { response ->
                _responseText.value += response
                val segmentLength = 10
                val totalSegments = getTotalSegments(_responseText.value, segmentLength)
                for (i in 0 until totalSegments) {
                    val segment = getTextSegment(_responseText.value, i, segmentLength)
                    _mText10.value = segment
                }
                _isLoading.value = false
            }.onCompletion {
                _responseText.value += "\n\n[Response completed]"
                _isLoading.value = false
            }.catch { e ->
                Log.e("OllamaStream", "Request failed", e)
                _responseText.value += "Error: ${e.message}\n"
                _isLoading.value = false
            }.collect()
        }
    }

    fun clearResponse() {
        _responseText.value = ""
        val text = """
        庆历四年春，滕子京谪守巴陵郡。越明年，政通人和，百废具兴，乃重修岳阳楼，增其旧制，刻唐贤今人诗赋于其上，属予作文以记之。(具 通：俱)
　　予观夫巴陵胜状，在洞庭一湖。衔远山，吞长江，浩浩汤汤，横无际涯，朝晖夕阴，气象万千，此则岳阳楼之大观也，前人之述备矣。然则北通巫峡，南极潇湘，迁客骚人，多会于此，览物之情，得无异乎？
　　若夫淫雨霏霏，连月不开，阴风怒号，浊浪排空，日星隐曜，山岳潜形，商旅不行，樯倾楫摧，薄暮冥冥，虎啸猿啼。登斯楼也，则有去国怀乡，忧谗畏讥，满目萧然，感极而悲者矣。(隐曜 一作：隐耀；淫雨 通：霪雨)
　　至若春和景明，波澜不惊，上下天光，一碧万顷，沙鸥翔集，锦鳞游泳，岸芷汀兰，郁郁青青。而或长烟一空，皓月千里，浮光跃金，静影沉璧，渔歌互答，此乐何极！登斯楼也，则有心旷神怡，宠辱偕忘，把酒临风，其喜洋洋者矣。
　　嗟夫！予尝求古仁人之心，或异二者之为，何哉？不以物喜，不以己悲，居庙堂之高则忧其民，处江湖之远则忧其君。是进亦忧，退亦忧。然则何时而乐耶？其必曰：“先天下之忧而忧，后天下之乐而乐”乎！噫！微斯人，吾谁与归？
　　时六年九月十五日。
    """.trimIndent()
        val cleanText = text.replace("\\s+".toRegex(), "")
        val segmentLength = 10
        val totalSegments = getTotalSegments(cleanText, segmentLength)
        for (i in 0 until totalSegments) {
            val segment = getTextSegment(cleanText, i, segmentLength)
            KLog.e(TAG, "分段 ${i + 1}/$totalSegments: $segment")
        }

    }


    /**
     * 获取指定分段的文本
     */
    fun getTextSegment(text: String?, segmentIndex: Int, segmentLength: Int): String {
        if (text.isNullOrEmpty() || segmentLength <= 0) return ""

        val startIndex = segmentIndex * segmentLength
        if (startIndex >= text.length) return ""

        val endIndex = minOf(startIndex + segmentLength, text.length)
        return text.substring(startIndex, endIndex)
    }

    /**
     * 计算总分段数
     */
    fun getTotalSegments(text: String?, segmentLength: Int): Int {
        if (text.isNullOrEmpty() || segmentLength <= 0) return 0
        return Math.ceil(text.length.toDouble() / segmentLength).toInt()
    }

}