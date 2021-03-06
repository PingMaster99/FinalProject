package com.example.newsapp.network
/**
 * <h1>News</h1>
 *<p>
 * Data class for the format news attributes of an event
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
data class News (
    val name: String,
    val description: String,
    val date: Date,
    val formatted_date: String = date.iso,
    val type: List<String>,
    val hours: String = date.datetime.month,
    val place: String = "CIT - " + date.datetime.day
)
