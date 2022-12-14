package ru.myitschool.mte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PoetryDBService {

    @GET("author,poemcount/{author};{poemcount}/lines.json")
    Call<List<Text>> getTexts(@Path("author") String toString,@Path("poemcount") int i);
}
