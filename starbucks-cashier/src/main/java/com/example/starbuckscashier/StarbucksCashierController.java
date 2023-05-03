package com.example.starbuckscashier;
import com.example.starbuckscashier.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/")
public class StarbucksCashierController {
    private static final String KEY = "kwRg54x2Go9iEdl49jFENRM12Mp711QI";
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private String hmacSha256(String data) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA_256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), HMAC_SHA_256);
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
        String hashString = generateHashForCommand(command);
        String serverInfo = getServerInfo();
        String message = "";
        // 4. Display result
        model.addAttribute("hash", hashString);
        model.addAttribute("message", message);
        model.addAttribute("server", serverInfo);
        return "starbucks";
    }



    @PostMapping
    public String postAction(@ModelAttribute("command") Command command,
                             @RequestParam(value = "action") String action,
                             @RequestParam(value = "register") String register,
                             @RequestParam(value = "drink") String drink,
                             @RequestParam(value = "milk") String milk,
                             @RequestParam(value = "size") String size,
                             Model model) {

        // 1. Initialize variables and set up environment
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String message = "";
        String host = "http://localhost:8080";
        String apikey = "2H3fONTa8ugl1IcVS7CjLPnPIS2Hp9dJ";

        // 2. Set request headers
        headers.set("apikey", apikey);
        headers.add("register", register);
        headers.add("size", size);
        headers.add("drink", drink);
        headers.add("milk", milk);

        // 3. Handle actions based on user input
        switch (action) {
            case "Get Order":
                message = handleGetOrder(apikey, host, register, restTemplate);
                break;
            case "Place Order":
                message = handlePlaceOrder(apikey, host, register, restTemplate, drink, size, milk, headers);
                break;
            case "Clear Order":
                message = handleClearOrder(apikey, host, register, restTemplate);
                break;
        }

        String hashString = generateHashForCommand(command);
        String serverInfo = getServerInfo();

        // 4. Display result
        model.addAttribute("hash", hashString);
        model.addAttribute("message", message);
        model.addAttribute("server", serverInfo);
        return "starbucks";
    }

    // Handle "Get Order" action
    private String handleGetOrder(String apikey, String host, String register, RestTemplate restTemplate) {
        String resourceUrl = host.concat("/order/register/" + register + "?apikey={apikey}");
        String message;

        try {
            ResponseEntity<Object> orderResponse = restTemplate.getForEntity(resourceUrl, Object.class, apikey);

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
        return message;
    }

    // Handle "Place Order" action
    private String handlePlaceOrder(String apikey, String host, String register, RestTemplate restTemplate,
                                    String drink, String size, String milk, HttpHeaders headers) {
        String resourceUrl = host.concat("/order/register/" + register + "?apikey={apikey}");
        String message;

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

            if (newOrderResponse.getStatusCodeValue() == 201) {
                message = "Placing an order:\n" + jsonString;
            } else if (newOrderResponse.getStatusCodeValue() == 400) {
                message = "Active order exists or there is an input error when placing order.";
            } else {
                message = "Oops, something went wrong when placing an order.";
            }
        } catch (Exception e) {
            message = "Active order exists or there is an input error when placing order.";
        }
        return message;
    }

    // Handle "Clear Order" action
    private String handleClearOrder(String apikey, String host, String register, RestTemplate restTemplate) {
        String resourceUrl = host.concat("/order/register/" + register + "?apikey={apikey}");
        String message;
        try {
            restTemplate.delete(resourceUrl, apikey);
            message = "Active order cleared.";
        } catch (Exception e) {
            message = "No active order for this registry.";
            System.out.println(e.getMessage());
        }
        return message;
    }

    private String generateHashForCommand(Command command) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        command.setTimestamp(timestamp);

        String text = "READY" + "/" + timestamp;
        String hashString = hmacSha256(text);
        command.setHash(hashString);

        return hashString;
    }

    private String getServerInfo() {
        String serverIp;
        String hostName;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            serverIp = ip.getHostAddress();
            hostName = ip.getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return hostName + "/" + serverIp;
    }

}
