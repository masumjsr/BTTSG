package com.mssoftinc.bttsg.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String="",
    val imageName:String="",
    val type:String="",
    val last_attempt:Int=0,
    val total_question:Int=0,
    val lastModified:Long=-1
)


val sampleCategory = arrayListOf(
    Category(title="Offences and Demerit Points", imageName = "deduction", total_question = 16, last_attempt = 12),
    Category(title="Mandatory Signs", imageName = "give_way"),
    Category(title="Prohibitory Signs", imageName = "no_entry_for_all_vechicles"),
    Category(title="Warning Signs", imageName = "steep_downward_slope"),
    Category(title="Offences and Demerit Points", imageName = "deduction"),
    Category(title="Offences and Demerit Points", imageName = "deduction"),
    Category(title="Offences and Demerit Points", imageName = "deduction"),
    Category(title="Offences and Demerit Points", imageName = "deduction"),
    Category(title="Offences and Demerit Points", imageName = "deduction"),
)
