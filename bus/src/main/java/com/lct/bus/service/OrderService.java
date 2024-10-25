package com.lct.bus.service;

import com.lct.bus.dto.OrderDTO;
import com.lct.bus.models.Order;
import com.lct.bus.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public void createOrder(OrderDTO orderDTO) {
        Boolean existsorder = orderRepository.existsById(orderDTO.getId());
        if (existsorder) {
            new RuntimeException("order đã tồn tại");
        }

        Order o = new Order();

        o.setId(orderDTO.getId());
        o.setUser(orderDTO.getUser());
        o.setTicket(orderDTO.getTicket());
        o.setNumberOfMonth(orderDTO.getNumberOfMonth());
        o.setDateOfPurchase(orderDTO.getDateOfPurchase());
        o.setTotalAmount(orderDTO.getTotalAmount());
        o.setPaid(orderDTO.getPaid());
        o.setCreatedDate(LocalDateTime.now());
        o.setActive(true);
        orderRepository.save(o);
    }

    public void updateOrder(Order order) {
        Order orderUpdate = orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("order not found"));

        orderUpdate.setUser(order.getUser());
        orderUpdate.setTicket(order.getTicket());
        orderUpdate.setNumberOfMonth(order.getNumberOfMonth());
        orderUpdate.setDateOfPurchase(order.getDateOfPurchase());
        orderUpdate.setTotalAmount(order.getTotalAmount());
        orderUpdate.setPaid(order.getPaid());
        orderUpdate.setActive(true);

        orderRepository.save(orderUpdate);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
