package com.epam.ems.controller;

import com.epam.ems.converter.OrderConverter;
import com.epam.ems.dto.OrderDTO;
import com.epam.ems.response.InfoResponse;
import com.epam.ems.validator.OrderValidator;
import com.epam.ems.validator.PaginationValidator;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.DataGenerator;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("orders")
public class OrderController {

    private OrderService orderService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;

    @Autowired
    public OrderController(OrderService orderService, DataGenerator dataGeneratorService, MessageSource messageSource) {
        this.orderService = orderService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public HttpEntity<List<OrderDTO>> getOrders(@RequestParam(value = "userId") Long userId, Locale locale, Pagination pagination) throws ServiceException, BusinessValidationException {
        PaginationValidator.validatePagination(pagination, locale);
        List<OrderDTO> ordersDTO = orderService.getByUser(userId, pagination)
                .stream()
                .map(OrderConverter::convertToDto)
                .collect(Collectors.toList());
        addOrdersHATEOAS(ordersDTO);
        return ResponseEntity.ok(ordersDTO);
    }

    @GetMapping("/{orderId}")
    public Map<String, Object> getOrderById(@PathVariable Long orderId, @RequestParam(value = "include", required = false) String include) throws ServiceException, BusinessValidationException {
        Order order = orderService.get(orderId);
        return OrderConverter.convertOrderToMap(order, include);
    }

    @PostMapping
    public InfoResponse addOrder(@RequestBody OrderDTO orderDTO, Locale locale) throws ServiceException, BusinessValidationException {
        OrderValidator.validateOrderDTO(orderDTO, locale);
        Order order = OrderConverter.convertToEntity(orderDTO);
        Long orderId = orderService.create(order);
        String message = messageSource.getMessage("label.order.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ":" + orderId,
                HttpStatus.CREATED.toString());
    }

    @PostMapping("/generate")
    public InfoResponse generateOrders(Locale locale, @RequestParam(value = "count") int count) throws ServiceException {
        dataGeneratorService.generateOrders(count);
        int numberOfRows = orderService.getAllOrders().size();
        String message = messageSource.getMessage("label.orders.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addOrdersHATEOAS(List<OrderDTO> ordersDTO) throws ServiceException, BusinessValidationException {
        for (OrderDTO orderDTO : ordersDTO) {
            orderDTO.add(linkTo(methodOn(OrderController.class).getOrderById(orderDTO.getOrderId(), null)).withSelfRel());
        }
    }
}