package com.brucemax;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements OnClickListener {

	final private String LogMy = "myTags"; // ��������� ��� ������ �����
	private int posInTab = 0;  // ��������� ������� � �������
	TextView textQuestion;  // ����� �������
	Button btnAnswer;	// ������ ������������� ������
	EditText EditTextAnswer;	// ���� ��� ����� ������

	DBHelper dbHelper; // ����� ��� ���������� ��  �������� ����

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		Log.d("myLogs", "GameActivity create");
		
		// ������� ������� �� �����
		textQuestion = (TextView) findViewById(R.id.textQuestion);
		btnAnswer = (Button) findViewById(R.id.btnAnswer);
		EditTextAnswer = (EditText) findViewById(R.id.EditTextAnswer);

		btnAnswer.setOnClickListener(this); // ������ ��������� �� ������ ��� ���������=� �������
		dbHelper = new DBHelper(this); // ������� ������ ��� �������� � ���������� �������� ��

		SQLiteDatabase db = dbHelper.getWritableDatabase(); // ������������ � ��

		Log.d(LogMy, "--- Clear mytable: ---");
		// ������� ��� ������
		int clearCount = db.delete("mytable", null, null);
		Log.d(LogMy, "deleted rows count = " + clearCount);
		// ����� ������ � �������
		ContentValues cv = new ContentValues();
		cv.put("question", "1. ������� ���� ���?");
		cv.put("answer", "23");
		long rowID = db.insert("mytable", null, cv);
		Log.d(LogMy, "row inserted, ID = " + rowID);

		cv.put("question", "2. ������� ����� �� ������ �� �����?");
		cv.put("answer", "3");
		rowID = db.insert("mytable", null, cv);
		Log.d(LogMy, "row inserted, ID = " + rowID);
		
		cv.put("question", "3. Roy ..?..");
		cv.put("answer", "Jones");
		rowID = db.insert("mytable", null, cv);
		Log.d(LogMy, "row inserted, ID = " + rowID);
		// ������ ������, ������ ��� �� ������ �������
		// � ����� ������ ������
		Cursor c = db.query("mytable", null, null, null, null, null, null);
		if (c.moveToFirst()) {
			textQuestion.setText(c.getString(c.getColumnIndex("question")));

		} else {
			Log.d(LogMy, "0 rows");
			textQuestion.setText("��� ��������");
		}
		db.close(); // ����������� �� ��

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnAnswer:
			// ������� ������ ��� ������

			Log.d(LogMy, "��������� ������� Go!");
			String strAnswer = EditTextAnswer.getText().toString(); // ��������� �����
			// ������������ � ��
			SQLiteDatabase db = dbHelper.getWritableDatabase();

			// ������ ������ ���� ������ �� ������� mytable, �������� Cursor
			Cursor c = db.query("mytable", null, null, null, null, null, null);
			// ������ ������ �� ��������� ������� (���� ��� ����������)
			if (c.moveToPosition(posInTab)) {
				// ��������� �����
				if (strAnswer.equals(c.getString(c.getColumnIndex("answer"))))
					Toast.makeText(this, "���������!!!", Toast.LENGTH_LONG) // ������� ����������� ���������
							.show();
				else {
					Toast.makeText(this, "������!", Toast.LENGTH_LONG).show();
				}
				// ������������ ������ �� ��������� �������
				if(c.moveToPosition(++posInTab))
				textQuestion.setText(c.getString(c.getColumnIndex("question")));
				else {
					textQuestion.setText("��� ��������");
					EditTextAnswer.setEnabled(false); // ���� �������� ���, �� ���� ��� ������� ����������
				}
			} else
				Log.d(LogMy, "0 rows");

			db.close();
			break;
		}
	}

	class DBHelper extends SQLiteOpenHelper {   // ����� ��� ������������ ��

		public DBHelper(Context context) {
			super(context, "myDB", null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("myTags", "--- onCreate database ---");
			// ������� ������� � ������ _id, question � answer			
			db.execSQL("create table mytable ("
					+ "_id integer primary key autoincrement,"
					+ "question text," + "answer text" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	}
}
