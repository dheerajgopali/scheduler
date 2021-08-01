package com.dheeraj.learning.scheduler

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.stage.Stage
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import tornadofx.launch
import java.lang.Exception
import java.nio.file.Paths
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val CSV_File_Path = "C:/Program Files (x86)/PaperCut Print Logger/logs/csv/papercut-print-log-all-time.csv"
    // read the file
    val p = Paths.get(CSV_File_Path)
    println(p.toAbsolutePath())
    val reader = Files.newBufferedReader(p)
    // parse the file into csv values
    val csvParser = CSVParser(reader, CSVFormat.DEFAULT)
    val csvRecord = csvParser.last()
    println(csvRecord.get(0))
    val lastDate = csvRecord[0]
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    val date = sdf.parse(lastDate)
    val curDate = Date()
    println(date)
    println(curDate)
    val diff: Long = curDate.getTime() - date.getTime()
    val seconds = diff / 1000
    val minutes = seconds / 60
    val sec = seconds%60
    val hours = minutes / 60
    val min = minutes%60
    val days = hours / 24
    val hr = hours%24

    println("It has been $days days $hr hours $min minutes $sec seconds since a print is given")
    val msg: String = if(days>5)
        "Take a print now. It has been $days days $hr hours $min minutes $sec seconds since a print is given"
    else
        "You dont have to take a print yet."

    launch<LabelExperiments>(arrayOf(msg))
}

class LabelExperiments : Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Should I take print now?"
        val args = parameters.raw.get(0)
        val label = Label(args)
        label.style = "-fx-font-size: 30; -fx-font-weight: bold"
        label.isWrapText = true
        val scene = Scene(label, 500.0, 200.0)
        primaryStage.scene = scene
        primaryStage.show()
    }
}