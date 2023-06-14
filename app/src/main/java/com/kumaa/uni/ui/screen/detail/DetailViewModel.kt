package com.kumaa.uni.ui.screen.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumaa.uni.data.UniRepository
import com.kumaa.uni.model.UniList
import com.kumaa.uni.ui.common.StateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val uniRepository: UniRepository
) : ViewModel() {

    private val _stateHolder: MutableStateFlow<StateHolder<UniList>> = MutableStateFlow(StateHolder.Loading)
    val stateHolder = _stateHolder.asStateFlow()

    fun getUniById(uniId: Int) {
        viewModelScope.launch {
            _stateHolder.value = StateHolder.Loading
            _stateHolder.value = StateHolder.Success(uniRepository.getUniById(uniId))
        }
    }

    fun updateUni(id: Int, newCountValue: Boolean) = viewModelScope.launch {
        uniRepository.updateUni(id, !newCountValue)
            .collect { isUpdated ->
                if (isUpdated) getUniById(id)
            }
    }
}