package com.kryptkode.cardinfofinder.util.ocr

import android.annotation.SuppressLint
import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import timber.log.Timber
import java.io.IOException

class TextReaderAnalyzer(
    private val textFoundListener: (String) -> Unit
) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { process(it, imageProxy) }
    }

    private fun process(image: Image, imageProxy: ImageProxy) {
        try {
            readTextFromImage(InputImage.fromMediaImage(image, 90), imageProxy)
        } catch (e: IOException) {
            Timber.d(e, "Failed to load the image")
        }
    }

    private fun readTextFromImage(image: InputImage, imageProxy: ImageProxy) {
        TextRecognition.getClient()
            .process(image)
            .addOnSuccessListener { visionText ->
                processTextFromImage(visionText)
                imageProxy.close()
            }
            .addOnFailureListener { error ->
                Timber.d(error, "Failed to process the image")
                imageProxy.close()
            }
    }

    private fun processTextFromImage(visionText: Text) {
        for (block in visionText.textBlocks) {
            // You can access whole block of text using block.text
            for (line in block.lines) {
                // You can access whole line of text using line.text
                for (element in line.elements) {
                    textFoundListener(element.text)
                }
            }
        }
    }
}
