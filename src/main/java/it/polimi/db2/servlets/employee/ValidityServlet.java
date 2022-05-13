package it.polimi.db2.servlets.employee;

import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.ValidityPeriodExistentException;
import it.polimi.db2.services.ValidityService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ValidityServlet", value = "/employee/validity")
public class ValidityServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/ValidityService")
    private ValidityService validityService;

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

        if(req.getParameter("goToValidity") != null && Boolean.parseBoolean(req.getParameter("goToValidity"))) {
            req.getSession().setAttribute("doRedirect", true);
        }
        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/employee/validity.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        assert !(req.getParameter("monthlyFee") != null || req.getParameter("months") != null);
        int monthlyFee = Integer.parseInt(req.getParameter("monthlyFee"));
        int months = Integer.parseInt(req.getParameter("months"));

        if (monthlyFee == 0 || months == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing values");
            return;
        }

        ValidityPeriodEntity validityPeriod = null;
        String error = "";

        try {
            validityService.checkValidity(months, monthlyFee);
            validityPeriod = new ValidityPeriodEntity(months, monthlyFee);
            validityService.createValidityPeriod(validityPeriod);
        } catch (ValidityPeriodExistentException | PersistenceException e) {
            error = e.getMessage();
        }
        if (validityPeriod == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);
            templateEngine.process("/WEB-INF/employee/validity.html", ctx, resp.getWriter());
            return;
        }

        if (req.getSession().getAttribute("doRedirect") != null && (Boolean) req.getSession().getAttribute("doRedirect") ) {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/package");
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/homepage");
        }
    }
}
