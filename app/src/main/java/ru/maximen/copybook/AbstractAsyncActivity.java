package ru.maximen.copybook;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public abstract class AbstractAsyncActivity extends AppCompatActivity {

    protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    private boolean destroyed = false;

    // ***************************************
    // Activity methods
    // ***************************************
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    // ***************************************
    // Public methods
    // ***************************************
    public void showLoadingProgressDialog() {
        this.showProgressDialog("Загрузка. Пожалуйста подождите...");
    }

    public void showProgressDialog(CharSequence message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
        }

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
        }
    }

}
