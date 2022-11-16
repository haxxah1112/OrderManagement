package com.homework.order.controller;

import com.homework.order.dto.OrderDto;
import com.homework.order.dto.OrderResponse;
import com.homework.order.dto.Result;
import com.homework.order.entity.Order;
import com.homework.order.entity.OrderStatus;
import com.homework.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PostMapping("/api/orders/save")
    public ResponseEntity saveOrder(@RequestBody OrderDto orderDto){
        Order order = orderService.saveOrder(orderDto);
        return ResponseEntity.ok(Result.createSuccessResult(order));
    }

    @Operation(summary = "주문 접수처리",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/api/orders/{id}/accept")
    public ResponseEntity acceptOrder(@PathVariable Long id){
        Order order = orderService.updateOrder(id, OrderStatus.ACCEPT);
        return ResponseEntity.ok(Result.createSuccessResult(order));
    }

    @Operation(summary = "주문 완료처리",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @PatchMapping("/api/orders/{id}/complete")
    public ResponseEntity completeOrder(@PathVariable Long id){
        Order order = orderService.updateOrder(id, OrderStatus.COMPLETE);
        return ResponseEntity.ok(Result.createSuccessResult(order));
    }

    @Operation(summary = "단일 주문조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Order.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/api/orders/{id}")
    public ResponseEntity getOrder(@PathVariable Long id){
        Order order = orderService.getOrder(id);
        return ResponseEntity.ok(Result.createSuccessResult(order));
    }

    @Operation(summary = "주문 목록조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Parameter", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    @GetMapping("/api/orders")
    public ResponseEntity getOrders(@PageableDefault(size = 10) Pageable pageable){
        SliceImpl<OrderResponse> orders = orderService.getOrders(pageable);
        return ResponseEntity.ok(Result.createSuccessResult(orders));
    }
}
