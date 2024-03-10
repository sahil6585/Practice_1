package com.example.task_eheuristic_shyam

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class Second_Activity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var submit: AppCompatButton
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        submit = findViewById(R.id.submit)
        imageView = findViewById(R.id.click_image)
        user = intent.getSerializableExtra("user") as User

        val nameTextView = findViewById<TextView>(R.id.name_view)
        nameTextView.text = "${user.firstName} ${user.lastName}"
        Picasso.get().load(user.avatar).into(imageView)

        imageView.setOnClickListener {
            showImageSourceDialog()
        }

        submit.setOnClickListener {

            uploadImageToApi()
            val intent = Intent(this@Second_Activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showImageSourceDialog() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->
            when (items[item]) {
                "Take Photo" -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_REQUEST_CODE
                        )
                    } else {
                        dispatchTakePictureIntent()
                    }
                }
                "Choose from Gallery" -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, PICK_IMAGE_REQUEST)
                }
                "Cancel" -> dialog.dismiss()
            }
        })
        builder.show()
    }

    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val selectedImageUri = data?.data
                    imageView.setImageURI(selectedImageUri)
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)

                }
            }
        }
    }

    private fun uploadImageToApi() {

        val byteArrayOutputStream = ByteArrayOutputStream()
        (imageView.drawable).toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required to take photos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    override fun onBackPressed() {

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirm Exit")
        alertDialogBuilder.setMessage("Are you sure you want to go back? Your changes will not be saved.")
        alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
            super.onBackPressed()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
        private const val CAMERA_PERMISSION_REQUEST_CODE = 101
    }
}


/*class Second_Activity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var submit:AppCompatButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        submit = findViewById(R.id.submit)

        val user = intent.getSerializableExtra("user") as User

        val nameTextView = findViewById<TextView>(R.id.name_view)
        imageView = findViewById(R.id.click_image)

        nameTextView.text = "${user.firstName} ${user.lastName}"
        Picasso.get().load(user.avatar).into(imageView)

        imageView.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
        submit.setOnClickListener {

            val intent = Intent(this@Second_Activity, MainActivity::class.java)
            startActivity(intent)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val selectedImageUri = data.data
                imageView.setImageURI(selectedImageUri)
            }
        }
    }


    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }


}*/

  /*{  private lateinit var selectedImageUri: Uri

    private lateinit var imageView: ImageView

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required to take photos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val avatar = intent.getStringExtra("avatar")


        // Display firstName, lastName, and avatar in the layout
        // Initialize click listener for the image view
        imageView.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == "Take Photo" -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                           android.Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    } else {
                        openCamera()
                    }
                }
                items[item] == "Choose from Gallery" -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
                }
                items[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_GALLERY -> {
                if (resultCode == RESULT_OK && data != null) {
                    selectedImageUri = data.data!!
                    updateImageInApi()
                }
            }
            REQUEST_IMAGE_CAMERA -> {
                if (resultCode == RESULT_OK && data != null) {
                    val imageBitmap = data.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                    // Save image to storage or update in API directly
                }
            }
        }
    }

    private fun updateImageInApi() {
        // Implement logic to upload the image to the API
        // You can use libraries like Retrofit to make network requests
        // Here's a simplified example using Volley:
        val queue = Volley.newRequestQueue(this)
        val url = "your_api_endpoint_here"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Image updated successfully
                Toast.makeText(this, "Image updated successfully", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // Handle error
                Toast.makeText(this, "Error updating image: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                // Pass image data here, such as selectedImageUri.toString()
                return params
            }
        }

        queue.add(stringRequest)
    }

    companion object {
        private const val REQUEST_IMAGE_GALLERY = 100
        private const val REQUEST_IMAGE_CAMERA = 101
    }

}*/