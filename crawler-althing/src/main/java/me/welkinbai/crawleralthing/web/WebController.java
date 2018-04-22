package me.welkinbai.crawleralthing.web;

import me.welkinbai.crawleralthing.config.PathPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    private PathPool pathPool;

    @RequestMapping(value = "/geta", method = RequestMethod.GET)
    public String getA() {
        return String.valueOf(pathPool.getList());
    }
}
