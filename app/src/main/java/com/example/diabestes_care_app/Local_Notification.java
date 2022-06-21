package com.example.diabestes_care_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.errorprone.annotations.Var;

import androidx.appcompat.app.AppCompatActivity;

public class Local_Notification extends AppCompatActivity {
private Button blbl , w , n ,t,bl , btn_save;
public static final String SHAERD_PERF ="";
public static final String W ="w"  ,BLBL ="blbl" ,N ="n",T ="t",BL ="bl"  ;

private String ww  , nn , blblbl , bll , tt ;
TextView tvs ;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_notification);

            tvs = findViewById(R.id.tvs);
            btn_save = findViewById(R.id.btn_save);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            saveData();

            }
        });
        loadData();
        updateChat();




    }
public void saveData()
{
    SharedPreferences sharedPreferences = getSharedPreferences(SHAERD_PERF , MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(T , Integer.parseInt(t.getText().toString()));
    editor.putInt(W , Integer.parseInt(w.getText().toString()));
    editor.putInt(N , Integer.parseInt(n.getText().toString()));
    editor.putInt(BL , Integer.parseInt(bl.getText().toString()));
    editor.putInt(BLBL, Integer.parseInt(blbl.getText().toString()));
    editor.apply();
    Toast.makeText(this, "Save And Move Data In Text View", Toast.LENGTH_SHORT).show();
} // end method save Data

    public void loadData (){
    SharedPreferences sharedPreferences = getSharedPreferences(SHAERD_PERF , MODE_PRIVATE) ;
    tt = sharedPreferences.getString(T ,"");
    nn = sharedPreferences.getString(N ,"");
    ww = sharedPreferences.getString(W ,"");
    bll = sharedPreferences.getString(BL ,"");
    blblbl = sharedPreferences.getString(BLBL ,"");

}
public  void updateChat(){
    tvs.setText(tt);
    tvs.setText(nn);
    tvs.setText(ww);
    tvs.setText(bll);
    tvs.setText(blblbl);
}



} // end page