package it.polimi.db2.servlets;

import it.polimi.db2.entities.OrderEntity;
import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.services.OrderCreationService;
import it.polimi.db2.services.UserService;
import it.polimi.db2.utils.FilledOrder;
import it.polimi.db2.utils.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.criteria.Order;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;

@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.services/OrderCreationService")
    private OrderCreationService orderCreationService;

    @EJB(name = "it.polimi.db2.services/UserService")
    private UserService userService;

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
        String path = "/WEB-INF/cart.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilledOrder filledOrder = (FilledOrder) req.getSession().getAttribute("filledOrder");

        assert filledOrder != null;

        UserEntity current = (UserEntity) req.getSession().getAttribute("user");
        assert current != null;
        OrderEntity order = orderCreationService.createOrder(filledOrder, current);

        assert order != null;

        if(req.getParameter("purchaseOk") != null && Boolean.parseBoolean(req.getParameter("purchaseOk"))) {
            orderCreationService.updateOrderOk(order.getId());
            orderCreationService.createActivationSchedule(order.getId());
            System.out.println("PURCHASE OK");
        }
        if(req.getParameter("purchaseKo") != null && Boolean.parseBoolean(req.getParameter("purchaseKo"))) {
            orderCreationService.updateOrderKo(order.getId());
            userService.setUserInsolvent(((UserEntity) req.getSession().getAttribute("user")).getId());
            System.out.println("PURCHASE KO");
        }

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/homepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

}
