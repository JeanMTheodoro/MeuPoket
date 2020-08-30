package br.pro.ednilsonrossi.meupocket.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.R;
import br.pro.ednilsonrossi.meupocket.model.Site;

public class SiteDao {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    private static final String SITE_PREFERENCES = "SITE_PREFERENCES";
    private static final String KEY_LIKE_DB = "LIKE_SITE";


    private static List<Site> siteList = null;



    public static List<Site> recuperarSite(Context context) {

        mSharedPreferences = context.getSharedPreferences(SITE_PREFERENCES, context.MODE_PRIVATE);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        ArrayList<Site> siteList = new ArrayList<>();

        try {

            String mSite = mSharedPreferences.getString(KEY_LIKE_DB, "");
            jsonArray = new JSONArray(mSite);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                Site s = new Site(jsonObject.getString("titulo"), jsonObject.getString("endereco"));

                if(jsonObject.getBoolean("favorito")){

                    s.doFavotite();

                }else {

                    s.undoFavorite();
                }


                siteList.add(s);
            }


        } catch (JSONException ex) {

            siteList.clear();
        }

        return siteList;
    }

    public static void atualizaStatus(Context context){

    }

    public static void adionarSite(Context context, Site site) {

        mSharedPreferences = context.getSharedPreferences(SITE_PREFERENCES, context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        if (siteList == null) {
            siteList = new ArrayList<>(1);
        }

        siteList = recuperarSite(context);
        siteList.add(site);

        try {

            JSONArray jsonArray = new JSONArray();


            for (Site s : siteList) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("titulo", s.getTitulo());
                jsonObject.put("endereco", s.getEndereco());
                jsonObject.put("favorito", s.isFavorito());
                jsonArray.put(jsonObject);

            }

            String si = jsonArray.toString();
            mEditor.putString(KEY_LIKE_DB, si);
            mEditor.commit();

        } catch (JSONException e) {

            Log.e("Erro ao popular a lista", "erro");
        }
    }


}