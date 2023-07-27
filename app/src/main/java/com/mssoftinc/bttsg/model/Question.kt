package com.mssoftinc.bttsg.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val category:Long=0,
    val type:String="",
    val question:String="",
    val answer:Int=0,
    val choice0:String="",
    val choice1:String="",
    val choice2:String="",
    val explanation:String="",
    val imageName:String="",
    val lastAnswer:Int=-1,
    )


val sampleQuestions = arrayListOf(
    Question(question= "This sign means?",
    answer= 1,
choice0= "Sharp deviation",
choice1= "Barrier",
choice2= "Curve Alignment Marks",
explanation = "Answer B. Barrier. Slow down and drive carefully.",
imageName= "barrier"),
    Question(question= "This sign means?",
        answer= 1,
        choice0= "Sharp deviation",
        choice1= "Barrier",
        choice2= "Curve Alignment Marks",
        explanation = "Answer B. Barrier. Slow down and drive carefully.",
        imageName= "barrier"),
    Question(question= "This sign means?",
        answer= 1,
        choice0= "Sharp deviation",
        choice1= "Barrier",
        choice2= "Curve Alignment Marks",
        explanation = "Answer B. Barrier. Slow down and drive carefully.",
        imageName= "barrier"),
    Question(question= "This sign means?",
        answer= 1,
        choice0= "Sharp deviation",
        choice1= "Barrier",
        choice2= "Curve Alignment Marks",
        explanation = "Answer B. Barrier. Slow down and drive carefully.",
        imageName= "barrier"),
)
