package alfberdi.IMT3662.goog_pop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

class Network extends AsyncTask<String, Boolean, Integer>{

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		String searchQuery = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCkbeVAdKmAOcus7ke_jumhgBp09W6YwMw&cx=013036536707430787589:_pqjad5hr1a&q=".concat(params[0].concat("&alt=json&fields=queries(request(totalResults))"));
		URL url;
		int i=0;
		try {
			url = new URL(searchQuery);
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null && i!=4)
					i++;
				in.close();
				return Integer.valueOf(inputLine.substring(21, inputLine.length()-1));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	
}
