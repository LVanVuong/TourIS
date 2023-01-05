package com.example.touris.SignUp;

import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.touris.MainActivity;
import com.example.touris.R;
import com.example.touris.Sever.APIService;
import com.example.touris.Sever.Dataservice;

import java.util.HashMap;
import java.util.Map;

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
            StringRequest request = new StringRequest(Request.Method.POST, dataservice.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("true")) {
                        startActivity(new Intent(getActivity(), MainActivity.class));

                    } else if (response.equals("false")) {
                        Toast.makeText(getActivity(), "Invalid Login/Password", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("user_name", email);
                    data.put("password", pass);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);
        } else {
            Toast.makeText(getActivity(), " Don't Empty Field ", Toast.LENGTH_SHORT).show();
        }
    }
}
