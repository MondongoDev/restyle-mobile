package com.example.restyle_mobile

import Beans.Projects
import Persistence.OpenHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide

class ProjectCreation : AppCompatActivity() {
    private lateinit var cancelButton: Button
    private lateinit var dbHelper: OpenHelper
    private lateinit var addImageButton: ImageButton
    private lateinit var projectImageView: ImageView
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_creation_main)

        cancelButton = findViewById(R.id.btnCancelProject)
        addImageButton = findViewById(R.id.add_image_button)
        projectImageView = findViewById(R.id.project_image_view)

        val txtTitulo = findViewById<EditText>(R.id.project_title)
        val txtDescripcion = findViewById<EditText>(R.id.project_description)

        // Abrir la galería cuando se hace clic en el botón
        addImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        val btnSave: Button = findViewById(R.id.btnSaveProject)
        btnSave.setOnClickListener {
            dbHelper = OpenHelper(this)
            val title = txtTitulo.text.toString()
            val description = txtDescripcion.text.toString()
            val imageUri = selectedImageUri?.toString() ?: ""

            // Guardar el proyecto en la base de datos
            val project = Projects(description, imageUri, title)
            dbHelper.newProject(project)

            txtTitulo.text.clear()
            txtDescripcion.text.clear()
            val intent = Intent(this, Portfolio::class.java)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            val intent = Intent(this, Portfolio::class.java)
            startActivity(intent)
        }
    }

    // Manejar el resultado de la selección de imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data

            // Mostrar la imagen seleccionada en el ImageView
            Glide.with(this)
                .load(selectedImageUri)
                .into(projectImageView)

            projectImageView.visibility = ImageView.VISIBLE
        }
    }
}