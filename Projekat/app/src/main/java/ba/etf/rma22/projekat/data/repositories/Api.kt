package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("anketa")
    suspend fun dajSveAnkete(@Query("offset") id : Int): List<Anketa>

    @GET("anketa/{id}")
    suspend fun dajeAnketu(@Path("id") id: Int): Response<Anketa>

    @GET("student/{id}/anketataken")
    suspend fun getPoceteAnkete(@Path("id") id: String): List<AnketaTaken>

    @POST("student/{id}/anketa/{kid}")
    suspend fun zapocniAnketu(@Path("kid") idAnketa: Int, @Path("id") id: String): AnketaTaken

    @GET("grupa/{id}/ankete")
    suspend fun dajUpisaneAnkete(@Path("id") id : Int): List<Anketa>

    @GET("student/{id}/grupa")
    suspend fun dajStudentoveGrupe(@Path("id") id : String) : List<Grupa>

    @GET("student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getOdgovoriAnketa(@Path("ktid") idAnkete:Int, @Path("id") id: String): List<Odgovor>

    @GET("istrazivanje")
    suspend fun dajIstrazivanja (@Query("offset") id : Int) : Response<List<Istrazivanje>>

    @GET("istrazivanje")
    suspend fun dajIstrazivanja2 (@Query("offset") id : Int) : List<Istrazivanje>

    @GET("grupa")
    suspend fun dajGrupe () : Response<List<Grupa>>

    @GET("grupa")
    suspend fun dajGrupe2 () : List<Grupa>

    @POST("grupa/{gid}/student/{id}")
    suspend fun upisiStudentaUGrupu(@Path("gid") gid : Int, @Path("id") id : String) :  Mess

    @GET("istrazivanje/{id}")
    suspend fun dajIstrazivanjeId(@Path("id") istrazivanjeId: Int): Istrazivanje

    @GET("anketa/{id}/grupa")
    suspend fun dajGrupeZaAnkete(@Path("id") anketaId: Int): List<IstrazivanjeIGrupa>

    @GET("anketa/{id}/pitanja")
    suspend fun getPitanja(@Path("id") idAnkete: Int): Response<List<Pitanje>>

    @POST("student/{id}/anketataken/{ktid}/odgovor")
    suspend fun postaviOdgovorAnketi(@Path("id") idAnkete: String, @Path("ktid") idAnketaTaken: Int, @Body odgovor: OdgovorSlanje) : PovratniOdgovor

}