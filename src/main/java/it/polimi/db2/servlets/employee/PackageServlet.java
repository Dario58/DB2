package it.polimi.db2.servlets.employee;
import it.polimi.db2.entities.*;
import it.polimi.db2.services.PackageService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet(name = "PackageServlet", value = "/employee/package")
public class PackageServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/PackageService")
    private PackageService packageService;

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

        HttpSession session = req.getSession();

        List<OptionalProductEntity> optionalProductEntityList = new ArrayList<>();
        List<ValidityPeriodEntity> validityPeriodEntityList = new ArrayList<>();

        try {
           optionalProductEntityList = packageService.retrieveAllOptional();
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't retrieve optional products.");
        }
        try {
            validityPeriodEntityList = packageService.retrieveAllPeriods();
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't retrieve validity periods.");
        }

            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("optionalProductEntityList", optionalProductEntityList);
            session.setAttribute("optionalProductEntityList", optionalProductEntityList);
            ctx.setVariable("validityPeriodEntityList", validityPeriodEntityList);
            session.setAttribute("validityPeriodEntityList", validityPeriodEntityList);
            String path = "/WEB-INF/employee/package.html";


            templateEngine.process(path, ctx, resp.getWriter());


    }



}