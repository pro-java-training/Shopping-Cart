<%@ page import="java.util.Map" %>
<!doctype html>
<html lang="en">
<head>
    <title>View Cart</title>
    <a href="<c:url value="/shop" />">Product List</a><br/><br/>
    <a href="<c:url value="/shop?action=emptyCart" />">Empty Cart</a><br/><br/>
    <%
        @SuppressWarnings("unchecked")
        Map<Integer, String> products =
            (Map<Integer, String>) request.getAttribute("products");

        // 从session中读取购物车
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart =
                (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null || cart.size() == 0) {
            out.println("Your cart is empty.");
        } else {
            for (int id : cart.keySet()) {
                out.println(products.get(id) + " ( amount: " + cart.get(id) + ")<br/>");
            }
        }
    %>
</head>
<body>

</body>
</html>