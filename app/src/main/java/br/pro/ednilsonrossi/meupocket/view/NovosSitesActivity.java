package br.pro.ednilsonrossi.meupocket.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.dao.SiteDao;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class NovosSitesActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mSiteName;
    private EditText mEndSite;
    private Button mButton;
    private ImageView mImageFavorito;
    private boolean favorito = false;
    private Site msite;
    private List<Site> siteList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novos_sites);

        initializeComponeste();

        mButton.setOnClickListener(this);
        mImageFavorito.setOnClickListener(this);
    }

    private void initializeComponeste() {

        mSiteName = findViewById(R.id.edittext_name_site);
        mEndSite = findViewById(R.id.editText_end_site);
        mButton = findViewById(R.id.bnt_cadastrar);
        mImageFavorito = findViewById(R.id.image_favorito);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if (view == mButton) {

            String nameSite = mSiteName.getText().toString();
            String endSite = mEndSite.getText().toString();

            if (!nameSite.isEmpty() && !endSite.isEmpty()) {

                Site s  = new Site(nameSite, endSite);

                if (favorito) {

                    s.doFavotite();

                }

                SiteDao.adionarSite(getApplicationContext(),s);
                finish();

            }
        } if (view == mImageFavorito) {

            mImageFavorito.setImageResource(R.drawable.ic_favorito);
            favorito = true;

        }
    }
}