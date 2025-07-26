package de.brunokrams.lichessclient.controller;

import de.brunokrams.lichessclient.config.LichessConfig;
import de.brunokrams.lichessclient.model.lichess.LichessOAuthService;
import de.brunokrams.lichessclient.view.SceneSwitcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

// TODO add test
@Controller
public class LoginController {

    private final LichessOAuthService oauthService;
    private final SceneSwitcher sceneSwitcher;

    @FXML
    private ProgressIndicator progressIndicator;

    public LoginController(LichessOAuthService oauthService, SceneSwitcher sceneSwitcher) {
        this.oauthService = oauthService;
        this.sceneSwitcher = sceneSwitcher;
    }

    @FXML
    public void login() throws Exception {
        progressIndicator.setVisible(true);
        oauthService.startPKCEFlow();
    }

    @GetMapping(LichessConfig.OAuth.REDIRECT_ENDPOINT)
    public ModelAndView authenticationCode(@RequestParam("code") String authenticationCode) {
        oauthService.finishPKCEFlow(authenticationCode);
        progressIndicator.setVisible(false);
        Platform.runLater(sceneSwitcher::displayMain);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authresponse.html");
        return modelAndView;
    }
}



