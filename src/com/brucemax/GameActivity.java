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

	final private String LogMy = "myTags"; // Константа для вывода логов
	private int posInTab = 0;  // Указатель позиции в таблице
	TextView textQuestion;  // Текст вопроса
	Button btnAnswer;	// Кнопка подтверждения ответа
	EditText EditTextAnswer;	// Поле для ввода ответа

	DBHelper dbHelper; // Класс для управления БД  определён ниже

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		Log.d("myLogs", "GameActivity create");
		
		// Находим виджеты на форме
		textQuestion = (TextView) findViewById(R.id.textQuestion);
		btnAnswer = (Button) findViewById(R.id.btnAnswer);
		EditTextAnswer = (EditText) findViewById(R.id.EditTextAnswer);

		btnAnswer.setOnClickListener(this); // Ставим слушатель на кнопку для обработка=и нажатия
		dbHelper = new DBHelper(this); // создаем объект для создания и управления версиями БД

		SQLiteDatabase db = dbHelper.getWritableDatabase(); // Подключаемся к БД

		Log.d(LogMy, "--- Clear mytable: ---");
		// удаляем все записи
		int clearCount = db.delete("mytable", null, null);
		Log.d(LogMy, "deleted rows count = " + clearCount);
		// Пишем данные в таблицу
		ContentValues cv = new ContentValues();
		cv.put("question", "1. Сколько Кире лет?");
		cv.put("answer", "23");
		long rowID = db.insert("mytable", null, cv);
		Log.d(LogMy, "row inserted, ID = " + rowID);

		cv.put("question", "2. Планета Земля от Солнца по счёту?");
		cv.put("answer", "3");
		rowID = db.insert("mytable", null, cv);
		Log.d(LogMy, "row inserted, ID = " + rowID);
		
		cv.put("question", "3. Roy ..?..");
		cv.put("answer", "Jones");
		rowID = db.insert("mytable", null, cv);
		Log.d(LogMy, "row inserted, ID = " + rowID);
		// Создаём курсор, ставим его на первую позицию
		// и пишем первый вопрос
		Cursor c = db.query("mytable", null, null, null, null, null, null);
		if (c.moveToFirst()) {
			textQuestion.setText(c.getString(c.getColumnIndex("question")));

		} else {
			Log.d(LogMy, "0 rows");
			textQuestion.setText("Нет вопросов");
		}
		db.close(); // Отключаемся от БД

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnAnswer:
			// создаем объект для данных

			Log.d(LogMy, "Обработка нажатия Go!");
			String strAnswer = EditTextAnswer.getText().toString(); // Считываем ответ
			// Подключаемся к БД
			SQLiteDatabase db = dbHelper.getWritableDatabase();

			// делаем запрос всех данных из таблицы mytable, получаем Cursor
			Cursor c = db.query("mytable", null, null, null, null, null, null);
			// Ставим курсор на очередную позицию (если она существует)
			if (c.moveToPosition(posInTab)) {
				// Провиряем ответ
				if (strAnswer.equals(c.getString(c.getColumnIndex("answer"))))
					Toast.makeText(this, "Правильно!!!", Toast.LENGTH_LONG) // Выводим всплывающее сообщение
							.show();
				else {
					Toast.makeText(this, "Ступит!", Toast.LENGTH_LONG).show();
				}
				// Переставляем курсор на следующую позицию
				if(c.moveToPosition(++posInTab))
				textQuestion.setText(c.getString(c.getColumnIndex("question")));
				else {
					textQuestion.setText("Нет вопросов");
					EditTextAnswer.setEnabled(false); // Если вопросов нет, то поле для ответов недоступно
				}
			} else
				Log.d(LogMy, "0 rows");

			db.close();
			break;
		}
	}

	class DBHelper extends SQLiteOpenHelper {   // Класс для обслуживания БД

		public DBHelper(Context context) {
			super(context, "myDB", null, 1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("myTags", "--- onCreate database ---");
			// создаем таблицу с полями _id, question и answer			
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
