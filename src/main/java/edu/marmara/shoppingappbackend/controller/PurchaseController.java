package edu.marmara.shoppingappbackend.controller;

import edu.marmara.shoppingappbackend.dto.PurchaseRequest;
import edu.marmara.shoppingappbackend.dto.PurchaseResponse;
import edu.marmara.shoppingappbackend.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/purchase")
public class PurchaseController {

    PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "Save a purchase.")
    @PostMapping()
    public ResponseEntity<PurchaseResponse> savePurchase(@RequestBody PurchaseRequest purchaseRequest) {
        PurchaseResponse purchaseResponse = purchaseService.savePurchase(purchaseRequest);
        return ResponseEntity.ok(purchaseResponse);
    }

    @Operation(summary = "Get a purchase with given id.")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchase(@PathVariable Long id) {
        PurchaseResponse purchaseResponse = purchaseService.getPurchase(id);
        return ResponseEntity.ok(purchaseResponse);
    }

    @Operation(summary = "Delete a purchase with given id.")
    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
    }

    @Operation(summary = "Get all purchases.")
    @GetMapping()
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> purchaseResponses = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchaseResponses);
    }
}
