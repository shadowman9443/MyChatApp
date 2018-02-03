package com.example.aunogarafat.mychatapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import com.example.aunogarafat.mychatapp.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File

class SettingsActivity : AppCompatActivity() {
    var mDatabase :DatabaseReference?=null
    var mCurrentUser:FirebaseUser?=null
    var mStoregRef:StorageReference?=null


    var GALLAERRYID:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mCurrentUser=FirebaseAuth.getInstance().currentUser
        mStoregRef=FirebaseStorage.getInstance().reference
        var userID=mCurrentUser!!.uid

        mDatabase=FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userID)
        mDatabase!!.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot?) {

                var displayName=dataSnapshot!!.child("display_name").value
                var image=dataSnapshot!!.child("image").value
                var userStatus=dataSnapshot!!.child("status").value
                var thumbNail=dataSnapshot!!.child("thumb_image").value

                profile_name_txt.text=displayName.toString()
                settings_status_txt.text=userStatus.toString()

                if(!image!!.equals("default")){
                    Picasso.with(applicationContext)
                            .load(image.toString())
                            .placeholder(R.drawable.profile_img)
                            .into(profile_image)
                }


            }
            override fun onCancelled(datasnapshotError: DatabaseError?) {

            }

        })
        sttngs_status_btn.setOnClickListener {
            var intent=Intent(this,StatusActivity::class.java)
            intent.putExtra("status",settings_status_txt.text.toString().trim())
            startActivity(intent)
        }
        change_img_btn.setOnClickListener {
            var galleryIntent=Intent()
            galleryIntent.type="image/*"
            galleryIntent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent,"Choose Image"),GALLAERRYID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if(requestCode==GALLAERRYID && resultCode== Activity.RESULT_OK){
           var Image:Uri=data!!.data
           CropImage.activity(Image)
                   .setAspectRatio(1,1)
                   .start(this)
       }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
           var result = CropImage.getActivityResult(data);
            if(resultCode== Activity.RESULT_OK){
                var resultUri=result.uri
                var userId=mCurrentUser!!.uid
                var imageFile= File(resultUri.path)
                var comprsImage= Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(75)
                        .compressToBitmap(imageFile)
                var byteArray=ByteArrayOutputStream();
                comprsImage.compress(Bitmap.CompressFormat.JPEG,100,byteArray)

                var thumbByteArray:ByteArray
                   thumbByteArray=byteArray.toByteArray()


                var filePath=mStoregRef!!.child("image")
                        .child(userId+".jpg")

                var thumBFilePath=mStoregRef!!.child("image")
                        .child("thumb_image")
                        .child(userId+".jpg")
                filePath.putFile(resultUri)
                        .addOnCompleteListener {
                            task: Task<UploadTask.TaskSnapshot> ->
                            if (task.isSuccessful){
                                var downloadUrl=task.result.downloadUrl.toString()

                                var uploadTask:UploadTask=thumBFilePath.putBytes(thumbByteArray)
                                uploadTask.addOnCompleteListener {
                                    task: Task<UploadTask.TaskSnapshot> ->
                                    var dthumbdUrl=task.result.downloadUrl.toString()
                                    if (task.isSuccessful){
                                        var userobject=HashMap<String,Any>()

                                        userobject.put("image",downloadUrl)
                                        userobject.put("thumb_image",dthumbdUrl)
                                        mDatabase!!.updateChildren(userobject)
                                                .addOnCompleteListener {
                                                    task:Task<Void> ->
                                                    if(task.isSuccessful) {
                                                        Toast.makeText(this,"Profile Image saved", Toast.LENGTH_LONG).show()


                                                    }else {
                                                        Toast.makeText(this,"Error"+task.exception,Toast.LENGTH_LONG).show()
                                                    }
                                                }
                                    }else{
                                        Toast.makeText(this,"Error"+task.exception,Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }



            }
        }
    }
}
