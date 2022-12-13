package com.example.externaldatabase

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import com.example.externaldatabase.databinding.ActivityShayriImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream

class ShayriImageActivity : AppCompatActivity() {
    lateinit var binding: ActivityShayriImageBinding
    var i = 0
    var galleryCode = 100
    var QuotesImage = intArrayOf(
        R.drawable.img8,
        R.drawable.img9,
        R.drawable.img10,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShayriImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickEvent()
    }
    private fun clickEvent() {

        var shayri = intent.getStringExtra("shayriimage")
        var shayricategory = intent.getStringExtra("category")

        binding.txtShayriimage.text = shayri
        binding.txtShayricategory.text = shayricategory


        binding.imgshayriimageback.setOnClickListener {
            finish()
        }
        binding.imgChangeBg.setOnClickListener {
            binding.imgBackground.setImageResource(QuotesImage[i])
            i++
            if (i == 9) {
                i = 0
            }
        }
        binding.relative1.setOnClickListener {
//            var dialog = ProgressDialog(this)
//            dialog.setMessage("Please wait...")
//            dialog.show()

            val bitmap = binding.downloadArea.drawToBitmap()
            var outStream: FileOutputStream? = null
            val sdcard = Environment.getExternalStorageDirectory()
            val dir = File(sdcard.absolutePath + "/DCIM/Camera")
            dir.mkdirs()
            val fileName = String.format("%d.jpg", System.currentTimeMillis())
            val outFile = File(dir, fileName)
            outStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
            Toast.makeText(this, "Saved In Gallery", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            intent.data = Uri.fromFile(outFile)
            sendBroadcast(intent)
        }
        binding.dislike.setOnClickListener {
            binding.dislike.visibility = View.GONE
            binding.like.visibility = View.VISIBLE
            Toast.makeText(this, "Added to Favourite", Toast.LENGTH_SHORT).show()
        }
        binding.like.setOnClickListener {
            binding.dislike.visibility = View.VISIBLE
            binding.like.visibility = View.GONE
        }
        binding.relative3.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody: String = binding.txtShayriimage.text.toString()
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
        binding.relative4.setOnClickListener {
            Toast.makeText(this, "Quote Copied in Clipboard.", Toast.LENGTH_SHORT).show()
            val copyQuote: String = binding.txtShayriimage.text.toString()
            val clipboard =
                this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Text Copty", copyQuote)
            clipboard.setPrimaryClip(clip)
        }
        binding.relative5.setOnClickListener {
            gallery()
        }
    }

    private fun gallery() {
        val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), galleryCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == galleryCode) {
            if (resultCode == -1 && data != null) {
                val uri = data.data
                binding.imgBackground.setImageURI(uri)
            }
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()
        }
    }


}


