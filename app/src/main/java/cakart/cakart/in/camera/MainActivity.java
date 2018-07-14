package cakart.cakart.in.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends Activity {

    Button button,vid_button;
    ImageView imageView;
    static final int CAM_REQUEST = 1;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.button);
        imageView=(ImageView)findViewById(R.id.image_view);
        videoView=(VideoView)findViewById(R.id.video_view);
        vid_button = (Button) findViewById(R.id.video);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file=getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,CAM_REQUEST);


            }
        });
        vid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera_intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                File video_file=getVidFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(video_file));
                camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                startActivityForResult(camera_intent,2);
            }
        });


    }
    private File getFile(){
        File folder = new File(Environment.getExternalStorageDirectory()+"/camera_app");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image_file=new File(folder,"cam_image.jpg");
        return  image_file;
    }

    private File getVidFile(){
        File folder = new File(Environment.getExternalStorageDirectory()+"/video_app");
        if(!folder.exists()){
            folder.mkdir();
        }
        File video_file=new File(folder,"sample_video.mp4");
        return  video_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1) {
            String path = Environment.getExternalStorageDirectory() + "/camera_app/cam_image.jpg";
            imageView.setImageDrawable(Drawable.createFromPath(path));
        }else{
            if(resultCode==RESULT_OK) {
                Toast.makeText(getApplicationContext(),"Video Successfully Recorded",Toast.LENGTH_SHORT).show();
               String path = Environment.getExternalStorageDirectory() + "/video_app/sample_video.mp4";
                videoView.setVideoPath(path);
            }
            else {
                Toast.makeText(getApplicationContext(),"The capture Failed Recorded",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
