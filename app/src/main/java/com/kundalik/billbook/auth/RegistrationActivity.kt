package com.kundalik.billbook.auth

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelUuid
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kundalik.billbook.MainActivity
import com.kundalik.billbook.R
import com.kundalik.billbook.databinding.ActivityRegistrationBinding
import com.kundalik.billbook.model.Users
import java.util.Date

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.tvAlreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }

        binding.ivUserImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }




        binding.btnContinueRegistration.setOnClickListener {
            if (selectedImg == null) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Select Profile Image",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.etUserName.text!!.isEmpty() && binding.etUserEmail.text!!.isEmpty() && binding.etUserMobile.text!!.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Enter all the fields!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                uploadUserProfileImage()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            selectedImg = data.data!!
            binding.ivUserImage.setImageURI(selectedImg)

        }
    }

    private fun uploadUserProfileImage() {

        val dialog = Dialog(this@RegistrationActivity)
        dialog.setContentView(R.layout.loading_layout)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()

        val databaseReference =
            storage.reference.child("ProfileImages").child(Date().time.toString())
        databaseReference.putFile(selectedImg)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    databaseReference.downloadUrl
                        .addOnSuccessListener { task ->
                            uploadUserInformation(task.toString())
                            dialog.dismiss()
                        }
                }
            }
    }

    private fun uploadUserInformation(imageUrl: String) {
        val uId = auth.uid.toString()

        val uName = binding.etUserName.text!!.toString()
        val uEmail = binding.etUserEmail.text!!.toString()
        val uNumber = auth.currentUser!!.phoneNumber.toString()
        val uImage = imageUrl

        val dialog = Dialog(this@RegistrationActivity)
        dialog.setContentView(R.layout.loading_layout)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()

        val users = Users(uId, uName, uEmail, uNumber, uImage)

        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(users)
            .addOnSuccessListener {
                startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                finish()
                dialog.dismiss()
            }
            .addOnFailureListener {
                Toast.makeText(this@RegistrationActivity, "Error ${it.message}", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
    }


}