package com.shoppi.app.entitytype

import android.app.Application
import androidx.lifecycle.*
import com.shoppi.app.model.Schedule
import com.shoppi.app.repository.schedule.ScheduleRepository
import com.shoppi.app.ui.basewrapper.BaseViewModel
import com.shoppi.app.ui.common.Event
import kotlinx.coroutines.launch


class ScheduleDataModel(application: Application) : BaseViewModel() {

    private val _basicSchedule = MutableLiveData<List<Schedule>>()
    var basicSchedule: LiveData<List<Schedule>> = _basicSchedule

    private val _openScheduleEvent = MutableLiveData<Event<Schedule>>()
    val openScheduleEvent: LiveData<Event<Schedule>> = _openScheduleEvent

    private val _items = MutableLiveData<Schedule>()

    val items: LiveData<Schedule>
        get() = _items

    init {

        loadSchedule()

    }

    fun refreshupdating() {
        basicSchedule = repository.getAll()
        // loadSchedule() possible
    }

    fun openScheduling(schedule: Schedule) {
        _openScheduleEvent.value = Event(schedule)
    }

    private var repository = ScheduleRepository(application)
    private var schedule = repository.getAll()

    fun getAll(): LiveData<List<Schedule>> {
        return this.schedule
    }

    fun insert(schedule: Schedule) {
        repository.insert(schedule)
    }

    fun delete(schedule: Schedule) {
        repository.delete(schedule)
    }

    /** for debugging only, deletion all */
    @Suppress("unused")
    fun cleardropping() {
        repository.cleardropping()
    }

    private fun loadSchedule() {

        viewModelScope.launch {
            try {
                basicSchedule = this@ScheduleDataModel.schedule

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    class Factory(private val cont: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(ScheduleDataModel::class.java) -> {
                    @Suppress("UNCHECKED_CAST")
                    ScheduleDataModel(cont) as T
                }
                else -> {
                    throw IllegalArgumentException("Fail unable to construct viewmodel: ${modelClass.name}")
                }
            }

        }
    }

}