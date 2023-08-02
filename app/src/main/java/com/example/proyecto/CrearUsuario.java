package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class CrearUsuario extends AppCompatActivity {

    private Spinner spinnerRoles;
    private Button btnregistrar;
    private String selectedRole;
    private TextView Nombreingre, contraingre, edId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        // Obtener referencias de las vistas
        btnregistrar = findViewById(R.id.btnRegistrar01);
        Nombreingre = findViewById(R.id.edusernamer);
        contraingre = findViewById(R.id.edcontralr);
        edId = findViewById(R.id.id);

        spinnerRoles = findViewById(R.id.spinnerempleado);
        String[] opciones = {"Administrador", "Cocinero", "Mesero", "Seleccione un rol"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoles.setAdapter(adapter);

        spinnerRoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Cuando se seleccione un elemento del Spinner, lo almacenamos en la variable selectedRole
                selectedRole = opciones[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No es necesario implementar esta parte para nuestro caso
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar();
            }
        });
    }

    private void Registrar() {
        // Verificamos que el rol haya sido seleccionado
        if (selectedRole.equals("Seleccione un rol")) {
            Toast.makeText(this, "Seleccione un rol válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://proyectocafeteria54.000webhostapp.com/insertar.php"; // <-- Asegúrate de proporcionar el URL correcto
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Aquí puedes manejar la respuesta del servidor si lo deseas
                // Por ejemplo, mostrar un mensaje de éxito o realizar alguna acción
                Toast.makeText(CrearUsuario.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CrearUsuario.this, "Error en el registro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", edId.getText().toString());
                parametros.put("nombre", Nombreingre.getText().toString());
                parametros.put("rol", selectedRole); // Agregamos el rol seleccionado

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
