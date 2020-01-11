package com.example.ujian4_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ujian4_quiz.model.QuizModel;
import com.example.ujian4_quiz.service.APIClient;
import com.example.ujian4_quiz.service.APIInterfacesRest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class question_activity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_Question,scoreView;
    Button btn_A,btn_B,btn_C,btn_D;
    ImageView gambar1;

    int size;
    int y = 0;

    ArrayList<String> total = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_activity);

        //TextView
        txt_Question = findViewById(R.id.txt_Question);
        scoreView = findViewById(R.id.scoreView);

        //Button
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);

        //imageview
        gambar1 = findViewById(R.id.gambar1);


        btn_A.setOnClickListener(this);
        btn_B.setOnClickListener(this);
        btn_C.setOnClickListener(this);
        btn_D.setOnClickListener(this);

        btn_A.setId(Integer.parseInt("1"));
        btn_B.setId(Integer.parseInt("2"));
        btn_C.setId(Integer.parseInt("3"));
        btn_D.setId(Integer.parseInt("4"));
        callquiz();

    }




    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callquiz(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(question_activity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<QuizModel> call3 = apiInterface.getModel();
        call3.enqueue(new Callback<QuizModel>() {
            @Override
            public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                progressDialog.dismiss();
                QuizModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();


                if (data !=null) {
                    size = data.getData().getSoalQuizAndroid().size();
                 if (y < size) {
                     if (data.getData().getSoalQuizAndroid().get(y).getJenisPertanyaan().equalsIgnoreCase("gambar")) {

                         txt_Question.setText(data.getData().getSoalQuizAndroid().get(y).getPertanyaan().toString());
                     btn_A.setText(data.getData().getSoalQuizAndroid().get(y).getA().toString());
                     btn_B.setText(data.getData().getSoalQuizAndroid().get(y).getB().toString());
                     btn_C.setText(data.getData().getSoalQuizAndroid().get(y).getC().toString());
                     btn_D.setText(data.getData().getSoalQuizAndroid().get(y).getD().toString());


                     String image = data.getData().getSoalQuizAndroid().get(y).getGambar();
                     Picasso.get().load(image).into(gambar1);

                 }else {

                         btn_A.setText(data.getData().getSoalQuizAndroid().get(y).getA().toString());
                         btn_B.setText(data.getData().getSoalQuizAndroid().get(y).getB().toString());
                         btn_C.setText(data.getData().getSoalQuizAndroid().get(y).getC().toString());
                         btn_D.setText(data.getData().getSoalQuizAndroid().get(y).getD().toString());

                     }
                 y++;



                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(question_activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(question_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }};
            }

            @Override
            public void onFailure(Call<QuizModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }

    @Override
    public void onClick(View v) {
        total.add(String.valueOf(v.getId()));
        Toast.makeText(question_activity.this, String.valueOf(total), Toast.LENGTH_LONG).show();
        callquiz();
        if (y == 3) {


            Intent i = new Intent(question_activity.this, ResultNilai.class);
            i.putStringArrayListExtra("total", total);
            startActivity(i);
            finish();
        }
    }
}
