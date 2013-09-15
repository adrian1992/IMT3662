package alfberdi.IMT3662.goog_pop;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserData extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_data);
		final EditText name = (EditText)findViewById(R.id.name);
		final EditText surname = (EditText)findViewById(R.id.surname);
		final Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int num_results = 0;
				// TODO Auto-generated method stub
				Network compute = (Network) new Network().execute(name.getText().toString().concat("+".concat(surname.getText().toString())));
				try {
					num_results = compute.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent change = new Intent(UserData.this, Results.class);
				Bundle data = new Bundle();			
				data.putInt(Results.NUM_RESULTS, num_results);
				data.putString(Results.NAME, name.getText().toString());
				data.putString(Results.SURNAME, surname.getText().toString());
				change.putExtras(data);				
				startActivity(change);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_data, menu);
		return true;
	}

}
