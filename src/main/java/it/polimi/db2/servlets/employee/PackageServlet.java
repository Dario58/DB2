package it.polimi.db2.servlets.employee;
import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.BundleExistentException;
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

        List<ServiceEntity> services = new ArrayList<>();
        List<OptionalProductEntity> optionalProductEntityList = new ArrayList<>();
        List<ValidityPeriodEntity> validityPeriodEntityList = new ArrayList<>();

        try{
            services = packageService.retrieveAllServices();
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't retrieve services.");
        }
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
            ctx.setVariable("services", services);
            session.setAttribute("services", services);
            ctx.setVariable("optionalProductEntityList", optionalProductEntityList);
            session.setAttribute("optionalProductEntityList", optionalProductEntityList);

            ctx.setVariable("validityPeriodEntityList", validityPeriodEntityList);
            session.setAttribute("validityPeriodEntityList", validityPeriodEntityList);
            String path = "/WEB-INF/employee/package.html";

            templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String error = "";

        String title = "";
        List<ServiceEntity> chosenServices = new ArrayList<>();
        List<ValidityPeriodEntity> chosenValidityPeriods = new ArrayList<>();
        List<OptionalProductEntity> chosenOptionalProducts = new ArrayList<>();

        if(req.getParameter("selectedTitle") != null) {
            title = req.getParameter("selectedTitle");
        }


        for(ServiceEntity s : packageService.retrieveAllServices()) {
            if(req.getParameter("selectedService" + s.getId()) != null && Integer.parseInt(req.getParameter("selectedService" + s.getId())) == s.getId()) {
                chosenServices.add(s);
            }
        }
        if(chosenServices.isEmpty()) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "No service selected: At least one required.");
            templateEngine.process("/WEB-INF/employee/homepage.html", ctx, resp.getWriter());
            return;
        }

        for(ValidityPeriodEntity v : packageService.retrieveAllPeriods()) {
            if(req.getParameter("selectedValidityPeriod" + v.getId()) != null && Integer.parseInt(req.getParameter("selectedValidityPeriod" + v.getId())) == v.getId()) {
                chosenValidityPeriods.add(v);
            }
        }
        if(chosenValidityPeriods.isEmpty()) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "No validity period selected: At least one required.");
            templateEngine.process("/WEB-INF/employee/homepage.html", ctx, resp.getWriter());
            return;
        }

        for(OptionalProductEntity o : packageService.retrieveAllOptional()) {
            if(req.getParameter("selectedOptional" + o.getId()) != null && Integer.parseInt(req.getParameter("selectedOptional" + o.getId())) == o.getId()) {
                chosenOptionalProducts.add(o);
            }
        }

        BundleEntity bundle = null;

        try {
            packageService.checkValidity(title, chosenServices, chosenValidityPeriods, chosenOptionalProducts);
            bundle = new BundleEntity(title, chosenServices, chosenValidityPeriods, chosenOptionalProducts);
            packageService.createBundle(bundle);
        } catch (BundleExistentException | PersistenceException e) {
            error = e.getMessage();
        }
        if (bundle == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);
            templateEngine.process("/WEB-INF/employee/homepage.html", ctx, resp.getWriter());
            return;
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/employee/homepage.html";

        templateEngine.process(path, ctx, resp.getWriter());


    }
}