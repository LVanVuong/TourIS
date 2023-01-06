package com.example.touris.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.touris.MainActivity;
import com.example.touris.R;
import com.example.touris.Sever.APIService;
import com.example.touris.Sever.Dataservice;
import com.example.touris.Sever.responsemodel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    EditText mEmail, mPass, mCfPass, mPhone;
    Button btnSignUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.sign_up,container,false);
        btnSignUp = viewGroup.findViewById(R.id.btnSignUp);
        mEmail = viewGroup.findViewById(R.id.email_name);
        mPass = viewGroup.findViewById(R.id.pass_sign_up);
        mCfPass = viewGroup.findViewById(R.id.pass_sign_up_1);
        mPhone = viewGroup.findViewById(R.id.phone);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });

        return viewGroup;

    }
    private void SignUp() {
        String email = mEmail.getText().toString().trim();
        String phone = mPhone.getText().toString().trim();
        String pass = mPass.getText().toString().trim();
        String cfpass = mCfPass.getText().toString().trim();
        Dataservice dataservice = APIService.getService();
        if (!pass.equals(cfpass)) {
            Toast.makeText(getActivity(), "Password mismatch", Toast.LENGTH_SHORT).show();
        } else if (!email.equals("") && !phone.equals("") && !pass.equals("") && pass.equals(cfpass)) {
            Call<responsemodel> call = dataservice.PostUser(phone,pass,email);
            call.enqueue(new Callback<responsemodel>() {
                @Override
                public void onResponse(Call<responsemodel> call, Response<responsemodel> response) {
                   responsemodel ojb = response.body();
                    String output = ojb.getMessage();
                    if(output.equals("exist")){
                        Toast.makeText(getActivity(),"Email Exist",Toast.LENGTH_SHORT).show();
                    }
                    if(output.equals("true")){
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                    else{
                        Toast.makeText(getActivity(),"Invalid email or password",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<responsemodel> call, Throwable t) {
                    String message = "Error occurred please try again later";
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
