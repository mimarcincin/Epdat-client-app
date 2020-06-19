package sk.upjs.micma.epdat_client_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.upjs.micma.epdat_client_app.DatabaseApi;
import sk.upjs.micma.epdat_client_app.MainActivity;
import sk.upjs.micma.epdat_client_app.R;
import sk.upjs.micma.epdat_client_app.models.Token;
import sk.upjs.micma.epdat_client_app.models.User;

public class LoginFragment extends Fragment {
    private EditText userEdit;
    private EditText passEdit;
    private Button loginBut;
    private Button cancelBut;
    private DatabaseApi databaseApi = DatabaseApi.API;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userEdit = view.findViewById(R.id.userNameEditText);
        passEdit = view.findViewById(R.id.passwordEditText);
        loginBut = view.findViewById(R.id.loginButton);
        cancelBut = view.findViewById(R.id.loginCancelButton);

        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(userEdit.getText().toString());
                user.setPassword(passEdit.getText().toString());
                //Toast.makeText(getContext(), user.getUsername()+"  "+user.getPassword(), Toast.LENGTH_SHORT).show();
                login(user);

            }
        });
        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void login(User user) {
        Call<Token> call = databaseApi.login(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                    Token token = response.body();
                    ((MainActivity) getActivity()).saveToken(token.getToken());
                    //System.out.println(token.getToken());
                    //Toast.makeText(getContext(), ((MainActivity) getActivity()).getToken(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Successful login", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), "UNSUCC", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
