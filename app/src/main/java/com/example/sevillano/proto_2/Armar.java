package com.example.sevillano.proto_2;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Armar extends AppCompatActivity {

    private StorageReference mStorageRef;
    VideoView video;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armar);
        mediaController = new MediaController(this);
        video = (VideoView) findViewById(R.id.video);
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://prototipo-pdg.appspot.com/video.mp4");
        cargarVideo();
    }

    public void cargarVideo() {

        String url = "https://firebasestorage.googleapis.com/v0/b/prototipo-pdg.appspot.com/o/video.mp4?alt=media&token=71c6d3a5-1f68-49ff-a3fa-92f3429709ce";
        Uri uri = Uri.parse(url);
        video.setVideoURI(uri);
        mediaController.setAnchorView(video);
        mediaController.setMediaPlayer(video);
        video.setMediaController(mediaController);
        video.start();

    }


}
