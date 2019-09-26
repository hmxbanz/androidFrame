package cn.noname.app.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hmxbanz on 2017/9/20.
 */
@Entity
public class DownloadGame {
    @Id(autoincrement = true)
    private Long id;
    private String gameName;
    private int gameId;
    @Generated(hash = 828856170)
    public DownloadGame(Long id, String gameName, int gameId) {
        this.id = id;
        this.gameName = gameName;
        this.gameId = gameId;
    }
    @Generated(hash = 661236629)
    public DownloadGame() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGameName() {
        return this.gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public int getGameId() {
        return this.gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}

