package com.example.touris.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.touris.MainActivity;
import com.example.touris.R;
import com.example.touris.Sever.APIService;
import com.example.touris.Sever.Dataservice;
import com.example.touris.Sever.responsemodel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    SharedPreferences sharedPreferences;
    EditText UserName, Password;
    TextView Forget_Password;
    ImageView google;
    Button Login;
    CheckBox checkbox;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);

        UserName = viewGroup.findViewById(R.id.email);
        Password = viewGroup.findViewById(R.id.pass);
        Forget_Password = viewGroup.findViewById(R.id.forget);
        Login = viewGroup.findViewById(R.id.button);
        checkbox = viewGroup.findViewById(R.id.checkbox);
        google = viewGroup.findViewById(R.id.google);
        sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        UserName.setText(sharedPreferences.getString("taikhoan", ""));
        Password.setText(sharedPreferences.getString("matkhau", ""));
        checkbox.setChecked(sharedPreferences.getBoolean("checked", false));

        bntLogin();

        return  viewGroup;
    }
    private void bntLogin(){
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processlogin();
            }
        });
    }

    private void processlogin() {
        String email = UserName.getText().toString().trim();
        String password = Password.getText().toString().trim();
        Dataservice dataservice = APIService.getService();
        Call<responsemodel> call = dataservice.verifyUser(email,password);
        call.enqueue(new Callback<responsemodel>() {
            @Override
            public void onResponse(Call<responsemodel> call, Response<responsemodel> response) {
                responsemodel ojb = response.body();
                String output = ojb.getMessage();
                Log.e("A:",output);
                if(output.equals("failed")){
                    Toast.makeText(getActivity(),"Invalid email or password",Toast.LENGTH_SHORT).show();
                }
                if(output.equals("exist")){
                    Log.e("A:",output.toString());
                    CheckBox(checkbox,email,password);
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<responsemodel> call, Throwable t) {
                String message = "Error occurred please try again later";
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void CheckBox(@NonNull CheckBox checkbox, String email, String password) {

        if (checkbox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("taikhoan", email);
            editor.putString("matkhau", password);
            editor.putBoolean("checked", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("taikhoan");
            editor.remove("matkhau");
            editor.remove("checked");
            editor.commit();
        }

    }
}