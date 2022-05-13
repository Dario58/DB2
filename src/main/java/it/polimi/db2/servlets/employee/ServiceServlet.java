package it.polimi.db2.servlets.employee;

import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.ServiceExistentException;
import it.polimi.db2.exceptions.ValidityPeriodExistentException;
import it.polimi.db2.services.ServiceService;
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

@WebServlet(name = "ServiceServlet", value = "/employee/service")
public class ServiceServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/ServiceService")
    private ServiceService serviceService;

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

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/employee/service.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        assert req.getSession().getAttribute("serviceType") != null;
        String typeOfService = (String) req.getSession().getAttribute("serviceType");
        ServiceEntity serviceEntity = null;
        String error = "";

        switch(typeOfService) {
            case "FI":
                int fiGBs = Integer.parseInt(req.getParameter("fiGBs"));
                int fiExtraGBsCost = Integer.parseInt(req.getParameter("fiExtraGBsCost"));
                if (fiGBs == 0 || fiExtraGBsCost == 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing values");
                    return;
                }
                try {
                    serviceService.checkValidity(typeOfService, fiGBs, fiExtraGBsCost);
                    serviceEntity = new ServiceEntity(typeOfService, fiGBs, fiExtraGBsCost);
                    serviceService.createService(serviceEntity);
                } catch (ServiceExistentException | PersistenceException e) {
                    error = e.getMessage();
                }
                break;

            case "MP":
                int mpMins = Integer.parseInt(req.getParameter("mpMins"));
                int mpSms = Integer.parseInt(req.getParameter("mpSms"));
                int mpExtraMinsCost = Integer.parseInt(req.getParameter("mpExtraMinsCost"));
                int mpExtraSmsCost = Integer.parseInt(req.getParameter("mpExtraSmsCost"));
                if (mpMins == 0 || mpSms == 0 || mpExtraMinsCost == 0 || mpExtraSmsCost == 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing values");
                    return;
                }
                try {
                    serviceService.checkValidity(typeOfService, mpMins, mpSms, mpExtraMinsCost, mpExtraSmsCost);
                    serviceEntity = new ServiceEntity(typeOfService, mpMins, mpSms, mpExtraMinsCost, mpExtraSmsCost);
                    serviceService.createService(serviceEntity);
                } catch (ServiceExistentException | PersistenceException e) {
                    error = e.getMessage();
                }
                break;

            case "MI":
                int miGBs = Integer.parseInt(req.getParameter("miGBs"));
                int miExtraGBsCost = Integer.parseInt(req.getParameter("miExtraGBsCost"));
                if (miGBs == 0 || miExtraGBsCost == 0) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing values");
                    return;
                }
                try {
                    serviceService.checkValidity(typeOfService, miGBs, miExtraGBsCost);
                    serviceEntity = new ServiceEntity(typeOfService, miGBs, miExtraGBsCost);
                    serviceService.createService(serviceEntity);
                } catch (ServiceExistentException | PersistenceException e) {
                    error = e.getMessage();
                }
                break;
        }


        if (serviceEntity == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", error);
            templateEngine.process("/WEB-INF/employee/service.html", ctx, resp.getWriter());
            return;
        }

        if (req.getSession().getAttribute("doRedirect") != null && (Boolean) req.getSession().getAttribute("doRedirect") ) {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/package");
        } else {
            resp.sendRedirect(getServletContext().getContextPath() + "/employee/homepage");
        }
    }
}
