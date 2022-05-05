package it.polimi.db2.servlets;

import it.polimi.db2.entities.*;
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

@WebServlet(name = "HomepageServlet", value = "/homepage")
public class HomepageServlet extends HttpServlet {

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
        UserEntity user = (UserEntity) session.getAttribute("user");

        List<BundleEntity> bundleList = new ArrayList<>();

        try {
            bundleList = bundleService.retrieveAllBundles();
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't retrieve bundles.");
        }

        List<Product> products = null;
        if(bundleList != null) {
            products = new ArrayList<>();
            for(BundleEntity b : bundleList) products.add(bundleService.buildProduct(b, b.getId()));
            System.out.println(products.get(0));
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("products", products);
        String path = "/WEB-INF/homepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String order = StringEscapeUtils.escapeJava(req.getParameter("title"));
        String services = StringEscapeUtils.escapeJava(req.getParameter("services"));
        String offer = StringEscapeUtils.escapeJava(req.getParameter("offer")); //there is more than one offer how can you take them all?
        String validMonths = StringEscapeUtils.escapeJava(req.getParameter("months"));
        String costPerMonth = StringEscapeUtils.escapeJava(req.getParameter("costPerMonth"));

        if(order == null /*|| order = ""*/) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Your cart is empty, choose a service package to " +
                    "continue purchasing");
        }else{
            req.getSession().setAttribute("title", order);
            req.getSession().setAttribute("services", services);
            req.getSession().setAttribute("offer", offer);
            req.getSession().setAttribute("months", validMonths);
            req.getSession().setAttribute("costPerMonth", costPerMonth);

            resp.sendRedirect(getServletContext().getContextPath() + "/order");
        }
    }
}
