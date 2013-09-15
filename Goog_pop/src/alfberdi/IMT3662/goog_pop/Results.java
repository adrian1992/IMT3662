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

	private static final String TABLE_NAME = "popular";
	private static final String ID = "_id";
	public static final String NAME = "name";
	public static final String SURNAME = "surname";
	public static final String NUM_RESULTS = "results";
	public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
			+ " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " STRING," + SURNAME
			+ " STRING," + NUM_RESULTS + " INTEGER" + ");";
	//private static final String INSERT = "INSERT INTO "+TABLE_NAME+" ("+NAME+","+SURNAME+","+NUM_RESULTS+") VALUES (";
	
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
		final SQLiteDatabase db = dbManager.getWritableDatabase();
		dbManager.onCreate(db);
		try {
			int old = retrieveInfo(info, db);
			System.out.print(old);
			System.out.print(info.getInt(NUM_RESULTS));
			if (old == -1) {
				text.setText("First time? your results are: "
						+ info.getInt(NUM_RESULTS));
				saveNew(info, db);
			} else {
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
			}
		} catch (SQLiteException e) {
			text.setText("First time? your results are: "
					+ info.getInt(NUM_RESULTS));
			saveNew(info, db);
		}
		db.close();
		
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

	private int retrieveInfo(Bundle info, SQLiteDatabase db)throws SQLiteException{
		String name = info.getString(NAME);
		String surname = info.getString(SURNAME);
		final Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NAME + "='"
				+ name + "' AND " + SURNAME + "='" + surname + "';", null);
		if(cursor.getPosition() == -1){
			return -1;
		}
		int ret = cursor.getInt(4);
		return ret;
	}
	
	private void saveNew(Bundle info, SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put(NAME, info.getString(NAME));
		values.put(SURNAME, info.getString(SURNAME));
		values.put(NUM_RESULTS, info.getInt(NUM_RESULTS));
		db.insert(TABLE_NAME, null, values);
	}
}
