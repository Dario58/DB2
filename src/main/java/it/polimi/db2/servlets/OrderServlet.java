package it.polimi.db2.servlets;

import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.services.BundleService;
import it.polimi.db2.utils.DateParser;
import it.polimi.db2.utils.FilledOrder;
import it.polimi.db2.utils.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/order")
public class OrderServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/BundleService")
    private BundleService bundleService;

    private final DateParser dateParser = new DateParser();

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
        String path = "/WEB-INF/order.html";

        ctx.getSession().setAttribute("today", dateParser.getToday());

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Product current = (Product) session.getAttribute("currentProduct");

        ValidityPeriodEntity selectedValidityPeriod = current.getValidityPeriods()
                .stream()
                .filter(validityPeriod -> (Integer.valueOf(req.getParameter("selectedValidityPeriod"))).equals(validityPeriod.getId()))
                .findFirst()
                .orElse(null);

        assert selectedValidityPeriod != null;
        System.out.println("Selected vpId: " + selectedValidityPeriod.toString());

        List<OptionalProductEntity> selectedOptionals = new ArrayList<>();

        for(OptionalProductEntity op : current.getAvailableOptionals()) {
            if(req.getParameter("selectedOptional" + op.getId()) != null && Integer.parseInt(req.getParameter("selectedOptional" + op.getId())) == op.getId()) {
                selectedOptionals.add(op);
            }
        }
        selectedOptionals.forEach(System.out::println);

        FilledOrder filledOrder = null;
        try {
            filledOrder = new FilledOrder(current.getBundle(), selectedValidityPeriod, selectedOptionals, dateParser.parseDate(req.getParameter("subscriptionStart")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert filledOrder != null;
        session.setAttribute("filledOrder", filledOrder);

        System.out.println(filledOrder.toString());

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        templateEngine.process("/WEB-INF/cart.html", ctx, resp.getWriter());
    }
}

