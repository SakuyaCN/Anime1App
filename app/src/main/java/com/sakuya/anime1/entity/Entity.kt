package com.sakuya.anime1.entity

data class AnimeEntity(var url:String,
                       var title:String,
                       var img :Int)
data class AnimeEntityList(var list: ArrayList<AnimeEntity>)
