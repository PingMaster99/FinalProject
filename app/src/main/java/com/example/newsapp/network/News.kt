package com.example.newsapp.network
/**
 * <h1>News</h1>
 *<p>
 * Data class for the format a news article has
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
data class News (
    val created_at: String,
    val title: String,
    val url: String?,
    val author: String,
    val points: String,
    val formatted_date: String = created_at.substring(0,10) // Displays in format: year-month-day
)
