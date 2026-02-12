package se.iths.stefan.labbdrift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.stefan.labbdrift.model.Order;
import se.iths.stefan.labbdrift.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String getOrders(Model model) {
        model.addAttribute("order", orderService.displayOrders());
        return "orders/order";
    }

    @GetMapping("/new")
    public String newOrder(Model model) {
        model.addAttribute("order", new Order());
        return "orders/new-order";
    }

    @PostMapping()
    public String createOrder(@ModelAttribute Order order) {
        Order order1 = orderService.createOrder(order);
        return "redirect:/orders";
    }

    @PutMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrder(id);
        model.addAttribute("order", order);
        return "orders/update-order";
    }
    

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
