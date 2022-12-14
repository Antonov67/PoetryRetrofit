package ru.myitschool.mte;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myitschool.mte.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    TextView tvResult;
    EditText etSearch;
    Button btSearch;
    Retrofit retrofit;

    /**
     * Uses API: PoetryDB
     * https://github.com/thundercomb/poetrydb#readme
     * https://poetrydb.org/author,poemcount/Shakespeare;2/lines.json
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvResult = binding.content.tvResult;
        etSearch = binding.content.etSearch;
        btSearch = binding.content.btSearch;
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PoetryDBService service = retrofit.create(PoetryDBService.class);
        btSearch.setOnClickListener(view -> {
            Call<List<Text>> call = service.getTexts(etSearch.getText().toString(), 1);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<List<Text>> call, @NonNull Response<List<Text>> response) {
                    List<Text> books = response.body();
                    StringBuilder text = new StringBuilder();
                    if (books != null) {
                        for (Text b : books) {
                            for (String s : b.lines) {
                                text.append(s).append("\n");
                            }
                        }
                        tvResult.setText(text.toString());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<List<Text>> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "Network operation error!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}



