package com.app.service;

import com.app.dao.*;
import com.app.dao.generic.DbStatus;
import com.app.model.*;
import com.app.model.dto.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface MyService {
    DbStatus addNewCustomer(String name, String surname, Integer age, String countryName) throws Exception;
    DbStatus removeCustomer(Long id) throws Exception;
    DbStatus updateCustomer(Long id, String name, String surname, Integer age, String countryName) throws Exception;
    String findCustomerUsingID(Long id) throws Exception;
    String findCustomerUsingNameSurnameCountry(String name, String surname, String countryName) throws Exception;

    DbStatus addNewShop(String shopName, String countryName) throws Exception;
    DbStatus removeShop(Long id) throws Exception;
    DbStatus updateShop(Long id, String shopName, String countryName) throws Exception;
    String findShopUsingID(Long id) throws Exception;
    String findShopUsingName(String name) throws Exception;

    DbStatus addNewProducer(String producerName, String countryName, String tradeName) throws Exception;
    DbStatus removeProducer(Long id) throws Exception;
    DbStatus updateProducer(Long id, String producerName, String countryName, String tradeName) throws Exception;
    String findProducerUsingID(Long id) throws Exception;
    String findProducerUsingName(String name) throws Exception;

    DbStatus addNewProduct(String productName, String price, String categoryName, String producerName, String producersCountryName, String ... eGuarantee) throws Exception;
    DbStatus removeProduct(Long id) throws Exception;
    DbStatus updateProduct(Long id, String productName, String price, String categoryName, String producerName, String producersCountryName, String ... eGuarantee) throws Exception;
    String findProductUsingID(Long id) throws Exception;
    String findProductUsingName(String name) throws Exception;

    DbStatus addNewStock(String productName, String categoryName, String shopName, String countryName, Integer quantity) throws Exception;
    DbStatus removeStock(Long id) throws Exception;
    String findStockUsingID(Long id) throws Exception;

    DbStatus addNewCustomerOrder(String customerName, String customerSurname, String customersCountryName, String productName, String productsCategoryName, Integer quantity, LocalDateTime orderDate, Integer discount, EPayment ePayment) throws Exception;
    DbStatus removeOrder(Long id) throws Exception;
    DbStatus updateCustomerOrder(Long id,String customerName, String customerSurname, String customersCountryName, String productName, String productsCategoryName, Integer quantity, LocalDateTime orderDate, Integer discount, EPayment ePayment) throws Exception;
    String findCustomerOrderUsingID(Long id) throws Exception;

    DbStatus addNewCountry(String countryName) throws Exception;
    DbStatus removeCountry(Long id) throws Exception;
    DbStatus updateCountry(Long id, String countryName) throws Exception;
    Country findCountryUsingID(Long id) throws Exception;
    Country findCountryUsingName(String name) throws Exception;

    DbStatus addNewTrade(String tradeName) throws  Exception;
    DbStatus removeTrade(Long id) throws Exception;
    DbStatus updateTrade(Long id, String tradeName) throws Exception;
    Trade findTradeUsingID(Long id) throws Exception;
    Trade findTradeUsingName(String name) throws Exception;

    DbStatus addNewCategory(String categoryName) throws Exception;
    DbStatus removeCategory(Long id) throws Exception;
    DbStatus updateCategory(Long id, String categoryName) throws Exception;
    String findCategoryUsingID(Long id) throws Exception;
    String findCategoryUsingName(String name) throws Exception;

    String getMostExpensiveProductsInEachCategory();
    List<Product> getProductsOrderedByClientsFrom(String countryName, int fromAge, int toAge) throws Exception;
    List<Product> getProductsGuaranteedWith(EGuarantee eGuarantee);
    List<Shop> getShopsContainingForeignProducts();
    Map<Producer, Integer> getProducersWhoProducedMoreThan(String tradeName, Integer amount);
    List<CustomerOrder> getOrdersOrderedIn(LocalDateTime fromTime, LocalDateTime toTime);
    Map<Producer, List<Product>> getProductsOrderedByCustomer(String customerName, String customerSurname, String customersCountryName);
    String getCustomersWhoOrderedCountryProducts();
    DbStatus dropDataFromAllTables();
    DbStatus initializeDataBase();
    void makeLogFile();



    default DbStatus addNewOrderFromCSV(String customerName, String customerSurname, String customersCountryName, String productName, Integer quantity, LocalDateTime orderDate, Integer discount, String ePayment) throws Exception {
        CustomerDao customerDao = new CustomerDaoImpl();
        PaymentDao paymentDao = new PaymentDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        CustomerOrderDao customerOrderDao = new CustomerOrderDaoImpl();
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDate(orderDate);
        customerOrder.setDiscount(new BigDecimal(discount));
        customerOrder.setCustomer(customerDao.findOneByNameSurnameCountry(customerName, customerSurname, customersCountryName).get());
        customerOrder.setPayment(paymentDao.findOneByName(Payment.recognizePaymentMethod(ePayment)).get());
        customerOrder.setProduct(productDao.findOneByName(productName).get());
        customerOrder.setQuantity(quantity);
        customerOrderDao.add(customerOrder);
        return DbStatus.OK;
    }
}
