package wcm.jcrogar.fca.control;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {


    private EditText UserEdit;
    private EditText PassEdit;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserEdit = (EditText) findViewById(R.id.LoginUser);
        PassEdit    = (EditText) findViewById(R.id.LoginPass);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(login.this, "El usuario inicio sesion", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login.this, "El usuario salio de la sesion", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
       firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null)
            firebaseAuth.removeAuthStateListener(authStateListener);


    }

    public void loginOn(View view) {
    String username = UserEdit.getText().toString();
    String password = PassEdit.getText().toString();
    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          /*  if(UserEdit.equals("") && PassEdit.equals("")){
                Toast.makeText(login.this, "Ha dejado campos vacios", Toast.LENGTH_SHORT).show();
            }else*/
                if (!task.isSuccessful()) {
                    Toast.makeText(login.this, "Verifique su Usuario y Password", Toast.LENGTH_LONG).show();
                } else {
                    Intent intentF = new Intent(login.this, access.class);
                    startActivity(intentF);
                }

        }
    });

    }
}
