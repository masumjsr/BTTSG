package com.mssoftinc.bttsg.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mssoftinc.bttsg.data.repository.CategoryRepository
import com.mssoftinc.bttsg.data.repository.QuestionRepository
import com.mssoftinc.bttsg.model.sampleCategory
import com.mssoftinc.bttsg.model.sampleQuestions
import com.mssoftinc.bttsg.navigation.categoryIdArg
import com.mssoftinc.bttsg.navigation.titleArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questionRepository: QuestionRepository,
    private val categoryRepository: CategoryRepository
):ViewModel(){
    val title = savedStateHandle.get<String>(titleArg)?:""
    private val categoryId = savedStateHandle.get<Long>(categoryIdArg)?:0L



    val category= categoryRepository.getCategory(categoryId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = sampleCategory[0]
        )
    val questions = questionRepository.getQuestion(categoryId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = sampleQuestions
        )

}

