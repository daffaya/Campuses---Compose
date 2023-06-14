package com.kumaa.uni.data

import com.kumaa.uni.model.UniData
import com.kumaa.uni.model.UniList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class UniRepository {

    private val listUni = mutableListOf<UniList>()

    init {
        if (listUni.isEmpty()) {
            listUni.addAll(UniData.dummyUni)
        }
    }

    fun getAllUni(): Flow<List<UniList>> {
        return flowOf(listUni)
    }

    fun getUniById(uniId: Int): UniList {
        return listUni.first {
            it.id == uniId
        }
    }

    fun updateUni(id: Int, newCountValue: Boolean): Flow<Boolean> {
        val index = listUni.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val uni = listUni[index]
            listUni[index] = uni.copy(isFavorite = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavoriteUni(): Flow<List<UniList>> {
        return getAllUni()
            .map { list ->
                list.filter { uniList ->
                    uniList.isFavorite
                }
            }.flowOn(Dispatchers.Default)
    }

    fun findUni(query: String): Flow<List<UniList>> {
        return getAllUni()
            .map { list ->
                list.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
    }

    companion object {
        @Volatile
        private var instance: UniRepository? = null

        fun getInstance(): UniRepository =
            instance ?: synchronized(this) {
                UniRepository().apply {
                    instance = this
                }
            }
    }
}