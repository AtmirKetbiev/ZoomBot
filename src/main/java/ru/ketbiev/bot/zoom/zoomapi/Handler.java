package ru.ketbiev.bot.zoom.zoomapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class Handler {

    public String getRequest (String url, Map<String, String> headerParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet get = new HttpGet(url);
            for(String s : headerParams.keySet()) {
                get.setHeader(s, headerParams.get(s));
            }
            HttpResponse response = client.execute(get);
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            return "Ooops";
        }
    }

    public String postRequest (String url, Map<String, String> headerParams, JSONObject bodyParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(url);
            for(String s : headerParams.keySet()) {
                post.setHeader(s, headerParams.get(s));
            }
            post.setEntity(new StringEntity(bodyParams.toString()));
            client.execute(post);
            return "Ok";
        } catch (IOException ignored) {
            return "Ooops";
        }
    }

    public String patchRequest(String url, Map<String, String> headerParams, JSONObject bodyParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPatch patch = new HttpPatch(url);
            for(String s : headerParams.keySet()) {
                patch.setHeader(s, headerParams.get(s));
            }
            patch.setEntity(new StringEntity(bodyParams.toString()));
            client.execute(patch);
            return "Ok";
        } catch (IOException ignored) {
            return "Ooops";
        }
    }

    public String deleteRequest (String url, Map<String, String> headerParams) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpDelete delete = new HttpDelete(url);
            for(String s : headerParams.keySet()) {
                delete.setHeader(s, headerParams.get(s));
            }
            client.execute(delete);
            return "Ok";
        } catch (IOException ignored) {
            return "Ooops";
        }
    }
}
