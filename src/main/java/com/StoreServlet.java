package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@WebServlet(
        name = "storeServlet",
        urlPatterns = "/shop"
)
public class StoreServlet extends HttpServlet {
    private final Map<Integer, String> products = new Hashtable<>();

    public StoreServlet() {
        this.products.put(1, "iPhone");
        this.products.put(2, "iPad");
        this.products.put(3, "iMac");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "browser";
        }
        switch (action) {
            case "addToCart":
                this.addToCart(request, response);
                break;
            case "viewCart":
                this.viewCart(request, response);
                break;
            case "browser":
            default:
                this.browse(request, response);
                break;
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (Exception e) {
            response.sendRedirect("shop");
            return;
        }

        // 在Servlet中使用Session
        HttpSession session = request.getSession();// 如果session 不存在，返回null
        // HttpSession session = request.getSession(false); // 如果session不存在，则创建一个
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new Hashtable<Integer, Integer>());
        }

        @SuppressWarnings("unchecked")
        // 获取会话中的特性，并强制转换
        Map<Integer, Integer> cart =
                (Map<Integer, Integer>) session.getAttribute("cart");
        if (!cart.containsKey(productId)) {
            cart.put(productId, 0);
        }
        cart.put(productId, cart.get(productId) + 1);
        response.sendRedirect("shop?action=viewCart");
    }

    private void viewCart(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setAttribute("products", this.products);
        request.getRequestDispatcher("/WEB-INF/jsp/view/viewCart.jsp")
                .forward(request, response);
    }

    private void browse(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setAttribute("products", this.products);
        request.getRequestDispatcher("/WEB-INF/jsp/view/browse.jsp")
                .forward(request, response);
    }
}
