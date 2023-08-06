package com.epam.ems.converter;

import com.epam.ems.dto.*;
import com.epam.esm.entity.*;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OrderConverter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private OrderConverter() {
    }

    public static OrderDTO convertToDto(Order order) {
        Timestamp orderDate = order.getOrderDate();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(DATE_FORMAT.format(orderDate));
        orderDTO.setOrderId(order.getId());
        orderDTO.setUserId(order.getUser().getId());
        List<OrderDetail> orderDetails = order.getOrderDetails();
        orderDTO.setCost(order.getCost());
        List<OrderDetailDTO> orderDetailsDTO = orderDetails
                .stream()
                .map(OrderDetailConverter::convertToDto)
                .collect(Collectors.toList());
        orderDTO.setOrderDetails(orderDetailsDTO);
        return orderDTO;
    }

    public static Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        User user = new User();
        user.setId(orderDTO.getUserId());
        order.setUser(user);
        List<OrderDetail> ods = new ArrayList<>();
        Certificate certificate = null;
        List<OrderDetailDTO> orderDetailDTOS = orderDTO.getOrderDetails();
        double cost=0;
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOS) {
            OrderDetail od = new OrderDetail();
            certificate = new Certificate();
            certificate.setId(orderDetailDTO.getCertificateId());
            certificate.setName(orderDetailDTO.getCertificateName());
            certificate.setPrice(orderDetailDTO.getCertificatePrice());
            od.setCertificate(certificate);
            od.setQuantity(orderDetailDTO.getQuantity());
            ods.add(od);
            cost=cost+orderDetailDTO.getQuantity()*orderDetailDTO.getCertificatePrice();
        }
        order.setCost(cost);
        order.setOrderDetails(ods);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        return order;
    }

    public static Map<String, Object> convertOrderToMap(Order order, String includedFields) {
        OrderDTO orderDTO = convertToDto(order);
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderDTO.getOrderId());
        result.put("userId", orderDTO.getUserId());
        result.put("cost", orderDTO.getCost());
        result.put("orderDate", orderDTO.getOrderDate());
        result.put("orderDetails", orderDTO.getOrderDetails());
        if (includedFields != null) {
            String[] filterCriteria = includedFields.split(",");
            List<String> keysForFiltering = Arrays.asList(filterCriteria);
            return result
                    .entrySet()
                    .stream()
                    .filter(e -> keysForFiltering.contains(e.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return result;
        }
    }
}
