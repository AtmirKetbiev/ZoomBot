package ru.ketbiev.bot.zoom.zoomapi;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.json.JSONObject;
import ru.ketbiev.bot.zoom.Const;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;

public class Authorization {

    public JSONObject getOAuthToken() {
        try {
            String authorizationUrl;
            OAuthClientRequest authorizationCodeRequest = OAuthClientRequest
                    .authorizationLocation("https://zoom.us/oauth/authorize")
                    .setResponseType("code")
                    .setClientId(Const.CLIENT_ID_ZOOM)
                    .setRedirectURI(Const.REDIRECT_URL_ZOOM)
                    .buildQueryMessage();

            System.out.println("Opening browser for authentication at " + authorizationCodeRequest.getLocationUri());
            openBrowser(authorizationCodeRequest.getLocationUri());

            System.out.println("Get token");
            String codeUser = httpReceiver();

            //.............................................................//

            String encodedBytes = Base64.getEncoder().encodeToString(
                    (Const.CLIENT_ID_ZOOM+":"+Const.SECRET_ID_ZOOM).getBytes());

            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation("https://zoom.us/oauth/token")
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setCode(codeUser)
                    .setRedirectURI(Const.REDIRECT_URL_ZOOM)
                    .buildQueryMessage();

            accessTokenRequest.setHeader("Authorization","Basic "+encodedBytes);

            OAuthClient client = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse=client.resource(accessTokenRequest, OAuth.HttpMethod.POST,
                    OAuthResourceResponse.class);
            return new JSONObject(resourceResponse.getBody());
        } catch (OAuthSystemException | OAuthProblemException | IOException e) {
            return null;
        }
    }

    private void openBrowser(String redirectUri) throws IOException {
        URI myUri = URI.create(redirectUri);
        Desktop.getDesktop().browse(myUri);
    }

    private String httpReceiver() throws IOException {
        return "";
    }

    public JSONObject refresh(String refresh) {
        try{
            String encodedBytes = Base64.getEncoder().encodeToString(
                    (Const.CLIENT_ID_ZOOM+":"+Const.SECRET_ID_ZOOM).getBytes());

            OAuthClientRequest accessTokenRequest = OAuthClientRequest
                    .tokenLocation("https://zoom.us/oauth/token")
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(refresh)
                    .buildQueryMessage();

            accessTokenRequest.setHeader("Authorization","Basic "+encodedBytes);

            OAuthClient client = new OAuthClient(new URLConnectionClient());
            OAuthResourceResponse resourceResponse=client.resource(accessTokenRequest, OAuth.HttpMethod.POST,
                    OAuthResourceResponse.class);
            return new JSONObject(resourceResponse.getBody());
        } catch (OAuthSystemException | OAuthProblemException e) {
            return null;
        }
    }
}
