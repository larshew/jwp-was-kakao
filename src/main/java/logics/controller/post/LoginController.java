package logics.controller.post;

import logics.Service.SessionService;
import logics.controller.Controller;
import utils.requests.HttpRequest;
import utils.response.HttpResponse;
import utils.response.HttpResponseVersion1;
import utils.session.Session;

/**
 * make response when receiving login-request.
 */
public class LoginController implements Controller {

    public static final Controller instance = new LoginController();
    private LoginController(){}
    private final SessionService sessionService = SessionService.instance;
    private static final HttpResponse loginSuccess = new HttpResponseVersion1().setResponseCode(302)
            .setHeader("Location", "/index.html");
    private static final HttpResponse loginFail = new HttpResponseVersion1().setResponseCode(302)
            .setHeader("Location", "/user/login_failed.html");
    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) {
        try {
            Session session = sessionService.login(httpRequest.getBody());
            return loginSuccess.setHeader("Set-Cookie", session.toCookieHeader());
        } catch(IllegalArgumentException e){
            return loginFail;
        }
    }
}