package com.mssoftinc.bttsg.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mssoftinc.bttsg.data.repository.CategoryRepository
import com.mssoftinc.bttsg.model.sampleCategory
import com.mssoftinc.bttsg.navigation.titleArg
import com.mssoftinc.bttsg.navigation.typeArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val categoryRepository: CategoryRepository
):ViewModel(){
    val title = savedStateHandle.get<String>(titleArg)?:""
    val type = savedStateHandle.get<String>(typeArg)?:""

    val category = categoryRepository.getHistoryCategory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = sampleCategory
        )

}

