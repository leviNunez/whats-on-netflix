package com.whatsonnetflix.data.local

import androidx.room.*
import com.google.gson.Gson
import com.whatsonnetflix.domain.CategoryModel
import com.whatsonnetflix.domain.NetflixItemModel
import com.whatsonnetflix.domain.SearchHistoryModel
import java.sql.Date


@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val netflixItemList: NetflixItemList
)

data class NetflixItemList(
    val items: List<NetflixItemEntity>
)

data class NetflixItemEntity(
    val netflixId: Long,
    val title: String,
    val thumbnail: String,
    val poster: String,
    val titleType: String,
    val synopsis: String,
    val year: String,
    val runtime: String,
    val streamingDate: Date,
    val isExpiring: Boolean
)

fun List<CategoryEntity>.asCategoryModel(): List<CategoryModel> =
    map { entity ->
        CategoryModel(
            id = entity.id,
            title = entity.title,
            netflixItemList = entity.netflixItemList.items.asNetflixItemModel()
        )
    }

fun List<NetflixItemEntity>.asNetflixItemModel(): List<NetflixItemModel> =
    map { entity ->
        NetflixItemModel(
            netflixId = entity.netflixId,
            title = entity.title,
            thumbnail = entity.thumbnail,
            poster = entity.poster,
            titleType = entity.titleType,
            synopsis = entity.synopsis,
            year = entity.year,
            runtime = entity.runtime,
            streamingDate = entity.streamingDate,
            isExpiring = entity.isExpiring
        )
    }

fun CategoryEntity.asCategoryModel(): CategoryModel =
    CategoryModel(
        id = id,
        title = title,
        netflixItemList = netflixItemList.items.asNetflixItemModel()
    )


@Entity(
    tableName = "search_history_table",
    indices = [Index(value = ["search_term"], unique = true)]
)
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "search_term") val searchTerm: String,
    val timeStamp: Long,
)

fun List<SearchHistoryEntity>.asDomainModel(): List<SearchHistoryModel> =
    map {
        SearchHistoryModel(
            id = it.id,
            searchTerm = it.searchTerm
        )
    }

class Converters {
    @TypeConverter
    fun timestampToDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun convertNetflixItemListToJSONString(netflixItemList: NetflixItemList): String =
        Gson().toJson(netflixItemList)

    @TypeConverter
    fun convertJSONStringToNetflixItemList(jsonString: String): NetflixItemList =
        Gson().fromJson(jsonString, NetflixItemList::class.java)
}




