package it.polimi.db2.servlets.employee;

import it.polimi.db2.entities.EmployeeEntity;
import it.polimi.db2.exception.CredentialException;
import it.polimi.db2.services.EmployeeService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmployeeLoginServlet", value = "/employee/login")
public class LoginServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/EmployeeService")
    private EmployeeService employeeService;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(getServletContext().getContextPath() + "/employee");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = StringEscapeUtils.escapeJava(req.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(req.getParameter("password"));

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value.");
            return;
        }

        EmployeeEntity employee;
        try {
            employee = employeeService.checkCredentials(username, password);
        } catch (CredentialException | NonUniqueResultException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials.");
            return;
        }

        if (employee == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "Incorrect username or password.");

            templateEngine.process("/WEB-INF/employee/index.html", ctx, resp.getWriter());
        } else {
            req.getSession().setAttribute("employee", employee);
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/homepage");
        }
    }
}

