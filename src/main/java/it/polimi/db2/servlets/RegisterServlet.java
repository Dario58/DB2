package it.polimi.db2.servlets;

import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.exceptions.CredentialException;
import it.polimi.db2.exceptions.InputException;
import it.polimi.db2.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private String registerPath = new String();
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;


    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        if(Boolean.parseBoolean(req.getParameter("doRedirect"))) {
            req.getSession().setAttribute("doRedirect", true);
        }
        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        registerPath = "/WEB-INF/register.html";
        templateEngine.process(registerPath, ctx, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = StringEscapeUtils.escapeJava(req.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(req.getParameter("password"));
        String confirmPassword = StringEscapeUtils.escapeJava(req.getParameter("confirm_password"));
        String email = StringEscapeUtils.escapeJava(req.getParameter("email"));

        if (username == null || password == null || confirmPassword == null || email == null ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value.");
            return;
        }

        UserEntity user = null;
        String error = "";

        try {
            checkInputs(username, password, confirmPassword, email);
            user = userService.addNewUser(username, password, email);
        } catch (InputException | CredentialException e) {
            error = e.getMessage();
        } catch (Exception e) {
            error = "Could not complete the registration!";
        }

        if (user == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);

            templateEngine.process(registerPath, ctx, resp.getWriter());
        } else {
            req.getSession().setAttribute("user", user);
            if(req.getSession().getAttribute("doRedirect") != null && (Boolean) req.getSession().getAttribute("doRedirect")){
                resp.sendRedirect(getServletContext().getContextPath() + "/cart");
            }else {
                resp.sendRedirect(getServletContext().getContextPath() + "/login");
            }
        }
    }

    private void checkInputs(String username, String password, String confirmPassword, String email) throws InputException {
        if (username.length() > 45) {
            throw new InputException("Username is too long (max 45 chars)!");
        }

        if (email.length() > 90) {
            throw new InputException("Email is too long (max 90 chars)!");
        }

        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        if (!pattern.matcher(email).matches()) {
            throw new InputException("Invalid email address!");
        }

        if (password.length() > 45 || password.length() < 8) {
            throw new InputException("Password length not valid (min 8 max 45 chars)!");
        }

        if (!confirmPassword.equals(password)) {
            throw new InputException("Password entered are not the same!");
        }
    }



}
