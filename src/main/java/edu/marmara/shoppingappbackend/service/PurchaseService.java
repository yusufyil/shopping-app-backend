package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.PurchaseRequest;
import edu.marmara.shoppingappbackend.dto.PurchaseResponse;
import edu.marmara.shoppingappbackend.model.Order;
import edu.marmara.shoppingappbackend.model.Purchase;
import edu.marmara.shoppingappbackend.repository.OrderRepository;
import edu.marmara.shoppingappbackend.repository.PurchaseRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    PurchaseRepository purchaseRepository;
    OrderRepository orderRepository;

    public PurchaseService(PurchaseRepository purchaseRepository, OrderRepository orderRepository) {
        this.purchaseRepository = purchaseRepository;
        this.orderRepository = orderRepository;
    }

    public PurchaseResponse savePurchase(PurchaseRequest purchaseRequest) {
        Purchase purchase = MappingHelper.map(purchaseRequest, Purchase.class);
        List<Order> orderedProducts = purchase.getOrderedProducts();
        orderRepository.saveAll(orderedProducts);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return MappingHelper.map(savedPurchase, PurchaseResponse.class);
    }

    public PurchaseResponse getPurchase(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
        return MappingHelper.map(purchase, PurchaseResponse.class);
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }

    public List<PurchaseResponse> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return MappingHelper.mapList(purchases, PurchaseResponse.class);
    }

}
