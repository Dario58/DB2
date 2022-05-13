package it.polimi.db2.servlets.employee;

import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.ValidityPeriodExistentException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServiceSelectionServlet", value = "/employee/serviceSelection")
public class ServiceSelectionServlet extends HttpServlet {
    private TemplateEngine templateEngine;

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

        if(req.getParameter("goToServiceSelection") != null && Boolean.parseBoolean(req.getParameter("goToServiceSelection"))) {
            req.getSession().setAttribute("doRedirect", true);
        }

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/employee/serviceSelection.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        assert req.getParameter("serviceSelection") != null;

        switch(req.getParameter("serviceSelection")) {
            case "FI":
                req.getSession().setAttribute("serviceType", "FI");
                break;

            case "MP":
                req.getSession().setAttribute("serviceType", "MP");
                break;

            case "MI":
                req.getSession().setAttribute("serviceType", "MI");
                break;
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        templateEngine.process("/WEB-INF/employee/service.html", ctx, resp.getWriter());
    }
}
