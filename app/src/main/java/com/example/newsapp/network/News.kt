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
    val name: String,
    val description: String,
    val date: Date,
    val formatted_date: String = date.iso,
    val type: List<String>
)
