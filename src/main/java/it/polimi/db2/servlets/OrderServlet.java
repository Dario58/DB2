package it.polimi.db2.servlets;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.services.BundleService;
import it.polimi.db2.utils.Product;
import org.apache.commons.text.StringEscapeUtils;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/BundleService")
    private BundleService bundleService;

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
        Product order = (Product) session.getAttribute("title");
        Product services = (Product) session.getAttribute("services");
        Product offer = (Product) session.getAttribute("offer");
        Product validMonths = (Product) session.getAttribute("months");
        Product costPerMonth = (Product) session.getAttribute("costPerMonth");

        List<BundleEntity> bundleList = new ArrayList<>();

        try {
            bundleList = bundleService.retrieveAllBundles();
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't retrieve bundles.");

            ServletContext servletContext = getServletContext();
            WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            String path = "/WEB-INF/order.html";
            templateEngine.process(path, ctx, resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String choice = StringEscapeUtils.escapeJava(req.getParameter("title"));
        req.getSession().setAttribute("title", choice);


        resp.sendRedirect(getServletContext().getContextPath() + "/cart");

    }
}

