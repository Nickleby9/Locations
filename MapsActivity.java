package hilay.edu.locationaware;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MapsActivity extends AppCompatActivity{

    private static final String TAG = "Ness";
    private static final int RC_LOCATION = 10;
    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //in the dynamic approach -> instantiate the fragment
        MyMapFragment mapFragment = new MyMapFragment();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.frame1, mapFragment, "1").
                replace(R.id.frame2, new LocationFragment(), "2").
                commit();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    initWithUser();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:No User");
                    Intent intent = new Intent(MapsActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void initWithUser() {

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //1) declare a listener: AuthStateListener
    //2) register the listener in onResume
    //3) remove the listener in onPause


}
