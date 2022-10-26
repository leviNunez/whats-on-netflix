package com.android.course.whatsonnetflix.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.android.course.whatsonnetflix.domain.NetflixContent
import com.android.course.whatsonnetflix.domain.NetflixContentPreview
import java.sql.Date

@Entity(tableName = "content_preview_table")
data class NetflixContentPreviewEntity(
    @PrimaryKey
    val netflixId: Long,
    val img: String,
    val titleType: String,
    val titleDate: Date,
)


fun List<NetflixContentPreviewEntity>.asDomainModel(): List<NetflixContentPreview> {
    return map {
        NetflixContentPreview(
            netflixId = it.netflixId,
            img = it.img,
            titleType = it.titleType,
            titleDate = it.titleDate
        )
    }
}

@Entity(tableName = "content_detail_table")
data class NetflixContentEntity(
    @PrimaryKey
    val netflixId: Long,
    val title: String,
    val maturityLabel: String,
    val img: String,
    val titleType: String,
    val synopsis: String,
    val year: String,
    val runtime: String,
    val titleDate: Date
)

fun NetflixContentEntity.asDomainModel(): NetflixContent = NetflixContent(
    netflixId = netflixId,
    title = title,
    maturityLabel = maturityLabel,
    img = img,
    titleType = titleType,
    synopsis = synopsis,
    year = year,
    runtime = runtime,
    titleDate = titleDate
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}




