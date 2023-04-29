package com.example.starbuckscashier;

import com.example.starbuckscashier.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/")
public class StarbucksCashierController {
    
    private final String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI";

    // Generate HMAC SHA-256 Hash
    private String hmac_sha256(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec("kwRg54x2Go9iEdl49jFENRM12Mp711QI".getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes());
            java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
            return encoder.encodeToString(digest);
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("Invalid key exception while converting to HMAC SHA256");
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm");
        }
    }

    @GetMapping
    public String getAction(@ModelAttribute("command") Command command, Model model) {

        command.setRegister("5012349");

        String message = "Starbucks Reserved Order" + "\n\n" +
                "Register: " + command.getRegister() + "\n" +
                "Status:   " + "Ready for New Order" + "\n";

        long ts_long = System.currentTimeMillis();
        String ts_string = String.valueOf(ts_long);
        command.setTimestamp(ts_string);

        String state = "READY";

        String text = state + "/" + ts_string;
        String hash_string = hmac_sha256(text);
        command.setHash(hash_string);

        String server_ip;
        String host_name;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("hash", hash_string);
        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "starbucks";
    }

    // Database Response Debugger
    @PostMapping("/selectedStore")
    public ResponseEntity<?> setSelectedStore(@RequestParam("register") String register, HttpSession session) {
        if (register != null && !register.isEmpty()) {
            // Save the register value in the session
            session.setAttribute("selectedRegister", register);
        } else {
            // Get the register value from the session
            register = (String) session.getAttribute("selectedRegister");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public String postAction(
            @Valid @ModelAttribute("command") Command command,
            @RequestParam(value = "action", required = true) String action,
            @RequestParam(value = "register", required = true) String register,
            @RequestParam(value = "drink", required = true) String drink,
            @RequestParam(value = "milk", required = true) String milk,
            @RequestParam(value = "size", required = true) String size,
            Errors errors, Model model, HttpServletRequest request, HttpSession session) {

        // Initialize some variables
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String resourceUrl = "";
        String message = "";

        // Environment variables
        String host = "http://localhost:8080/";
        String apikey = "2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ";

        // Set headers
        headers.set("apikey", "2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ");
        headers.add("register", register);
        headers.add("size", size);
        headers.add("drink", drink);
        headers.add("milk", milk);

        // returning the hash
        String input_hash = command.getHash();
        String input_state = command.getState();
        String input_timestamp = command.getTimestamp();

        String input_text = input_state + "/" + input_timestamp;
        String calculated_hash = hmac_sha256(input_text);

        System.out.println("Input Hash: " + input_hash);
        System.out.println("Valid Hash: " + calculated_hash);

        // check message integrity
//        if (!input_hash.equals(calculated_hash)) {
//            throw new CashierServerError();
//        }

        long ts1 = Long.parseLong(input_timestamp);
        long ts2 = System.currentTimeMillis();
        long diff = ts2 - ts1;

        System.out.println("Input State: " + ts1);
        System.out.println("Current State: " + ts2);
        System.out.println("State Delta: " + diff);

        // guard against replay attack
        if ((diff / 1000) > 1000) {
            throw new CashierServerError();
        }

        // Get active order
        if (action.equals("Get Order")) {
            resourceUrl = host + "/order/register/" + register + "?apikey={apikey}";
            System.out.println("Request URL: " + resourceUrl + ",\nHeaders: " + headers);
            try {
                ResponseEntity<Object> orderResponse = restTemplate.getForEntity(
                        resourceUrl, Object.class, apikey);
                // Order orderMsg = orderResponse.getBody();
                if (orderResponse.getStatusCodeValue() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(orderResponse.getBody());
                    message = "The active order:\n" + jsonString;
                } else if (orderResponse.getStatusCodeValue() == 400) {
                    message = "No active orders for this registry.";
                } else {
                    message = "Oops, something went wrong when getting an order.";
                }
            } catch (Exception e) {
                message = "No active orders for this registry.";
                System.out.println(e.getMessage());
            }
        }
        // Place new order
        if (action.equals("Place Order")) {
            resourceUrl = host + "/order/register/" + register + "?apikey={apikey}";
            System.out.println("Request URL: " + resourceUrl + ",\nHeaders: " + headers);
            Order orderRequest = new Order();
            orderRequest.setDrink(drink);
            orderRequest.setSize(size);
            orderRequest.setMilk(milk);
            HttpEntity<Object> newOrderRequest = new HttpEntity<>(orderRequest, headers);
            try {
                ResponseEntity<Object> newOrderResponse = restTemplate.postForEntity(
                        resourceUrl, newOrderRequest, Object.class, apikey);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(newOrderResponse.getBody());
                ;
                if (newOrderResponse.getStatusCodeValue() == 201) {
                    // Order newOrder = (Order) newOrderResponse.getBody();
                    message = "Placing an order:\n" + jsonString;
                } else if (newOrderResponse.getStatusCodeValue() == 400) {
                    message = "Active order exists or there is an input error when placing order.";
                } else {
                    message = "Oops, something went wrong when placing an order.";
                }
            } catch (Exception e) {
                message = "Active order exists or there is an input error when placing order.";
            }
        }

        // Clear active order
        if (action.equals("Clear Order")) {
            try {
                resourceUrl = host + "/order/register/" + register + "?apikey={apikey}";
                System.out.println("Request URL: " + resourceUrl + ",\nHeaders: " + headers);
                restTemplate.delete(resourceUrl, apikey);
                message = "Active order cleared.";
            } catch (Exception e) {
                message = "No active order for this registry.";
                System.out.println(e.getMessage());
            }
        }

        long ts_long = System.currentTimeMillis();
        String ts_string = String.valueOf(ts_long);
        command.setTimestamp(ts_string);

        String state = "READY";

        String text = state + "/" + ts_string;
        String hash_string = hmac_sha256(text);
        command.setHash(hash_string);

        String server_ip;
        String host_name;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("hash", hash_string);
        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        // Display page
        model.addAttribute("message", message);
        return "starbucks";
    }
}