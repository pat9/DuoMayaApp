package AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by danie on 02/04/2018.
 */

public class ConsultarDatosAsyncTask extends AsyncTask<URL, Void, String> {
    public ConsultarDatosAsyncTask.interfacedelhilo delegado;
    public interface interfacedelhilo
    {
        void Respuesta(String Datos);
    }

    @Override
    protected String doInBackground(URL... urls) {
        String Datos = "";
        try {
            Datos = descargardatos(urls[0]);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return Datos;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        Log.d("Verifica", s);
        delegado.Respuesta(s);
    }

    public String descargardatos(URL liga) throws IOException
    {
        String RespuestaJson="";
        HttpURLConnection conexionUrl = null;
        InputStream inputStream = null;
        try {
            conexionUrl = (HttpURLConnection) liga.openConnection();
            conexionUrl.setRequestMethod("GET");
            conexionUrl.setReadTimeout(10000);
            conexionUrl.setConnectTimeout(15000);
            conexionUrl.connect();
            inputStream = conexionUrl.getInputStream();
            RespuestaJson = leerdesdeStream(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if(conexionUrl != null)
            {
                conexionUrl.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }


        }
        return RespuestaJson;
    }

    private String leerdesdeStream(InputStream inputStream) throws IOException
    {
        StringBuilder salida = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputlector = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader lector = new BufferedReader(inputlector);
            String Linea = lector.readLine();
            while(Linea != null)
            {
                salida.append(Linea);
                Linea = lector.readLine();
            }
        }
        return salida.toString();
    }
}
