package com.aperise;

import com.aperise.bean.Game;
import com.aperise.bean.User;
import com.aperise.dao.GameDao;
import com.aperise.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GameController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    GameDao gameDao;

    @RequestMapping("/game/info")
    public Game gameinfo(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        Game game = gameDao.getGame(id);
        return game;
    }

    @RequestMapping("/game/name")
    public String username(@RequestParam(value = "id", defaultValue = "1") int id) {
        System.out.println("id=" + id);
        String username = gameDao.getNameById(id);
        return username;
    }

    @RequestMapping("/game")
    public Game game(@RequestParam(value = "id", defaultValue = "1") int id, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        System.out.println("user:offset=" + offset);
        Game game = gameDao.getGameWithPlayers(id, offset, 20);
        return game;
    }

    @RequestMapping("/games")
    public List<Game> games(@RequestParam(value = "offset", defaultValue = "0") int offset) {
        System.out.println("user:offset=" + offset);
        List<Game> games = gameDao.getAllGameWithPlayers(offset, 20);
        return games;
    }

    @RequestMapping("/game/list")
    public List<Game> gameList(@RequestParam(value = "offset", defaultValue = "0") int offset) {
        System.out.println("offset=" + offset);
        List<Game> games = gameDao.getAllGame(offset, 20);
        return games;
    }

}
