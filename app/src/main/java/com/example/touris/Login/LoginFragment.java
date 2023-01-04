package com.example.touris.Login;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.touris.MainActivity;
import com.example.touris.R;
import com.example.touris.Sever.APIRetrofitClient;
import com.example.touris.Sever.APIService;
import com.example.touris.Sever.Dataservice;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;

import java.util.List;

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

        Login();

        return  viewGroup;
    }
    private  void Login(){
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = UserName.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(),"Vui lòng nhập email hoặc password",Toast.LENGTH_SHORT).show();
                }else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(email);
                    loginRequest.setPassword(password);
                    getUsernName(loginRequest);
                    CheckBox(checkbox,loginRequest);
                }
            }
        });
    }


    private void getUsernName(LoginRequest loginRequest){

        Call<User> call = APIService.getService().GetUsername(loginRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = (User) response.body();
                    startActivity(new Intent(getActivity(),MainActivity.class).putExtra("data",user));
                }else {
                    String message = "Error occurred please try again later";
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void CheckBox(CheckBox checkbox,LoginRequest loginRequest) {

        if (checkbox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("taikhoan", loginRequest.getUsername());
            editor.putString("matkhau", loginRequest.getPassword());
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