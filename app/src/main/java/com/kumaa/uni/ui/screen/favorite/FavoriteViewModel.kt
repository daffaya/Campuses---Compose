package com.kumaa.uni.ui.screen.favorite

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
class FavoriteViewModel @Inject constructor(
    private val uniRepository: UniRepository
) : ViewModel() {

    private val _stateHolder: MutableStateFlow<StateHolder<List<UniList>>> = MutableStateFlow(
        StateHolder.Loading)
    val stateHolder get() = _stateHolder.asStateFlow()

    fun getFavoriteUni() = viewModelScope.launch {
        uniRepository.getFavoriteUni()
            .catch {
                _stateHolder.value = StateHolder.Error(it.message.toString())
            }
            .collect {
                _stateHolder.value = StateHolder.Success(it)
            }
    }

    fun updateUni(id: Int, newCountValue: Boolean) {
        uniRepository.updateUni(id, newCountValue)
        getFavoriteUni()
    }
}