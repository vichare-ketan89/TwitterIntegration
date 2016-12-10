package com.keto.twitterintegration.util;

import com.twitter.sdk.android.core.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;

/**
 * Created by ketanvichare on 12/10/16.
 */

public class TwitterIntegrationUtil {

    public static String convertResponseToString(Result<ResponseBody> result) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                BufferedReader bufferedReader;
                InputStreamReader inputStreamReader = new InputStreamReader(result.data.byteStream());
                bufferedReader = new BufferedReader(inputStreamReader);

                try {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    bufferedReader.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String stringResponse = stringBuilder.toString();

            return stringResponse;
    }
}
