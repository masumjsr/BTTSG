package com.mssoftinc.bttsg.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mssoftinc.bttsg.R
import com.mssoftinc.bttsg.data.repository.CategoryRepository
import com.mssoftinc.bttsg.data.repository.PrayerSettingRepository
import com.mssoftinc.bttsg.data.repository.QuestionRepository
import com.mssoftinc.bttsg.model.Category
import com.mssoftinc.bttsg.model.Question
import com.mssoftinc.bttsg.model.UserData
import com.mssoftinc.bttsg.utils.CONFIGURATION
import com.mssoftinc.bttsg.utils.DATA
import com.mssoftinc.bttsg.utils.INITIAL_SETUP
import com.mssoftinc.bttsg.utils.PRACTICE
import com.mssoftinc.bttsg.utils.RANDOM_QUESTION
import com.mssoftinc.bttsg.utils.TEST_QUESTION
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val questionRepository: QuestionRepository,
    private val settingRepository: PrayerSettingRepository,
    @ApplicationContext
    context: Context,
):ViewModel(){
    fun onThemeChange(theme: Int) {
        viewModelScope.launch {
            settingRepository.updateTheme(theme)
        }
    }

    init {

        val preference=context.getSharedPreferences(CONFIGURATION, Context.MODE_PRIVATE)

        val isSetupCompleted = preference.getBoolean(INITIAL_SETUP,false)
        viewModelScope.launch {
            if(!isSetupCompleted){
                loadJSONFromAsset(context,R.raw.practice_quetions, type = PRACTICE,categoryRepository,questionRepository)
                loadJSONFromAsset(context,R.raw.data, type = DATA,categoryRepository,questionRepository)
                loadJSONFromAsset(context,R.raw.random_questions, type = RANDOM_QUESTION,categoryRepository,questionRepository)
                loadJSONFromAsset(context,R.raw.testquestions, type = TEST_QUESTION,categoryRepository,questionRepository)

                val editablePreferences=preference.edit()
                editablePreferences.putBoolean(INITIAL_SETUP,true)
                editablePreferences.apply()
            }
        }
    }

    val settings: StateFlow<UserData> = settingRepository
        .prayerPreferenceData

        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserData("",0)
        )





}

suspend fun loadJSONFromAsset(
    context: Context,
    id: Int,
    type:String,
    categoryRepository: CategoryRepository,
    questionRepository: QuestionRepository
) {
    val json = try {
        val `is`: InputStream =  context.resources.openRawResource(id)
        val size: Int = withContext(Dispatchers.IO) {
            `is`.available()
        }
        val buffer = ByteArray(size)
        withContext(Dispatchers.IO) {
            `is`.read(buffer)
        }
        withContext(Dispatchers.IO) {
            `is`.close()
        }
        String(buffer, Charset.forName("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
      null
    }

    try{
       json?.let { jsn->
           val rootArray = JSONArray(jsn)

            for (i in 0 until rootArray.length()) {
                val categoryObject:JSONObject = rootArray.getJSONObject(i)
                val title=categoryObject.getString("type")
                val imageName=categoryObject.getString("imageName")
                val questionArray:JSONArray = categoryObject.getJSONArray("questions")
                val category=Category(title=title, imageName = imageName, type = type, last_attempt = 0, total_question = questionArray.length())

                val categoryId=categoryRepository.upsertCategory(category)
                val questionList=ArrayList<Question>()
                for(j in 0 until questionArray.length()) {
                    val questionObject:JSONObject =questionArray.getJSONObject(j)
                    
                    val question=questionObject.getString("question")
                    val answer=questionObject.getInt("answer")
                    val choice0=questionObject.getString("choice0")
                    val choice1=questionObject.getString("choice1")
                    val choice2=questionObject.getString("choice2")
                    val explanation=questionObject.getString("explaination")
                    val image=questionObject.getString("imageName")
                    questionList.add(Question(0,categoryId,type,question,answer, choice0, choice1, choice2,explanation,image))
                }
                questionRepository.upsertQuestion(questionList)


           }

       }
    }
    catch (ex: JSONException) {
        Log.i("123321", "loadJSONFromAsset: ${ex.message}")
    }

}