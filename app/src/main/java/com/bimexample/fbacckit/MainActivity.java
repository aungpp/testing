package com.bimexample.fbacckit;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

public class MainActivity extends AppCompatActivity {
    private Button btnphone,btnemail;
    private final int APP_REQUEST_CODE=999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnphone=findViewById(R.id.btnphone);
        btnemail=findViewById(R.id.btnemail);
        btnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.PHONE,
                                AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
                // ... perform additional configuration ...
                intent.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilder.build());
                startActivityForResult(intent, APP_REQUEST_CODE);
            }
        });

        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.EMAIL,
                                AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
                // ... perform additional configuration ...
                intent.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilder.build());
                startActivityForResult(intent, APP_REQUEST_CODE);
            }
        });

    }

//    private void printKeyHash() {
//        try {
//            PackageInfo packageInfo=getPackageManager().getPackageInfo("com.bimexample.fbacckit", PackageManager.GET_SIGNATURES);
//            for(Signature signature:packageInfo.signatures){
//                MessageDigest messageDigest=MessageDigest.getInstance("SHA");
//                messageDigest.update(signature.toByteArray());
//                //Log.d("KEYHASH", Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));
//                Toast.makeText(getApplicationContext(),Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT)+"--OK--",Toast.LENGTH_LONG).show();
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage()+"Err1",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage()+"Err2",Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...

            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
