package ru.ketbiev.bot.zoom.repositories;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;
import ru.ketbiev.bot.zoom.model.UserToken;
import ru.ketbiev.bot.zoom.zoomapi.Authorization;

import java.util.Date;
import java.util.Map;

@Repository
public class TokenRepositories {

    private final Map<Long, UserToken> tokenMap;

    public TokenRepositories(DBContext db) {
        this.tokenMap = db.getMap("Token");
    }

    public UserToken get(Long id) {
        if (tokenMap.size() == 0 || !tokenMap.containsKey(id)) {
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
    }

    public void delete(Long id) {
        tokenMap.remove(id);
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
