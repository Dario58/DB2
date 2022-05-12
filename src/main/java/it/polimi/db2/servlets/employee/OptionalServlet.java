package it.polimi.db2.servlets.employee;

import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.exceptions.CredentialException;
import it.polimi.db2.exceptions.OptionalExistentException;
import it.polimi.db2.services.OptionalService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OptionalServlet", value = "/employee/optional")
public class OptionalServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/OptionalService")
    private OptionalService optionalService;

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

        if(req.getParameter("goToOptional") != null && Boolean.parseBoolean(req.getParameter("goToOptional"))) {
            req.getSession().setAttribute("doRedirect", true);
        }
        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/employee/optional.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = StringEscapeUtils.escapeJava(req.getParameter("title"));
        int months = Integer.parseInt(req.getParameter("MonthlyFee"));

        if (title == null || months == 0 || title.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing values");
            return;
        }

        OptionalProductEntity optionalProduct = null;

        try {
            optionalService.checkValidity(title);
            optionalProduct = new OptionalProductEntity(title, months);
            optionalService.createOptional(optionalProduct);
        } catch (OptionalExistentException | PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to check credentials");
        }
        if (optionalProduct == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "Incorrect title or invalid number of months.");

        }

        if (req.getSession().getAttribute("doRedirect") != null && (Boolean) req.getSession().getAttribute("doRedirect") ) {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/package");
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/homepage");
        }
    }
}

