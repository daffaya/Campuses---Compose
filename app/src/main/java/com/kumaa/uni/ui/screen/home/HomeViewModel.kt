package com.kumaa.uni.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kumaa.uni.data.UniRepository
import com.kumaa.uni.model.UniList
import com.kumaa.uni.ui.common.StateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val uniRepository: UniRepository
) : ViewModel() {

    private val _stateHolder: MutableStateFlow<StateHolder<List<UniList>>> = MutableStateFlow(
        StateHolder.Loading)
    val stateHolder get() = _stateHolder.asStateFlow()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        uniRepository.findUni(_query.value)
            .catch {
                _stateHolder.value = StateHolder.Error(it.message.toString())
            }
            .collect {
                _stateHolder.value = StateHolder.Success(it)
            }
    }

    fun updateUni(uniId: Int, newCountValue: Boolean) = viewModelScope.launch {
        uniRepository.updateUni(uniId, newCountValue)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}