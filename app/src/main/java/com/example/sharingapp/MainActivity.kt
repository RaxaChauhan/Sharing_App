package com.example.sharingapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sharingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try{
            binding.imageView.setImageURI(galleryUri)
            selectedImageUri = galleryUri
        }catch(e:Exception){
            e.printStackTrace()
        }

    }

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.shareButton.setOnClickListener {
            // Check if an image is selected
            if (selectedImageUri != null) {
                shareImage(selectedImageUri!!)
            }
            else{
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareImage(imageUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        shareIntent.type = "image/*"

        // Use createChooser to show a dialog allowing the user to choose an app
        val chooser = Intent.createChooser(shareIntent, "Share Image")

        // Verify that the intent will resolve to at least one activity
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }
}