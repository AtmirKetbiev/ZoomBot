package ru.ketbiev.bot.zoom.repositories;

import org.json.JSONObject;
import org.telegram.abilitybots.api.db.DBContext;
import ru.ketbiev.bot.zoom.model.UserToken;
import ru.ketbiev.bot.zoom.zoomapi.Authorization;

import java.util.Date;
import java.util.Map;

public class TokenRepositories {

    private Map<Long, UserToken> tokenMap;

    public TokenRepositories(DBContext db) {
        this.tokenMap = db.getMap("Teachers");
    }

    public UserToken get(Long id) {
        System.out.println(tokenMap);
        if (tokenMap.size()==0 || !tokenMap.containsKey(id)) {
            return null;
        } else if (checkTime(id)) {
            refreshToken(id, tokenMap.get(id).getRefreshToken());
        }
        return tokenMap.get(id);
    }

    public void set(Long id, JSONObject object) {
        UserToken ut = new UserToken(object.get("access_token").toString(),
                                    object.get("refresh_token").toString(),
                                    new Date());
        this.tokenMap.put(id, ut);
        System.out.println("hm");
    }

    public boolean checkTime(Long id) {
        return new Date().getTime() - tokenMap.get(id).getCreateDate().getTime() > 3600000;
    }

    public void refreshToken(Long id, String refresh) {
        Authorization authorization = new Authorization();
        JSONObject object = authorization.refresh(refresh);
        set(id, object);
    }

}
