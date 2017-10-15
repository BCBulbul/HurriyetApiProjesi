package burakcanbulbul.com.hurriyetapiprojesi.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import burakcanbulbul.com.hurriyetapiprojesi.R;

public class ShowIntro extends AppIntro
{
    private void printKeyHash()
    {
        // Add code to print out the key hash
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "burakcanbulbul.com.hurriyetapiprojesi",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        printKeyHash();
        addSlide(AppIntroFragment.newInstance("İsterseniz Günlük Haberleri Okuyun","Twitter veya Facebook hesabınızla giriş yaparak haberleri istediğiniz zaman istediğiniz anda mobil telefonunuzdan okuyun",
                R.drawable.ic_border_color_black_24dpr, getResources().getColor(R.color.firstFragmentColor)));


        addSlide(AppIntroFragment.newInstance("İsterseniz istediğiniz kritere göre haber okuyun ister konuşarak söyleyin, ister yazın","Görmek istediğiniz haberlerin konusunu aratın gelsin, ister gündem,ister spor, isterseniz ekonomi ve dahası",
                R.drawable.ic_search_black_24dp, getResources().getColor(R.color.secondFragmentColor)));
        addSlide(AppIntroFragment.newInstance("Facebook ya da Twitter ile bağlan","Butona bas ve okumaya başla :)",
                R.drawable.ic_mood_black_24dp, getResources().getColor(R.color.thirdFragmentColor)));

        setFadeAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        super.onSkipPressed(currentFragment);

        Toast.makeText(this,"Giriş Sayfasına yönleniyorsunuz",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ShowIntro.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment)
    {
        super.onSlideChanged(oldFragment, newFragment);

    }

    @Override
    public void onDonePressed(Fragment currentFragment)
    {
        super.onDonePressed(currentFragment);
        Toast.makeText(this,"Giriş Sayfasına yönleniyorsunuz",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ShowIntro.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
