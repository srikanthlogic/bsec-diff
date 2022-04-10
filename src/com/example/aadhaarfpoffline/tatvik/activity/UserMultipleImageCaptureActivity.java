package com.example.aadhaarfpoffline.tatvik.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.camerakit.CameraKitView;
import com.example.aadhaarfpoffline.tatvik.GetDataService;
import com.example.aadhaarfpoffline.tatvik.LocaleHelper;
import com.example.aadhaarfpoffline.tatvik.ProgressRequestBody;
import com.example.aadhaarfpoffline.tatvik.R;
import com.example.aadhaarfpoffline.tatvik.config.RetrofitClientInstance;
import com.example.aadhaarfpoffline.tatvik.network.MultipleFaceImageUploadResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserFaceMatchStatusUpdatePostResponse;
import com.example.aadhaarfpoffline.tatvik.network.UserVotingStatusUpdatePostResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/* loaded from: classes2.dex */
public class UserMultipleImageCaptureActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private CameraKitView cameraKitView;
    private Button captureCamera;
    private TextView clickText;
    Context context;
    private Button frontCamera;
    int i;
    ImageView image;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    private Button nextButton;
    ProgressBar progressBar;
    ProgressBar progressBarCirle;
    Resources resources;
    private TextView resultText;
    private Button reverseCamers;
    private Button startCamera;
    private Button uploadButton;
    int count = 0;
    String imagePath = "";
    String votername = "";
    String district = "";
    String blockno = "";
    String blockid = "";
    String voterid = "";
    String VoterIdentificationimage = "";
    String voteridtype = "";
    Double conf = Double.valueOf(-1.0d);
    List<File> fileList = new ArrayList();
    Boolean found = false;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        String lan = LocaleHelper.getLanguage(this);
        this.context = LocaleHelper.setLocale(this, lan);
        this.resources = this.context.getResources();
        Intent intent = getIntent();
        this.voterid = intent.getStringExtra("voter_id");
        this.votername = intent.getStringExtra("voter_name");
        this.district = intent.getStringExtra("district");
        this.blockno = intent.getStringExtra("blockno");
        this.blockid = intent.getStringExtra("blockid");
        this.VoterIdentificationimage = intent.getStringExtra("voteridentificationimage");
        this.voteridtype = intent.getStringExtra("iddoctype");
        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.progressBarCirle = (ProgressBar) findViewById(R.id.simpleprogressbar);
        this.resultText = (TextView) findViewById(R.id.result);
        this.resultText.setVisibility(8);
        this.clickText = (TextView) findViewById(R.id.clicktext);
        this.image = (ImageView) findViewById(R.id.image_1);
        this.image2 = (ImageView) findViewById(R.id.image_2);
        this.image3 = (ImageView) findViewById(R.id.image_3);
        this.image4 = (ImageView) findViewById(R.id.image_4);
        this.image5 = (ImageView) findViewById(R.id.image_5);
        this.cameraKitView = (CameraKitView) findViewById(R.id.camera);
        this.cameraKitView.setFacing(1);
        this.captureCamera = (Button) findViewById(R.id.capture_camera);
        this.startCamera = (Button) findViewById(R.id.camera_start);
        this.reverseCamers = (Button) findViewById(R.id.camera_reverse);
        this.frontCamera = (Button) findViewById(R.id.camera_front);
        this.uploadButton = (Button) findViewById(R.id.btnUploadImage);
        this.nextButton = (Button) findViewById(R.id.nextscreen);
        this.nextButton.setVisibility(8);
        this.nextButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserMultipleImageCaptureActivity.this.startNextScreen();
            }
        });
        this.uploadButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (UserMultipleImageCaptureActivity.this.fileList == null || UserMultipleImageCaptureActivity.this.fileList.size() != 5) {
                    Toast.makeText(UserMultipleImageCaptureActivity.this.getApplicationContext(), "Please capture 5 images", 1).show();
                    return;
                }
                UserMultipleImageCaptureActivity.this.progressBarCirle.setVisibility(0);
                System.out.println("camera upload click");
                UserMultipleImageCaptureActivity.this.clickText.setText("camera upload click");
                UserMultipleImageCaptureActivity.this.uploadImages();
            }
        });
        this.frontCamera.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                System.out.println("camera front click");
                UserMultipleImageCaptureActivity.this.clickText.setText("camera front click");
                UserMultipleImageCaptureActivity.this.cameraKitView.setFacing(1);
            }
        });
        this.reverseCamers.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                System.out.println("camera back click");
                UserMultipleImageCaptureActivity.this.clickText.setText("camera back click");
                UserMultipleImageCaptureActivity.this.cameraKitView.setFacing(0);
            }
        });
        this.startCamera.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserMultipleImageCaptureActivity.this.cameraKitView.onStart();
            }
        });
        this.captureCamera.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                System.out.println("camera capture click");
                UserMultipleImageCaptureActivity.this.clickText.setText("camera capture click");
                UserMultipleImageCaptureActivity.this.cameraKitView.captureImage(new CameraKitView.ImageCallback() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.6.1
                    @Override // com.camerakit.CameraKitView.ImageCallback
                    public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                        try {
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            if (UserMultipleImageCaptureActivity.this.image.getDrawable() == null) {
                                UserMultipleImageCaptureActivity.this.fileList.add(UserMultipleImageCaptureActivity.this.bitmapToFile(bmp, "abc1"));
                                UserMultipleImageCaptureActivity.this.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, UserMultipleImageCaptureActivity.this.image.getWidth(), UserMultipleImageCaptureActivity.this.image.getHeight(), false));
                            } else if (UserMultipleImageCaptureActivity.this.image2.getDrawable() == null) {
                                UserMultipleImageCaptureActivity.this.fileList.add(UserMultipleImageCaptureActivity.this.bitmapToFile(bmp, "abc2"));
                                UserMultipleImageCaptureActivity.this.image2.setImageBitmap(Bitmap.createScaledBitmap(bmp, UserMultipleImageCaptureActivity.this.image2.getWidth(), UserMultipleImageCaptureActivity.this.image2.getHeight(), false));
                            } else if (UserMultipleImageCaptureActivity.this.image3.getDrawable() == null) {
                                UserMultipleImageCaptureActivity.this.fileList.add(UserMultipleImageCaptureActivity.this.bitmapToFile(bmp, "abc3"));
                                UserMultipleImageCaptureActivity.this.image3.setImageBitmap(Bitmap.createScaledBitmap(bmp, UserMultipleImageCaptureActivity.this.image3.getWidth(), UserMultipleImageCaptureActivity.this.image3.getHeight(), false));
                            } else if (UserMultipleImageCaptureActivity.this.image4.getDrawable() == null) {
                                UserMultipleImageCaptureActivity.this.fileList.add(UserMultipleImageCaptureActivity.this.bitmapToFile(bmp, "abc4"));
                                UserMultipleImageCaptureActivity.this.image4.setImageBitmap(Bitmap.createScaledBitmap(bmp, UserMultipleImageCaptureActivity.this.image4.getWidth(), UserMultipleImageCaptureActivity.this.image4.getHeight(), false));
                            } else if (UserMultipleImageCaptureActivity.this.image5.getDrawable() == null) {
                                UserMultipleImageCaptureActivity.this.fileList.add(UserMultipleImageCaptureActivity.this.bitmapToFile(bmp, "abc5"));
                                UserMultipleImageCaptureActivity.this.image5.setImageBitmap(Bitmap.createScaledBitmap(bmp, UserMultipleImageCaptureActivity.this.image5.getWidth(), UserMultipleImageCaptureActivity.this.image5.getHeight(), false));
                            }
                            Log.d("hoja", "i=" + UserMultipleImageCaptureActivity.this.i);
                        } catch (Exception e) {
                            Context applicationContext = UserMultipleImageCaptureActivity.this.getApplicationContext();
                            Toast.makeText(applicationContext, "camerakitexception" + e.toString(), 1).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        translate(lan);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        this.cameraKitView.onStart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.cameraKitView.onResume();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        this.cameraKitView.onPause();
        super.onPause();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        this.cameraKitView.onStop();
        super.onStop();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        this.cameraKitView.onStop();
        super.onBackPressed();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File bitmapToFile(Bitmap bitmap1, String filename) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, filename + ".jpg");
        try {
            OutputStream os = new FileOutputStream(imageFile);
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            return null;
        }
    }

    private MultipartBody.Part[] multipart(List<File> fileList) {
        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            surveyImagesParts[i] = MultipartBody.Part.createFormData("imagelist[]", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/*"), fileList.get(i)));
        }
        return surveyImagesParts;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadImages() {
        System.out.println("uploadimages");
        MultipartBody.Part[] multi = multipart(this.fileList);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("votername", createPartFromString(this.votername));
        map.put("district", createPartFromString(this.district));
        map.put("blockno", createPartFromString(this.blockno));
        map.put("blockid", createPartFromString(this.blockid));
        map.put("voterid", createPartFromString(this.voterid));
        map.put("iddoctype", createPartFromString(this.voteridtype));
        map.put("voteridentificaiton", createPartFromString(this.VoterIdentificationimage));
        System.out.println("uploadimageslast");
        postDataWithImage(map, multi);
    }

    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
    }

    private void postDataWithImage(HashMap<String, RequestBody> map, MultipartBody.Part[] multipart) {
        System.out.println("postdatawithimage1");
        Call<MultipleFaceImageUploadResponse> call = ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVoterIdentificationMultiImages(multipart, map);
        System.out.println("postdatawithimage2");
        call.enqueue(new Callback<MultipleFaceImageUploadResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.7
            @Override // retrofit2.Callback
            public void onResponse(Call<MultipleFaceImageUploadResponse> call2, Response<MultipleFaceImageUploadResponse> response) {
                PrintStream printStream = System.out;
                printStream.println("postdatawithimage_success=" + response.toString());
                UserMultipleImageCaptureActivity userMultipleImageCaptureActivity = UserMultipleImageCaptureActivity.this;
                userMultipleImageCaptureActivity.deleteFaceImages(userMultipleImageCaptureActivity.fileList);
                UserMultipleImageCaptureActivity.this.progressBarCirle.setVisibility(8);
                if (response == null || !response.isSuccessful() || response.body() == null) {
                    TextView textView = UserMultipleImageCaptureActivity.this.resultText;
                    textView.setText("Extra1" + response.toString());
                    UserMultipleImageCaptureActivity.this.updateFaceMatchStatus(-1);
                } else if (response.body().isFound().booleanValue()) {
                    System.out.println("postdatawithimage_success2 found");
                    TextView textView2 = UserMultipleImageCaptureActivity.this.resultText;
                    textView2.setText("Already voted" + response.body().getConf());
                    UserMultipleImageCaptureActivity.this.conf = response.body().getConf();
                    UserMultipleImageCaptureActivity.this.found = true;
                    UserMultipleImageCaptureActivity.this.updateFaceMatchStatus(1);
                } else {
                    UserMultipleImageCaptureActivity.this.found = false;
                    System.out.println("postdatawithimage_success2 not found");
                    TextView textView3 = UserMultipleImageCaptureActivity.this.resultText;
                    textView3.setText("Not Found" + response.body().getConf());
                    UserMultipleImageCaptureActivity.this.conf = response.body().getConf();
                    UserMultipleImageCaptureActivity.this.updateFaceMatchStatus(0);
                    UserMultipleImageCaptureActivity.this.nextButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.7.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            UserMultipleImageCaptureActivity.this.startNextScreen();
                        }
                    });
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<MultipleFaceImageUploadResponse> call2, Throwable t) {
                UserMultipleImageCaptureActivity.this.progressBarCirle.setVisibility(8);
                TextView textView = UserMultipleImageCaptureActivity.this.resultText;
                textView.setText("Extra2" + t.toString());
                UserMultipleImageCaptureActivity.this.updateFaceMatchStatus(-1);
                PrintStream printStream = System.out;
                printStream.println("postdatawithimage_failure" + t.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNextScreen() {
        this.cameraKitView.onPause();
        this.cameraKitView.onStop();
        Intent intent = new Intent(this, FingerprintCaptureActivity.class);
        intent.putExtra("voter_id", this.voterid);
        intent.putExtra("voter_name", this.votername);
        intent.putExtra("district", this.district);
        intent.putExtra("blockno", this.blockno);
        intent.putExtra("blockid", this.blockid);
        intent.putExtra("voteridentificationimage", this.VoterIdentificationimage);
        intent.putExtra("iddoctype", this.voteridtype);
        intent.putExtra("conf", this.conf);
        intent.putExtra("facefound", this.found);
        startActivity(intent);
        finish();
    }

    private void startUserListScreen() {
        this.cameraKitView.onStop();
        Intent intent = new Intent(this, ListUserActivity.class);
        intent.putExtra("boothid", this.blockid);
        startActivity(intent);
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onProgressUpdate(int percentage) {
        this.progressBar.setProgress(percentage);
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onError() {
    }

    @Override // com.example.aadhaarfpoffline.tatvik.ProgressRequestBody.UploadCallbacks
    public void onFinish() {
        this.progressBar.setProgress(100);
    }

    private void updateUserVotingStatus(int votingstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("votingstatus", "0");
        map.put("voterid", this.voterid);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postVotingStatusUpdate(map).enqueue(new Callback<UserVotingStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.8
            @Override // retrofit2.Callback
            public void onResponse(Call<UserVotingStatusUpdatePostResponse> call, Response<UserVotingStatusUpdatePostResponse> response) {
                UserMultipleImageCaptureActivity.this.startNextScreen();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserVotingStatusUpdatePostResponse> call, Throwable t) {
                UserMultipleImageCaptureActivity.this.startNextScreen();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFaceMatchStatus(int facematchstatus) {
        Map<String, String> map = new HashMap<>();
        map.put("facematchstatus", "" + facematchstatus);
        map.put("voterid", this.voterid);
        ((GetDataService) RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class)).postFaceMatchStatusUpdate(map).enqueue(new Callback<UserFaceMatchStatusUpdatePostResponse>() { // from class: com.example.aadhaarfpoffline.tatvik.activity.UserMultipleImageCaptureActivity.9
            @Override // retrofit2.Callback
            public void onResponse(Call<UserFaceMatchStatusUpdatePostResponse> call, Response<UserFaceMatchStatusUpdatePostResponse> response) {
                UserMultipleImageCaptureActivity.this.startNextScreen();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<UserFaceMatchStatusUpdatePostResponse> call, Throwable t) {
                UserMultipleImageCaptureActivity.this.startNextScreen();
            }
        });
    }

    private void translate(String lan) {
        getSupportActionBar().setTitle(this.resources.getString(R.string.voter_authentication_text));
        this.uploadButton.setText(this.resources.getString(R.string.upload_text));
        this.captureCamera.setText(this.resources.getString(R.string.capture_text));
        this.frontCamera.setText(this.resources.getString(R.string.front_camera_text));
        this.reverseCamers.setText(this.resources.getString(R.string.back_camera_text));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteFaceImages(List<File> files) {
        if (!(files == null || files.isEmpty() || files.size() <= 0)) {
            for (int i = 0; i < files.size(); i++) {
                files.get(i).delete();
            }
        }
    }
}
