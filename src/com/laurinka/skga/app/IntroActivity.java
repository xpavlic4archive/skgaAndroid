package com.laurinka.skga.app;


<<<<<<< HEAD
=======
import android.annotation.TargetApi;
import android.app.ActionBar;
>>>>>>> remotes/origin/david_graphics01
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/**
 * Back intro screen.
 * @author radimpavlicek
 *
 */
<<<<<<< HEAD
=======
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
>>>>>>> remotes/origin/david_graphics01
public class IntroActivity extends Activity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Setting a custom layout for the list activity */
		setContentView(R.layout.intro);
		
		/** Added typeFace for Intro buttons */
		Button myButtonA=(Button)findViewById(R.id.records_button);
		Button myButtonB=(Button)findViewById(R.id.about_button);
		Button myButtonC=(Button)findViewById(R.id.exit_button);
		
		Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/helvneue63medextobl.ttf");
		myButtonA.setTypeface(typeFace);
		myButtonB.setTypeface(typeFace);
		myButtonC.setTypeface(typeFace);
		
		/** Hide ActionBar in IntroActivity screen*/
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();
	}
	
    public void about(View view) {
        startActivity(new Intent(this, AboutActivity.class));
    }
    
    public void view(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    
    public void close(View view) {
        finish();
    }
}
