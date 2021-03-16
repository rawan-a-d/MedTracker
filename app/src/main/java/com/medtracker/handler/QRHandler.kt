package com.medtracker.handler

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.medtracker.R

object QRHandler {
    public fun StringToQRCode(context: Context?, input: String?): Bitmap? {
        val multiFormatWriter = MultiFormatWriter()
        return try {
            val bitMatrix =
                multiFormatWriter.encode(input, BarcodeFormat.QR_CODE, 400, 400)
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: WriterException) {
            Toast.makeText(context, R.string.error_generate_qrcode, Toast.LENGTH_LONG).show()
            e.printStackTrace()
            null
        }
    }
}