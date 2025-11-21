package com.zoe.wan.android.example.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.data.User
import com.zoe.wan.android.example.room.AppRoomDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class HotKeyViewModel : LoadingVM() {

    var livedata: LiveData<List<User>>? = null

    override fun request(isRefreshing: Boolean?) {

    }

    fun insertUsers() {
        val users = mutableListOf<User>()
        repeat(10) {
            users.add(User((it + 1).toLong(), "user.name${it + 1}", "user.email${it + 1}", it + 1, "user.phone${it + 1}"))
        }
        viewModelScope.launch {
            val insert = launch(Dispatchers.IO) {
                val count = AppRoomDataBase.databaseInstance
                    .userDao()
                    .upsert(users)
//                val count = AppRoomDataBase.databaseInstance
//                    .userDao()
//                    .insert(users.first())
                println("djj count=${count.size}")
            }

            val query = launch(Dispatchers.IO) {
                livedata = AppRoomDataBase.databaseInstance
                    .userDao()
                    .getAllUsersLiveData()
            }

            insert.join()
            query.join()
//            joinAll(insert, query)
        }
    }

}