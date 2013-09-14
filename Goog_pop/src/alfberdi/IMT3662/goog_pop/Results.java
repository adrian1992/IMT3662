package alfberdi.IMT3662.goog_pop;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Results extends Activity {

	private static final String TABLE_NAME = "Google_popular";
	private static final String ID = "Id";
	private static final String NAME = "Name";
	private static final String SURNAME = "Surname";
	private static final String NUM_RESULTS = "Results";
	public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + SURNAME
			+ " TEXT" + NUM_RESULTS + " INTEGER" + ");";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		TextView text = (TextView)findViewById(R.id.final_info);
		Button restart = (Button)findViewById(R.id.restart);
		restart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		Bundle info = this.getIntent().getExtras();
		DatabaseManager dbManager = new DatabaseManager(this);
		try {
			int old = retrieveInfo(info, dbManager);

			if (info.getInt(NUM_RESULTS) == old) {
				text.setText("You haven\'t changed the nuber of results in a Google search. It still being: "
						+ String.valueOf(old));
			} else {
				if (info.getInt(NUM_RESULTS) > old) {
					text.setText("Congratulations you are now more popular than last time! You use to have: "
							+ String.valueOf(old)
							+ " results and now you have: "
							+ String.valueOf(info.getInt(NUM_RESULTS)));
					update(info, dbManager);
				} else {
					text.setText("It looks like you are not that pouplar any more! You use to have: "
							+ String.valueOf(old)
							+ " results and now you have: "
							+ String.valueOf(info.getInt(NUM_RESULTS)));
					update(info, dbManager);
				}
			}
		} catch (SQLiteException e) {
			text.setText("First time? your results are: "
					+ info.getInt(NUM_RESULTS));
			saveNew(info, dbManager);
		}
		
	}

	private void update(Bundle info, DatabaseManager dbManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
		return true;
	}

	private int retrieveInfo(Bundle info, DatabaseManager dbManager)throws SQLiteException{
		String name = info.getString(NAME);
		String surname = info.getString(SURNAME);
		final SQLiteDatabase read_db = dbManager.getReadableDatabase();
		final Cursor cursor = read_db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "="
				+ name + " AND " + SURNAME + "=" + surname + ";", null);
		if(cursor == null){
			read_db.close();
			return -1;
		}
		int ret = cursor.getInt(4);
		read_db.close();
		return ret;
	}
	
	private void saveNew(Bundle info, DatabaseManager dbManager){
		final SQLiteDatabase write_db = dbManager.getWritableDatabase();
		ContentValues cnt = new ContentValues();
		cnt.put(NAME, info.getString(NAME));
		cnt.put(SURNAME, info.getString(SURNAME));
		cnt.put(NUM_RESULTS, info.getInt(NUM_RESULTS));
		write_db.insert(TABLE_NAME, null, cnt);
		write_db.close();
	}
}
