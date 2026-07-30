package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

data class AppNotification(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

object NotificationRepository {
    private var prefs: SharedPreferences? = null
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val listType = Types.newParameterizedType(List::class.java, AppNotification::class.java)
    private val adapter = moshi.adapter<List<AppNotification>>(listType)

    private val _notifications = MutableStateFlow<List<AppNotification>>(emptyList())
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    fun init(context: Context) {
        if (prefs == null) {
            prefs = context.applicationContext.getSharedPreferences("notifications_prefs", Context.MODE_PRIVATE)
            loadFromPrefs()
        }
    }

    private fun loadFromPrefs() {
        val json = prefs?.getString("notifications_data", "[]") ?: "[]"
        try {
            val list = adapter.fromJson(json) ?: emptyList()
            _notifications.value = list.sortedByDescending { it.timestamp }
        } catch (e: Exception) {
            _notifications.value = emptyList()
        }
    }

    private fun saveToPrefs(list: List<AppNotification>) {
        val json = adapter.toJson(list)
        prefs?.edit()?.putString("notifications_data", json)?.apply()
    }

    fun addNotification(title: String, body: String) {
        _notifications.update { current ->
            val newList = (listOf(AppNotification(title = title, body = body)) + current)
                .sortedByDescending { it.timestamp }
            saveToPrefs(newList)
            newList
        }
    }

    fun markAsRead(id: String) {
        _notifications.update { current ->
            val newList = current.map { if (it.id == id) it.copy(isRead = true) else it }
            saveToPrefs(newList)
            newList
        }
    }
    
    fun markAllAsRead() {
        _notifications.update { current ->
            val newList = current.map { it.copy(isRead = true) }
            saveToPrefs(newList)
            newList
        }
    }
}
