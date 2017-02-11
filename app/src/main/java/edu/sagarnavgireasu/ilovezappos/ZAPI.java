package edu.sagarnavgireasu.ilovezappos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sagarnavgire on 2/7/17.
 */

public interface ZAPI {

    @GET("/Search?")
    Call<Product> getResponse(@Query("term") final String term, @Query("key") final String key);
}
