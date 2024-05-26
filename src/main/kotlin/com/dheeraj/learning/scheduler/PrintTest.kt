package com.dheeraj.learning.scheduler

import java.awt.Desktop
import java.io.File
import javax.print.Doc
import javax.print.DocFlavor
import javax.print.DocPrintJob
import javax.print.PrintException
import javax.print.PrintService
import javax.print.PrintServiceLookup
import javax.print.SimpleDoc
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.PrintRequestAttributeSet
import javax.print.event.PrintJobAdapter
import javax.print.event.PrintJobEvent
import java.io.FileInputStream

fun main() {
    printFile1("C:\\dev\\code\\scheduler\\src\\main\\resources\\color_print_sample.pdf");
}

fun printFile1(filePath: String) {
    val file = File(filePath)
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.PRINT)) {
        Desktop.getDesktop().print(file)

    } else {
        println("Printing is not supported on this platform.")
    }
}

fun printImageFile(filePath: String) {
    // Locate the default print service
    val printService: PrintService? = PrintServiceLookup.lookupDefaultPrintService()
    if (printService == null) {
        println("No default print service found.")
        return
    }

    // Create a FileInputStream from the given file path
    val fileInputStream = FileInputStream(filePath)

    // Create a Doc object to hold the file data
    val doc: Doc = SimpleDoc(fileInputStream, DocFlavor.INPUT_STREAM.JPEG, null)

    // Create a print job from the print service
    val printJob: DocPrintJob = printService.createPrintJob()

    // Add a listener to handle print job events
    printJob.addPrintJobListener(object : PrintJobAdapter() {
        override fun printJobCompleted(pje: PrintJobEvent?) {
            println("Print job completed successfully.")
        }

        override fun printJobFailed(pje: PrintJobEvent?) {
            println("Print job failed.")
        }

        override fun printJobCanceled(pje: PrintJobEvent?) {
            println("Print job canceled.")
        }

        override fun printJobNoMoreEvents(pje: PrintJobEvent?) {
            println("No more events.")
        }
    })

    // Print the document
    try {
        val printAttributes: PrintRequestAttributeSet = HashPrintRequestAttributeSet()
        printJob.print(doc, printAttributes)
    } catch (pe: PrintException) {
        println("Print failed: ${pe.message}")
    } catch (e: Exception) {
        println("Other exception ${e.message}")
    }
}