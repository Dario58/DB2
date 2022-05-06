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
import java.util.Collections;
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
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("products", products);
        session.setAttribute("products", products);
        String path = "/WEB-INF/homepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getParameter("buyButton") != null) {
            int bId = Integer.parseInt(req.getParameter("buyButton"));

            List<Product> products = (ArrayList<Product>) req.getSession().getAttribute("products");
            Product currentProduct = null;

            for(Product product : products) {
                if(product.getBundle().getId() == bId) currentProduct = product;
            }

            if(currentProduct != null) {
                req.getSession().setAttribute("currentProduct", currentProduct);
                resp.sendRedirect(getServletContext().getContextPath() + "/order");
                return;
            }
        }
        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("errorMessage", "Couldn't read the bundle.");

        templateEngine.process("/WEB-INF/homepage.html", ctx, resp.getWriter());
    }
}
