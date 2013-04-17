package com.example.agenda;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et1, et2, et3, et4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et1 = (EditText) findViewById(R.id.agenda_id_txt);
        et2 = (EditText) findViewById(R.id.agenda_nombre_txt);
        et3 = (EditText) findViewById(R.id.agenda_direccion_txt);
        et4 = (EditText) findViewById(R.id.agenda_telefono_txt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void alta(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();
        
        String id = et1.getText().toString();
        String nombre = et2.getText().toString();
        String direccion = et3.getText().toString();
        String telefono = et4.getText().toString();
        
        ContentValues registro = new ContentValues();
       
        registro.put("id", id);
        registro.put("nombre", nombre);
        registro.put("direccion", direccion);
        registro.put("telefono", telefono);
        
        bd.insert("agenda", null, registro);
        bd.close();
        
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        
        Toast.makeText(this, "el registro ha sido insertado en la agenda", Toast.LENGTH_SHORT).show();
    }
	
	public void baja(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();
        
        String id = et1.getText().toString();
        
        int cant = bd.delete("agenda", "id=" + id, null);
        bd.close();
        
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        
        if (cant == 1) {
            Toast.makeText(this, "El registro ha sido eliminado de la agenda", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "El existe el registro en la agenda", Toast.LENGTH_SHORT).show();
        }
    }
	
	public void modificacion(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();
        
        String id = et1.getText().toString();
        String nombre = et2.getText().toString();
        String direccion = et3.getText().toString();
        String telefono = et4.getText().toString();
        
        ContentValues registro = new ContentValues();
        
        registro.put("nombre", nombre);
        registro.put("direccion", direccion);
        registro.put("telefono", telefono);
        
        int cant = bd.update("agenda", registro, "id=" + id, null);
        bd.close();
        
        if (cant == 1) {
        	Toast.makeText(this, "Se han modificado los datos del registro", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el registro en la agenda", Toast.LENGTH_SHORT).show();
        }
    }
	
	public void consulta(View view) {
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        
        SQLiteDatabase bd=admin.getWritableDatabase();
        
        String id=et1.getText().toString();
        
        Cursor registro=bd.rawQuery("select nombre,direccion,telefono from agenda where id="+id+"", null);
        
        if (registro.moveToFirst()) {
            et2.setText(registro.getString(0));
            et3.setText(registro.getString(1));
            et4.setText(registro.getString(2));            
        } else {
        	Toast.makeText(this, "No existe el registro con ese id", Toast.LENGTH_SHORT).show();
        }
        
        bd.close();            
    }
	
	public void salir(View view) {
		finish();
	}

}
